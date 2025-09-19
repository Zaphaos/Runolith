package com.zaphaos.runolith.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.zaphaos.runolith.Runolith;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Runolith.MODID);
    
    public static final DeferredItem<Item> RUBY = ITEMS.register("ruby", 
    		() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> IMPURE_RUBY = ITEMS.register("impure_ruby", 
    		() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GEM_NODE_EMERALD = ITEMS.register("gem_node_emerald", 
    		() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GEM_NODE_RUBY = ITEMS.register("gem_node_ruby", 
    		() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GEM_NODE_DIAMOND = ITEMS.register("gem_node_diamond", 
    		() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> EMPTY_RUBY = ITEMS.register("empty_ruby", 
    		() -> new Item(new Item.Properties()));
    
    public static void register(IEventBus modEventBus) 
    {
        ITEMS.register(modEventBus);
    }
}