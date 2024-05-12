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

public class AcceptCommand {
    public AcceptCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("accept").then(Commands.argument("target", EntityArgument.entities()).executes(
                command -> accept(command, EntityArgument.getEntities(command, "target"))
        )));
    }

    private int accept(CommandContext<CommandSourceStack> command, Collection<? extends Entity> pTargets) {
        if (command.getSource().getEntity() instanceof Player player) {
            if (pTargets.size() != 1) {
                player.sendSystemMessage(Component.translatable("error.mod_players.one_opponent"));
            }
            if (pTargets.toArray()[0] instanceof Player target) {
                if (target.getPersistentData().getUUID(Main.MOD_ID + "_opponent").equals(player.getUUID())) {
//                    target.getPersistentData().remove(Main.MOD_ID + "_opponent");
//                    player.getPersistentData().remove(Main.MOD_ID + "_opponent");

                    target.getPersistentData().putBoolean(Main.MOD_ID + "_battle", true);
                    player.getPersistentData().putBoolean(Main.MOD_ID + "_battle", true);

                    target.sendSystemMessage(Component.translatable("message.mod_players.accept_target", player.getName().getString()));
                    player.sendSystemMessage(Component.translatable("message.mod_players.accept_player", target.getName().getString()));
                } else {
                    player.sendSystemMessage(Component.translatable("message.mod_players.accept_failed"));
                }
            } else {
                player.sendSystemMessage(Component.translatable("error.mod_players.player_opponent"));
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
