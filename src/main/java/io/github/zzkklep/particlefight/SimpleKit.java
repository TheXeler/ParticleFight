package io.github.zzkklep.particlefight;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.UUID;

public class SimpleKit {
    public static UUID getEntityUUID(Entity entity) {
        return entity.getUniqueId();
    }

    public static UUID getTargetEntityUUID(DamageEntityEvent event) {
        return event.getTargetEntity().getUniqueId();
    }

    public static boolean compareEdge(double num, double edge1, double edge2) {
        return ((edge1 + 1 - num) * (edge2 + 1 - num)) < 0;
    }

    public static boolean inArea(Location<World> postion, double x1, double y1, double z1, double x2, double y2, double z2) {
        return compareEdge(postion.getX(), x1, x2) && compareEdge(postion.getY(), y1, y2) && compareEdge(postion.getZ(), z1, z2);
    }

    public static boolean inArea(Location<World> postion, Location<World> areaPostion, double xOffset, double yOffset, double zOffset) {
        return inArea(postion, areaPostion.getX(), areaPostion.getY(), areaPostion.getZ(), areaPostion.getX() + xOffset, areaPostion.getY() + yOffset, areaPostion.getZ() + zOffset);
    }

    public static boolean inArea(Location<World> postion, Location<World> firstPostion, Location<World> lastPostion) {
        return inArea(postion, firstPostion.getX(), firstPostion.getY(), firstPostion.getZ(), lastPostion.getX(), lastPostion.getY(), lastPostion.getZ());
    }

    public static Title buildActionbarTitle(String str) {
        return Title.builder().actionBar(Text.builder(str).build()).build();
    }
}
