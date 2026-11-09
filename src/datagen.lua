local json = require 'json'
-- just datagen because i cant figure out java datagen
-- this is... sort of easier...
-- to curseforge & modrinth moderators: 
-- this code is not intended to run ingame! like.. ever!

local originalsmeltingrecipe = json.decode(io.open("smelt_template.json", "r"):read("*a"))
io.close()
local originalbreaktempl = json.decode(io.open("break_template.json", "r"):read("*a"))
io.close()
local tagtemplate = {replace = false, values = {}}
local filepath1 = "generated/resources/data/spacerain/recipes/"
local filepath2 = "generated/resources/data/spacerain/loot_tables/blocks/"
function meteoricoresmeltrecipe(inp, out, out_num)
    local file, err = io.open(filepath1 .. out .. "_smelting_from_meteoric_ore.json", "w")
    if file then
        local recipe = originalsmeltingrecipe
        recipe["type"] = "minecraft:smelting" -- why the fuck are you editing originalsmeltingrecipe??? edit recipe bro not the other fucking variable
        recipe.ingredient.item = "spacerain:meteoric_" .. inp .. "_ore"
        recipe.result.item = "minecraft:" .. out
        recipe.result.count = out_num
        file:write(json.encode(recipe))
        file:close()
    else
        print(err)
    end

    local file2, err2 = io.open(filepath1 .. out .. "_blasting_from_meteoric_ore.json", "w")
    if file2 then
        local recipe2 = originalsmeltingrecipe
        recipe2["type"] = "minecraft:blasting"
        recipe2.ingredient.item = "spacerain:meteoric_" .. inp .. "_ore"
        recipe2.result.item = "minecraft:" .. out
        recipe2.result.count = out_num
        file2:write(json.encode(recipe2))
        file2:close()
    else
        print(err2)
    end
end
function meteoricblocklootgen(name)
    local file, err = io.open(filepath2 .. name .. ".json", "w")
    if file then
        local recipe = originalbreaktempl
        recipe.pools[1].entries[1].name = "spacerain:" .. name
        recipe.random_sequence = "spacerain:block/" .. name
        file:write(json.encode(recipe))
        file:close()
    else
        print(err)
    end
    addtoweightjson(name, 5000, 0.6, 0.1)
end

function clearoutdirectory() -- doesnt work
    os.remove("generated/resources/data/spacerain")
end
local vsweightjson = {}
function addtoweightjson(name, weight, friction, elasticity) -- default values are 1000, 0.5, and 0.3. most meteor blocks use 5000, 0.6, 0.1
    table.insert(vsweightjson, {block = "spacerain:" .. name, mass = weight, friction = friction, elasticity = elasticity})
end
function weightjson(values)
    local file, err = io.open("generated/resources/data/spacerain/vs_mass/spacerain_mass_values.json", "w")
    if file then
        file:write(json.encode(values))
        file:close()
    else
        print(err)
    end
end
function setuptag(name, values)
    local file, err = io.open("generated/resources/data/minecraft/tags/blocks/" .. name .. ".json", "w")
    if file then
        local recipe = tagtemplate
        recipe.values = values
        file:write(json.encode(recipe))
        file:close()
    else
        print(err)
    end
    
end
local thesoundjson = {}
function addtosoundjson(name, subtitle) 
    thesoundjson[name] = {subtitles = subtitle, sounds = {"spacerain:" .. name}}
end
function soundjson(values)
    local file, err = io.open("generated/resources/assets/spacerain/sounds.json", "w")
    if file then
        file:write(json.encode(values))
        file:close()
    else
        print(err)
    end
end

function basicblockmodelgen(name)
    local bs = {variants = {}}
    bs.variants[""] = {}
    bs.variants[""].model = "spacerain:block/" .. name
    local bm = {parent = "minecraft:block/cube_all", textures = {all = "spacerain:block/" .. name}}
    local im = {parent = "spacerain:block/" .. name}
    local file, err = io.open("generated/resources/assets/spacerain/blockstates/" .. name .. ".json", "w")
    file:write(json.encode(bs))
    file:close()
    local file, err = io.open("generated/resources/assets/spacerain/models/block/" .. name .. ".json", "w")
    file:write(json.encode(bm))
    file:close()
    local file, err = io.open("generated/resources/assets/spacerain/models/item/" .. name .. ".json", "w")
    file:write(json.encode(im))
    file:close()

