package ssd.curve.spacerain.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import ssd.curve.spacerain.Spacerain;
import ssd.curve.spacerain.client.ScribingTableMenu;
import ssd.curve.spacerain.item.ModItems;

import javax.annotation.Nullable;
import java.util.List;

public class ScribingTableBlockEntity extends BlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int INK_SLOT = 2;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    public int progress = 0;
    private int maxProgress = 20 * 120;

    public ScribingTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SCRIBING_TABLE_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> ScribingTableBlockEntity.this.progress;
                    case 1 -> ScribingTableBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> ScribingTableBlockEntity.this.progress = pValue;
                    case 1 -> ScribingTableBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    //@Override
    public Component getDisplayName() {
        return Component.translatable("block.spacerain.scribing_table");
    }

    @Nullable
    //@Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ScribingTableMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("scribing_table.progress", progress);

        super.saveAdditional(pTag);
    }

    public ItemStack getRenderStack() {
        return itemHandler.getStackInSlot(INK_SLOT);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("scribing_table.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
                pLevel.playSound(null, pPos, SoundEvents.UI_LOOM_TAKE_RESULT, SoundSource.BLOCKS);
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        //ItemStack result = new ItemStack(ModItems.COBALT_INGOT.get(), 1);
        this.itemHandler.extractItem(INPUT_SLOT, 1, false);
        Item ink = this.itemHandler.getStackInSlot(INK_SLOT).getItem();
        this.itemHandler.extractItem(INK_SLOT, 1, false);

        if (ink == ModItems.ASTRAL_INK.get()) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT, getLootFromTable((ServerLevel) this.level, "basic_scroll_loot"));
        } else if (ink == ModItems.REFINED_INK.get()) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT, getLootFromTable((ServerLevel) this.level, "grand_scroll_loot"));
        } else if (ink == ModItems.HATEFUL_INK.get()) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT, getLootFromTable((ServerLevel) this.level, "evil_scroll_loot"));
        } else if (ink == ModItems.SOLAR_INK.get()) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT, getLootFromTable((ServerLevel) this.level, "solar_scroll_loot"));
        }




    }

    private boolean hasRecipe() {
        boolean hasCraftingItem = this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() == ModItems.PARCHMENT.get();
        //ItemStack result = new ItemStack(ModItems.COBALT_CATALYST.get());


        return hasCraftingItem && hasInk() && isOutputEmpty();
    }
    private boolean hasInk() {
        Item ink = this.itemHandler.getStackInSlot(INK_SLOT).getItem();

        if (ink == ModItems.ASTRAL_INK.get()) {
            if (maxProgress != 20 * 120) {
                progress = 0;
                maxProgress = 20*120;
            }
        }
        if (ink == ModItems.REFINED_INK.get()) {
            if (maxProgress != 20 * 600) {
                progress = 0;
                maxProgress = 20*600;
            }
        }
        if (ink == ModItems.HATEFUL_INK.get()) {
            if (maxProgress != 20 * 1800) {
                progress = 0;
                maxProgress = 20*1800;
            }
        }
        if (ink == ModItems.SOLAR_INK.get()) {
            if (maxProgress != 20 * 3600) {
                progress = 0;
                maxProgress = 20*3600;
            }
        }



        return (
                ink == ModItems.ASTRAL_INK.get() ||
                ink == ModItems.REFINED_INK.get() ||
                ink == ModItems.HATEFUL_INK.get() ||
                ink == ModItems.SOLAR_INK.get()
                );
    }
    private boolean isOutputEmpty() {
        ItemStack out = this.itemHandler.getStackInSlot(OUTPUT_SLOT);
        return out == ItemStack.EMPTY;
    }

    public ItemStack getLootFromTable(ServerLevel level, String tableid) {
        LootTable lt = level.getServer().getLootData().getLootTable(new ResourceLocation(Spacerain.MODID, tableid));
        List<ItemStack> items = lt.getRandomItems(new LootParams.Builder(level).create(LootContextParamSets.EMPTY));
        return items.get(0);
    }

    /*private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }*/

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }
}
