package net.jwn.mod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.jwn.mod.Main;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class SetOpponentCommand {
    public SetOpponentCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("opponent").then(Commands.literal("set").then(Commands.argument("target", EntityArgument.entities()).executes(
                command -> setOpponent(command, EntityArgument.getEntities(command, "target"))
        ))));
    }

    private int setOpponent(CommandContext<CommandSourceStack> command, Collection<? extends Entity> pTargets) {
        if (command.getSource().getEntity() instanceof Player player) {
            if (player.getPersistentData().getBoolean(Main.MOD_ID + "_battle")) {
                player.sendSystemMessage(Component.translatable("error.mod_players.battle"));
                return Command.SINGLE_SUCCESS;
            }
            if (pTargets.size() != 1) {
                player.sendSystemMessage(Component.translatable("error.mod_players.one_opponent"));
                return Command.SINGLE_SUCCESS;
            }
            if (pTargets.toArray()[0] instanceof Player target) {
                player.getPersistentData().putUUID(Main.MOD_ID + "_opponent", target.getUUID());

                player.sendSystemMessage(Component.translatable("message.mod_players.set_opponent", target.getName().getString()));
                target.sendSystemMessage(Component.translatable("message.mod_players.set_opponent_to_target", player.getName().getString()));
            } else {
                player.sendSystemMessage(Component.translatable("error.mod_players.player_opponent"));
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
