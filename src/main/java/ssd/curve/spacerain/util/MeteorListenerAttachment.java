package ssd.curve.spacerain.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.valkyrienskies.core.api.ships.PhysShip;
import org.valkyrienskies.core.api.ships.ShipPhysicsListener;
import org.valkyrienskies.core.api.world.PhysLevel;
import org.valkyrienskies.mod.mixinducks.world.entity.PlayerDuck;

public final class MeteorListenerAttachment implements ShipPhysicsListener {
    public double previousspeed;
    private boolean keepworking;
    private boolean stopbeingstatic;
    private boolean attemptcrater;
    private int ptickcountermajig;
    public MeteorListenerAttachment() {
        this.previousspeed = 0;
        this.keepworking = true;
        this.stopbeingstatic = false;
        this.attemptcrater = false;
        this.ptickcountermajig = 0;

    };

    @Override
    public void physTick(@NotNull PhysShip physShip, @NotNull PhysLevel physLevel) {
        // do stuff

        // ok 2 things
        // if too far away, stop chunkloading (?)
        // if velocity drops suddenly, make crater and stop chunkloading
        if (this.keepworking) {
            this.ptickcountermajig += 1;
            double changeinvelocity = Math.abs(this.previousspeed - physShip.getVelocity().length());

            if (changeinvelocity > 4.0) { //?? why crash idfk
                this.attemptcrater = true;

                physShip.setStatic(true);

                this.keepworking = false;
                this.stopbeingstatic = true;
            }

            this.previousspeed = physShip.getVelocity().length();
            if (ptickcountermajig % 3 == 0) {
                if (!MeteorImpactCraterManager.dontFUCKINGmodifytheselists) {
                    MeteorImpactCraterManager.meteorfallparticles.add(physShip.getId());
                }

            }

        }
        if (this.stopbeingstatic) {

            physShip.setStatic(false);
        }
        if (this.attemptcrater) {
            if (!MeteorImpactCraterManager.dontFUCKINGmodifytheselists) {
                MeteorImpactCraterManager.meteorsExploding.add(physShip.getId());
                this.attemptcrater = false;
            }
        }


    }

}
