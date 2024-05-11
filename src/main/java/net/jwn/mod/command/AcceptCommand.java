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
                command -> Accept(command, EntityArgument.getEntities(command, "target"))
        )));
    }

    private int Accept(CommandContext<CommandSourceStack> command, Collection<? extends Entity> pTargets) {
        if (command.getSource().getEntity() instanceof Player player) {
            if (pTargets.size() != 1) {
                player.sendSystemMessage(Component.literal("select one opponent"));
            }
            if (pTargets.toArray()[0] instanceof Player target) {
                if (target.getPersistentData().getString(Main.MOD_ID + "_opponent").equals(player.getName().getString())) {
                    target.getPersistentData().putString(Main.MOD_ID + "_opponent", "");
                    player.sendSystemMessage(Component.literal("accept!"));
                } else {
                    player.sendSystemMessage(Component.literal("no match!"));
                }
            } else {
                player.sendSystemMessage(Component.literal("select player"));
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
