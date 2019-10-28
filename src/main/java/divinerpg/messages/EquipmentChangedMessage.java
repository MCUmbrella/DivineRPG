package divinerpg.messages;

import divinerpg.events.FullArmorEventHandler;
import divinerpg.utils.ArmorObserver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class EquipmentChangedMessage implements IMessage {

    private UUID id;

    public EquipmentChangedMessage() {

    }

    @OnlyIn(Dist.CLIENT)
    public EquipmentChangedMessage(UUID id) {
        this.id = id;
    }

    @Override
    public void read(PacketBuffer buffer) {
        id = new UUID(buffer.readVarLong(), buffer.readVarLong());
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeVarLong(id.getMostSignificantBits());
        buffer.writeVarLong(id.getLeastSignificantBits());
    }

    @Override
    public void consume(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            // get sender
            ServerPlayerEntity playerEntity = context.get().getSender();
            if (playerEntity != null) {
                // get world
                World world = playerEntity.getEntityWorld();
                // find caller
                PlayerEntity player = world.getPlayerByUuid(id);
                if (player != null) {
                    // find his powers ability record
                    ArmorObserver observer = FullArmorEventHandler.playerMap.get(id);
                    if (observer != null) {
                        // request update
                        observer.Update(player);
                    } else {
                        FullArmorEventHandler.putNewPLayer(player);
                    }
                }
                return;
            }

            throw new RuntimeException("Can't access to player world!");
        });
    }
}
