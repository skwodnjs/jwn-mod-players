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
            if (pTargets.size() != 1) {
                player.sendSystemMessage(Component.literal("select one target"));
            }
            if (pTargets.toArray()[0] instanceof Player target) {
                player.getPersistentData().putString(Main.MOD_ID + "_target", target.getName().getString());
                player.sendSystemMessage(Component.literal("target: " + target.getName().getString()));
            } else {
                player.sendSystemMessage(Component.literal("select player"));
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
