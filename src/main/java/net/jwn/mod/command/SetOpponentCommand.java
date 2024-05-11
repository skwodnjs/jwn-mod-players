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

public class SetHomeCommand {
    public SetHomeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home").then(Commands.literal("set").then(Commands.argument("target", EntityArgument.entities()).executes(
                command -> setHome(command, EntityArgument.getEntity(command, "target"))
        ))));
    }

    private int setHome(CommandContext<CommandSourceStack> command, Entity entity) {
        if (command.getSource().getEntity() instanceof Player player) {
//            if (player.level().isClientSide) {
//                player.sendSystemMessage(Component.literal("client"));
//            } else {
//                player.sendSystemMessage(Component.literal("server"));
//            }
//            only server
            if (entity instanceof Player) {
                player.getPersistentData().putString(Main.MOD_ID + "_target", entity.getName().getString());
                player.sendSystemMessage(Component.literal(entity.getName().getString()));
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