end
function basicitemgen(name)
    local im = {parent = "item/generated", textures = {layer0 = "spacerain:item/" .. name}}
    local file, err = io.open("generated/resources/assets/spacerain/models/item/" .. name .. ".json", "w")
    file:write(json.encode(im))
    file:close()
end
function toolstyleitemgen(name)
    local im = {parent = "item/handheld", textures = {layer0 = "spacerain:item/" .. name}}
    local file, err = io.open("generated/resources/assets/spacerain/models/item/" .. name .. ".json", "w")
    file:write(json.encode(im))
    file:close()
end
function scrollitemgen(name, variant) -- evil, grand, grand and evil, secret
    local im = {parent = "spacerain:item/scroll"}
    if variant == 1 then
        im = {parent = "spacerain:item/scroll", textures = {}}
        im.textures["0"] = "spacerain:item/scroll_red"
    end
    if variant == 2 then
        im = {parent = "spacerain:item/scroll", textures = {}}
        im.textures["0"] = "spacerain:item/scroll_grand"
    end
    if variant == 3 then
        im = {parent = "spacerain:item/scroll", textures = {}}
        im.textures["0"] = "spacerain:item/scroll_grand_red"
    end
    if variant == 4 then
        im = {parent = "spacerain:item/scroll", textures = {}}
        im.textures["0"] = "spacerain:item/scroll_darkness"
    end
    local file, err = io.open("generated/resources/assets/spacerain/models/item/" .. name .. ".json", "w")
    file:write(json.encode(im))
    file:close()
end


-- models
basicblockmodelgen("meteorite_cobble")
basicblockmodelgen("meteorite_block")
basicblockmodelgen("molten_meteorite_block")
basicblockmodelgen("frigid_meteorite_block")

basicblockmodelgen("meteoric_copper_ore")
basicblockmodelgen("meteoric_iron_ore")
basicblockmodelgen("meteoric_gold_ore")
basicblockmodelgen("meteoric_diamond_ore")
basicblockmodelgen("meteoric_lapis_ore")
basicblockmodelgen("meteoric_redstone_ore")
basicblockmodelgen("meteoric_emerald_ore")

basicblockmodelgen("meteoric_cobaltite_ore")
basicblockmodelgen("astral_jewel_ore")
basicblockmodelgen("stellar_jewel_ore")
basicblockmodelgen("stellar_blight")

basicblockmodelgen("meteor_spawner_block")
--basicblockmodelgen("star")

basicblockmodelgen("solarfield")
basicblockmodelgen("shimmering_solarfield")
basicblockmodelgen("glowing_solarfield")
basicblockmodelgen("blazing_solarfield")
basicblockmodelgen("astral_solarfield")

basicblockmodelgen("solarbed")


basicitemgen("cobalt_ingot")
basicitemgen("cobalt_catalyst")
basicitemgen("fallen_sigil")
basicitemgen("awakened_sigil")
basicitemgen("astral_jewel")
basicitemgen("stellar_jewel")
basicitemgen("stellar_sigil")
basicitemgen("star")

basicitemgen("solar_tongs")
basicitemgen("spectrograph")

basicitemgen("cobalt_bottle")
basicitemgen("light_elements")
basicitemgen("medium_elements")
basicitemgen("heavy_elements")
basicitemgen("exotic_elements")

basicitemgen("cobalt_helmet")
basicitemgen("cobalt_chestplate")
basicitemgen("cobalt_leggings")
basicitemgen("cobalt_boots")

toolstyleitemgen("cobalt_sword")
toolstyleitemgen("cobalt_pickaxe")
toolstyleitemgen("cobalt_axe")
toolstyleitemgen("cobalt_shovel")
toolstyleitemgen("cobalt_hoe")

toolstyleitemgen("cobalt_omicron")
toolstyleitemgen("awakened_cobalt_omicron")
toolstyleitemgen("stellar_cobalt_omicron")

