package io.github.zzkklep.particlefight;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.SkullTypes;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserDataManager {

    private HashMap<UUID, Long> m_pointsMap = new HashMap<>();
    private HashMap<UUID, Long> m_pointsMaxMap = new HashMap<>();
    private HashMap<UUID, Long> m_pointsRegenMap = new HashMap<>();
    private HashMap<UUID, Integer> m_lastDamageTickMap = new HashMap<>();
    private HashMap<UUID, Task> m_tasksMap = new HashMap<>();
    private HashMap<UUID, ItemStack> m_helmetMap = new HashMap<>();

    @Inject
    private Logger logger;

    public long getParticlePoints(UUID uuid) {
        if (isValidID(uuid)) {
            return m_pointsMap.get(uuid);
        }
        return -1;
    }

    public long getParticlePointsMax(UUID uuid) {
        if (isValidID(uuid)) {
            return m_pointsMaxMap.get(uuid);
        }
        return -1;
    }

    public long getParticlePointsRegen(UUID uuid) {
        if (isValidID(uuid)) {
            return m_pointsRegenMap.get(uuid);
        }
        return -1;
    }

    public int getLastDamageTick(UUID uuid) {
        if (isValidID(uuid)) {
            return m_lastDamageTickMap.get(uuid);
        }
        return -1;
    }

    public void setParticlePoints(UUID uuid, long value) {
        if (isValidID(uuid)) {
            m_pointsMap.put(uuid, value);
        } else {
            initUser(uuid);
            m_pointsMap.put(uuid, value);
        }
    }

    public void setParticlePointsMax(UUID uuid, long value) {
        if (isValidID(uuid)) {
            m_pointsMaxMap.put(uuid, value);
        } else {
            initUser(uuid);
            m_pointsMaxMap.put(uuid, value);
        }
    }

    public void setParticlePointsRegen(UUID uuid, long value) {
        if (isValidID(uuid)) {
            m_pointsRegenMap.put(uuid, value);
        } else {
            initUser(uuid);
            m_pointsRegenMap.put(uuid, value);
        }
    }

    public void setLastDamageTick(UUID uuid, int value) {
        if (isValidID(uuid)) {
            m_lastDamageTickMap.put(uuid, value);
        } else {
            initUser(uuid);
            m_lastDamageTickMap.put(uuid, value);
        }
    }

    public boolean removeTask(UUID uuid) {
        if (m_tasksMap.get(uuid) != null) {
            m_tasksMap.get(uuid).cancel();
            m_tasksMap.put(uuid, null);
        }
        return m_tasksMap.get(uuid) == null;
    }

    public boolean startTask(UUID uuid, Task task) {
        if (m_tasksMap.get(uuid) == null) {
            m_tasksMap.put(uuid, task);
            return true;
        }
        task.cancel();
        return false;
    }

    public ItemStack exchangeHelmetCache(UUID uuid, ItemStack item) {
        ItemStack cache = m_helmetMap.get(uuid);
        if (m_helmetMap.get(uuid) == null || (m_helmetMap.get(uuid) == item)) {
            m_helmetMap.put(uuid, item);
            List<Text> lore = new ArrayList<Text>();
            lore.add(0, Text.of(TextColors.BLACK, "PF_LOCKITEM"));
            cache = ItemStack.builder()
                    .itemType(ItemTypes.SKULL)
                    .add(Keys.SKULL_TYPE, SkullTypes.PLAYER)
                    .add(Keys.REPRESENTED_PLAYER, Sponge.getServer().getPlayer(uuid).get().getProfile())
                    .add(Keys.ITEM_LORE, lore)
                    .build();
        } else {
            m_helmetMap.put(uuid, item);
        }
        return cache;
    }

    public void initUser(UUID uuid) {
        m_pointsMap.put(uuid, (long) 0);
        m_pointsMaxMap.put(uuid, (long) 200);
        m_pointsRegenMap.put(uuid, (long) 5);
        m_lastDamageTickMap.put(uuid, 0);
    }

    public boolean isValidID(UUID uuid) {
        return (m_pointsMap.get(uuid) != null) | (m_tasksMap.get(uuid) != null);
    }

    public boolean loadData() {
        boolean flag = true;
        return flag;
    }
}
