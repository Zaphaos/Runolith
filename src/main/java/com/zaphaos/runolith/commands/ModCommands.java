package com.zaphaos.runolith.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.SpellRegistry;
import com.zaphaos.runolith.spell_system.PlayerSpellData;

import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.bus.api.SubscribeEvent;

@EventBusSubscriber(modid = Runolith.MOD_ID)
public class ModCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
    	event.getDispatcher().register(
    			Commands.literal("runolith")
    				.then(Commands.literal("learn")
    				    .requires(src -> src.hasPermission(2))
    				    .then(Commands.argument("targets", EntityArgument.players())
    				        .then(Commands.argument("spellPart", StringArgumentType.string())
    				            .suggests((ctx, builder) -> {
    				                for (String key : SpellRegistry.PARTS.keySet()) builder.suggest(key);
    				                return builder.buildFuture();
    				            })
    				            .executes(ModCommands::learnSpell)
    				        )
    				    )
    				)
    				.then(Commands.literal("learn_all")
    				    .requires(src -> src.hasPermission(2))
    				    .then(Commands.argument("targets", EntityArgument.players())
    				        .executes(ModCommands::learnAll)
    				    )
    				)

    		        .then(Commands.literal("list")
    		        		// Variant 1: list user's spells (anyone can use)
    		        	    .executes(ModCommands::listSpells)
    		        	    // Variant 2: list other player's spells (requires OP)
    		        	    .then(Commands.argument("target", EntityArgument.player())
    		        	        .requires(src -> src.hasPermission(2)) // only OP
    		        	        .executes(ModCommands::listSpellsForTarget)
    		        	    )
    		        )

    		);

    }

    private static int learnSpell(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
    	var targets = EntityArgument.getPlayers(ctx, "targets");
        String part = StringArgumentType.getString(ctx, "spellPart");

        if (SpellRegistry.get(part) == null) {
            ctx.getSource().sendFailure(Component.literal("Unknown spell part: " + part));
            return 0;
        }

        for (ServerPlayer player : targets) {
            PlayerSpellData.learn(player, part);
        }

        ctx.getSource().sendSuccess(() ->
            Component.literal("Gave spell part '" + part + "' to " + targets.size() + " player(s)"),
            false
        );
        return 1;
    }

    private static int learnAll(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
    	var targets = EntityArgument.getPlayers(ctx, "targets");

        for (ServerPlayer player : targets) {
            for (String key : SpellRegistry.PARTS.keySet()) {
                PlayerSpellData.learn(player, key, true);
            }
        }

        ctx.getSource().sendSuccess(() ->
            Component.literal("Gave ALL spell parts to " + targets.size() + " player(s)"),
            false
        );
        return 1;
    }

    private static int listSpells(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) return 0;

        var list = PlayerSpellData.getKnownParts(player);

        ctx.getSource().sendSuccess(
            () -> Component.literal("Known spell parts: " + String.join(", ", list)),
            false
        );
        return 1;
    }
    private static int listSpellsForTarget(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
        var list = PlayerSpellData.getKnownParts(target);

        ctx.getSource().sendSuccess(
            () -> Component.literal(target.getName().getString() + " knows: " + String.join(", ", list)),
            false
        );
        return 1;
    }
    
}