scrollitemgen("debug_scroll", 4)
scrollitemgen("scroll_transmutate_iron")
scrollitemgen("scroll_transmutate_obsidian", 2)
scrollitemgen("scroll_totemization", 2)
scrollitemgen("scroll_applechantment")
scrollitemgen("scroll_lightning")
scrollitemgen("scroll_fireball")
scrollitemgen("scroll_atomic")
scrollitemgen("scroll_warp")
scrollitemgen("scroll_rain", 2)
scrollitemgen("scroll_meteor", 2)
scrollitemgen("scroll_bovinomancy")
scrollitemgen("scroll_omicron", 1)
scrollitemgen("scroll_true_light", 2)
scrollitemgen("scroll_extended_endurance")
scrollitemgen("scroll_extended_healing")
scrollitemgen("scroll_saturate")
scrollitemgen("scroll_famine", 1)
scrollitemgen("scroll_global_warming", 1)
scrollitemgen("scroll_awaken", 1)
scrollitemgen("scroll_stellaris", 3)

-- loot and recipes
meteoricoresmeltrecipe("copper", "copper_ingot", 16)
meteoricoresmeltrecipe("iron", "iron_ingot", 16)
meteoricoresmeltrecipe("gold", "gold_ingot", 16)
meteoricoresmeltrecipe("diamond", "diamond", 4)
meteoricoresmeltrecipe("lapis", "lapis_lazuli", 32)
meteoricoresmeltrecipe("redstone", "redstone", 48)
meteoricoresmeltrecipe("emerald", "emerald", 16)

meteoricblocklootgen("meteorite_cobble")
meteoricblocklootgen("meteorite_block")
meteoricblocklootgen("molten_meteorite_block")
meteoricblocklootgen("frigid_meteorite_block")

meteoricblocklootgen("meteoric_copper_ore")
meteoricblocklootgen("meteoric_iron_ore")
meteoricblocklootgen("meteoric_gold_ore")
meteoricblocklootgen("meteoric_diamond_ore")
meteoricblocklootgen("meteoric_lapis_ore")
meteoricblocklootgen("meteoric_redstone_ore")
meteoricblocklootgen("meteoric_emerald_ore")

meteoricblocklootgen("solarfield")
meteoricblocklootgen("shimmering_solarfield")
meteoricblocklootgen("glowing_solarfield")
meteoricblocklootgen("blazing_solarfield")
meteoricblocklootgen("astral_solarfield")


setuptag("needs_stone_tool", {
    "spacerain:meteoric_copper_ore",
    "spacerain:meteoric_iron_ore",
    "spacerain:meteoric_cobaltite_ore",
    "spacerain:meteorite_block",
    "spacerain:molten_meteorite_block",
    "spacerain:frigid_meteorite_block",
    "spacerain:meteorite_cobble",
    "spacerain:stellar_blight"
})
setuptag("needs_iron_tool", {
    "spacerain:meteoric_gold_ore",
    "spacerain:meteoric_diamond_ore",
    "spacerain:meteoric_redstone_ore",
    "spacerain:meteoric_lapis_ore",
    "spacerain:meteoric_emerald_ore",
    "spacerain:astral_altar"
})
setuptag("needs_diamond_tool", {
    "spacerain:astral_jewel_ore",
    "spacerain:stellar_jewel_ore"
})
setuptag("/mineable/pickaxe", {
    "spacerain:meteoric_copper_ore",
    "spacerain:meteoric_iron_ore",
    "spacerain:meteoric_cobaltite_ore",
    "spacerain:meteoric_gold_ore",
    "spacerain:meteoric_diamond_ore",
    "spacerain:meteoric_redstone_ore",
    "spacerain:meteoric_lapis_ore",
    "spacerain:meteoric_emerald_ore",
    "spacerain:astral_jewel_ore",
    "spacerain:stellar_jewel_ore",
    "spacerain:meteorite_block",
    "spacerain:molten_meteorite_block",
    "spacerain:frigid_meteorite_block",
    "spacerain:meteorite_cobble",
    "spacerain:stellar_blight",
    "spacerain:astral_altar",
    "spacerain:solarfield",
    "spacerain:shimmering_solarfield",
    "spacerain:glowing_solarfield",
    "spacerain:blazing_solarfield",
    "spacerain:astral_solarfield",
})

meteoricblocklootgen("meteoric_cobaltite_ore")
--meteoricblocklootgen("astral_jewel_ore")


--soundjson(thesoundjson)
addtoweightjson("astral_jewel_ore", 5000, 0.6, 0.1)
addtoweightjson("stellar_jewel_ore", 5000, 0.6, 0.1)
addtoweightjson("star", 1000000, 0, 0)
weightjson(vsweightjson)

