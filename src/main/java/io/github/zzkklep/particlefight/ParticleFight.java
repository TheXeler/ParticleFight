package io.github.zzkklep.particlefight;

import com.flowpowered.math.vector.Vector3d;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.data.property.PropertyHolder;
import org.spongepowered.api.data.type.GoldenApples;
import org.spongepowered.api.data.type.SkullTypes;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.event.item.inventory.UseItemStackEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.profile.property.ProfileProperty;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Plugin(authors = {"TheXeler", "Yancie"}, name = "ParticleFight", id = "particlefight")
public class ParticleFight {

    private final String COMMAND_BLOCK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjMzNjQxMWUyMWJhNWJhZTQxZGE0ZDBkYTIzYjcxOTExOGYxYzc5YjYxYzMwYmJmMGE1YWNhZjQ1M2ExYSJ9fX0=";

    @Inject
    private Logger m_logger;

    @Inject
    private PluginContainer container;
    //private EventContext eventContext = EventContext.builder().add(EventContextKeys.PLUGIN, container).build();

    private boolean m_valid = true;
    private final UserDataManager m_userDataManager = new UserDataManager();

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path m_configDir;
    private ConfigurationLoader<CommentedConfigurationNode> m_configLoader = null;

    //Initialization
    @Listener(order = Order.LATE)
    public void onServerStart(GameStartingServerEvent event) {
        m_logger.info("------------UNDER TENSAI------------");
        m_logger.info("  0000000000000000000000000000  ");
        m_logger.info("00000000000000000000000000000000");
        m_logger.info("00000000000000000000000000000000");
        m_logger.info("00000000000000000000000000000000");
        m_logger.info("00000000000000000000000000000000");
        m_logger.info("00000000000000000000    00000000");
        m_logger.info("00000000000000000000    00000000");
        m_logger.info("00000000000000000000    00000000");
        m_logger.info("00000000 0000000   0000     0000");
        m_logger.info("00000000 0000000   0000     0000");
        m_logger.info("00000000 0000000   0000     0000");
        m_logger.info("00000000 0000000   0000     0000");
        m_logger.info("00000    0000      0000     0000");
        m_logger.info("0000   000000      0000     0000");
        m_logger.info("0000   000000      0000     0000");
        m_logger.info("0000     0000      0000     0000");
        m_logger.info("-----------------Kai-----------------");
        m_logger.info("I'm serious,you should go to watch BV16b41167JX");
        m_logger.info("Start init ParticleFight plugin.");
        m_logger.info("Version:Test Version");
        m_logger.info("UpdateDate:2021/7/13 v1");
        m_logger.info("Start register command...");
        CommandSpec commandSpec_bighead = CommandSpec.builder()
                .description(Text.of("Particle Big Head."))
                .arguments(GenericArguments.onlyOne(GenericArguments.bool(Text.of("switch"))))
                .executor((CommandSource src, CommandContext args) -> {
                    if (src instanceof Player) {
                        if (args.getOne("switch").isPresent()) {
                            Player player = (Player) src;
                            if (args.<Boolean>getOne("switch").get()) {
                                m_userDataManager.startTask(((Player) src).getUniqueId(), Task.builder().execute(() -> {
                                    onPlayerHappy(player);
                                })
                                        .intervalTicks(5)
                                        .submit(this));
                            } else {
                                m_userDataManager.removeTask(((Player) src).getUniqueId());
                                if (player.getHelmet().isPresent()) {
                                    ItemStack helmet = player.getHelmet().get();
                                    if ((helmet.getType() == ItemTypes.SKULL) && (helmet.get(Keys.SKULL_TYPE).get() == SkullTypes.PLAYER) && (helmet.get(Keys.REPRESENTED_PLAYER).get() == player.getProfile())) {
                                        player.setHelmet(m_userDataManager.exchangeHelmetCache(player.getUniqueId(), null));
                                    }
                                }
                            }
                            src.sendMessage(CommandReturnInfo.SUCCESS);
                            return CommandResult.success();
                        }
                        return CommandResult.empty();
                    }
                    src.sendMessage(CommandReturnInfo.ERROR_ONLYUSER);
                    return CommandResult.empty();
                })
                .build();
        CommandSpec commandSpec_commandblock = CommandSpec.builder()
                .description(Text.of("Particle Connect."))
                .executor((CommandSource src, CommandContext args) -> {
                    if (src instanceof Player) {
                        Player player = (Player) src;
                        Extent extent = player.getWorld();
                        PropertyHolder property = null;
                        Entity entity = extent.createEntity(EntityTypes.ITEM, player.getPosition());
                        List<Text> lore = new ArrayList<Text>();
                        lore.add(0, Text.of(TextColors.BLACK, "PF_LOCKPLACEITEM"));
                        entity.offer(Keys.REPRESENTED_ITEM, ItemStack.builder()
                                .itemType(ItemTypes.SKULL)
                                .quantity(1)
                                .add(Keys.DISPLAY_NAME, Text.of("§r便携式命令方块"))
                                .add(Keys.SKULL_TYPE, SkullTypes.PLAYER)
                                .add(Keys.ITEM_LORE, lore)
                                .add(Keys.REPRESENTED_PLAYER, Sponge.getServer().getGameProfileManager().get(UUID.fromString("45705530-7874-4155-9528-87a3f902e267")).join())
                                //eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjMzNjQxMWUyMWJhNWJhZTQxZGE0ZDBkYTIzYjcxOTExOGYxYzc5YjYxYzMwYmJmMGE1YWNhZjQ1M2ExYSJ9fX0=
                                .build()
                                .createSnapshot()
                        );
                        extent.spawnEntity(entity);
                        src.sendMessage(CommandReturnInfo.SUCCESS);
                        return CommandResult.success();
                    }
                    src.sendMessage(CommandReturnInfo.ERROR_ONLYUSER);
                    return CommandResult.empty();
                })
                .build();
        CommandSpec commandSpec_jumpattack = CommandSpec.builder()
                .description(Text.of("Particle Jump."))
                .executor((CommandSource src, CommandContext args) -> {
                    if (src instanceof Player) {
                        Player player = (Player) src;
                        if (m_userDataManager.getParticlePoints(((Player) src).getUniqueId()) >= 50) {
                            PotionEffectData effects = player.get(PotionEffectData.class).isPresent() ? player.get(PotionEffectData.class).get() : null;
                            if (!effects.effects().isEmpty()) {
                                int index = 0;
                                while (index < effects.effects().size()) {
                                    if (effects.effects().get(index).getType() == PotionEffectTypes.JUMP_BOOST) {
                                        break;
                                    }
                                    index++;
                                }
                                effects.effects().set(index, PotionEffect.of(PotionEffectTypes.JUMP_BOOST, 14, 20));
                            } else {
                                effects.effects().add(0, PotionEffect.of(PotionEffectTypes.JUMP_BOOST, 14, 20));
                            }
                            player.offer(effects);
                            src.sendMessage(CommandReturnInfo.SUCCESS);
                            return CommandResult.success();
                        } else {
                            src.sendMessage(CommandReturnInfo.ERROR_DATAERROR);
                            return CommandResult.empty();
                        }
                    } else {
                        src.sendMessage(CommandReturnInfo.ERROR_NOPP);
                        return CommandResult.empty();
                    }
                })
                .build();
        CommandSpec commandSpec_particlefight = CommandSpec.builder()
                .description(Text.of("ParticleFight plugin main command."))
                .executor((CommandSource src, CommandContext args) -> {
                    src.sendMessage(Text.of("ParticleFight Plugin madeby Xeler\nVersion 0.10D07122021V4"));
                    return CommandResult.success();
                })
                .child(commandSpec_bighead, "bighead")
                .child(commandSpec_commandblock, "getcb")
                .child(commandSpec_jumpattack, "jump")
                .build();
        Sponge.getCommandManager().register(this, commandSpec_particlefight, "particlefight");
        m_logger.info("Register complete.Start loading data.");
        GameProfile gameProfile = GameProfile.of(UUID.fromString("45705530-7874-4155-9528-87a3f902e267"),null);
        gameProfile.addProperty(ProfileProperty.of("textures","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjMzNjQxMWUyMWJhNWJhZTQxZGE0ZDBkYTIzYjcxOTExOGYxYzc5YjYxYzMwYmJmMGE1YWNhZjQ1M2ExYSJ9fX0="));
        Sponge.getServer().getGameProfileManager().fill(gameProfile);
        try {
            Sponge.getServer().getGameProfileManager().get(UUID.fromString("45705530-7874-4155-9528-87a3f902e267")).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        m_logger.info(Sponge.getServer().getGameProfileManager().get(UUID.fromString("45705530-7874-4155-9528-87a3f902e267")).join().getPropertyMap().toString());
        m_configLoader = HoconConfigurationLoader.builder().setPath(m_configDir).build();
        m_logger.info(m_configDir.toString());
        if (!(m_userDataManager.loadData())) {
            m_logger.error("Load error!Maybe plugin can't normal run.");
            m_valid = false;
        }
        m_logger.info("Load complete.");
    }

    //Set Info Display Task
    @Listener
    public void onServerStarted(GameStartedServerEvent event) {
        //Set actionbar info
        //if
        Task.Builder actionbarInfoTaskBuilder = Task.builder();
        actionbarInfoTaskBuilder.execute(() -> {
            for (Player player : Sponge.getServer().getOnlinePlayers()) {
                func_SendParticleTitle(player, TextColors.WHITE);
            }
        });
        actionbarInfoTaskBuilder.intervalTicks(20);
        Task actionInfoTask = actionbarInfoTaskBuilder.submit(this);
        //Set scoreboard sidebar info
        //Set regen task
        Task.Builder regenTaskBuilder = Task.builder();
        regenTaskBuilder.execute(() -> {
            for (Player player : Sponge.getServer().getOnlinePlayers()) {
                func_RegenParticle(player);
            }
        });
        regenTaskBuilder.intervalTicks(20);
        Task regenTask = regenTaskBuilder.submit(this);
    }

    //SaveData
    @Listener
    public void onServerStop(GameStoppingServerEvent event) {
        if (m_valid) {
        }
    }

    //Listen player connect and check HashMap
    @Listener
    public void onPlayerJoinGame(ClientConnectionEvent.Join event) {
        if (!(m_userDataManager.isValidID(event.getTargetEntity().getUniqueId()))) {
            m_userDataManager.initUser(event.getTargetEntity().getUniqueId());
            m_logger.info("Initialization user:" + event.getTargetEntity().getName() + "(" + event.getTargetEntity().getUniqueId() + ")");
        }
    }

    //Listen player attack enemy
    @Listener(order = Order.EARLY, beforeModifications = true)
    public void onPlayerJumpAttack(DamageEntityEvent event) {
        //Process JumpAttack
        if (event.getCause().first(Player.class).isPresent() && (SimpleKit.getTargetEntityUUID(event) == event.getCause().first(Player.class).get().getUniqueId())) {
            double baseDamage = event.getBaseDamage();
            if (baseDamage >= 10) {
                event.setBaseDamage(baseDamage - 5);
                func_JumpAttack(event.getTargetEntity().getLocation(), (Player) event.getTargetEntity(), baseDamage);
            }
        }
    }

    //Listen damage event,if target is player,use grain to defend damage.
    @Listener(order = Order.LATE, beforeModifications = true)
    public void onDamageEntity(DamageEntityEvent event) {
        if (Sponge.getServer().getPlayer(event.getTargetEntity().getUniqueId()).isPresent() && event.getTargetEntity().getType() == EntityTypes.PLAYER) {
            UUID uuid = event.getTargetEntity().getUniqueId();
            double damageParticle = event.getBaseDamage() * 10;
            long playerParitcle = m_userDataManager.getParticlePoints(uuid);
            boolean fullProtect = damageParticle <= playerParitcle;
            event.setBaseDamage(fullProtect ? 0 : (damageParticle / 10));
            playerParitcle = fullProtect ? (long) (playerParitcle - damageParticle) : playerParitcle % 10;
            m_userDataManager.setParticlePoints(uuid, playerParitcle);
            if (!fullProtect) {
                m_userDataManager.setLastDamageTick(uuid, Sponge.getServer().getRunningTimeTicks());
            }
            func_SendParticleTitle(Sponge.getServer().getPlayer(uuid).get(), TextColors.RED);
        }
    }

    //Listen food regen particle.
    @Listener
    public void onUseItemStackFinish(UseItemStackEvent.Finish event) {
        if (event.getCause().first(Player.class).isPresent()) {
            UUID uuid = event.getCause().first(Player.class).get().getUniqueId();
            if (event.getItemStackInUse().getType() == ItemTypes.GOLDEN_APPLE && event.getItemStackInUse().get(Keys.GOLDEN_APPLE_TYPE).isPresent()) {
                long praticleBuffer = event.getItemStackInUse().get(Keys.GOLDEN_APPLE_TYPE).get().equals(GoldenApples.ENCHANTED_GOLDEN_APPLE) ? 200 : 100;
                m_userDataManager.setParticlePoints(uuid, m_userDataManager.getParticlePoints(uuid) + praticleBuffer);
                func_SendParticleTitle(Sponge.getServer().getPlayer(uuid).get(), TextColors.GREEN);
            }
        }
    }

    //Listen particle connect
    @Listener
    public void onParticleConnect(InteractItemEvent event) {
        if (event.getItemStack().get(Keys.REPRESENTED_PLAYER).isPresent()) {
            m_logger.info(event.getItemStack().get(Keys.REPRESENTED_PLAYER).get().getPropertyMap().get("textures").toString());
            if (event.getItemStack().get(Keys.REPRESENTED_PLAYER).get().getPropertyMap().get("textures").stream().anyMatch(profileProperty -> profileProperty.getValue().contains(COMMAND_BLOCK_HEAD))) {
                m_userDataManager.setParticlePoints(((Player) event.getSource()).getUniqueId(), m_userDataManager.getParticlePoints(((Player) event.getSource()).getUniqueId()) + 10000);
                event.setCancelled(true);
            }
        }
    }

    //Listen lock item pick
    @Listener
    public void onChangeInventoryLockItem(ChangeInventoryEvent event) {
        ItemStackSnapshot stack = event.getTransactions().get(0).getOriginal();
        if (stack.get(Keys.ITEM_LORE).isPresent() && stack.get(Keys.ITEM_LORE).get().get(0).compareTo(Text.of(TextColors.BLACK, "PF_LOCKITEM")) == 0) {
            event.setCancelled(true);
        }
    }

    //Listen lock item place
    @Listener
    public void onPlaceLockItem(ChangeBlockEvent.Place event) {
        ItemStackSnapshot stack = event.getContext().get(EventContextKeys.USED_ITEM).orElse(null);
        if (stack != null && stack.get(Keys.ITEM_LORE).isPresent() && stack.get(Keys.ITEM_LORE).get().get(0).compareTo(Text.of(TextColors.BLACK, "PF_LOCKPLACEITEM")) == 0) {
            event.setCancelled(true);
        }
    }

    //Particle Big Head
    public void onPlayerHappy(Player player) {
        player.setHelmet(m_userDataManager.exchangeHelmetCache(player.getUniqueId(), player.getHelmet().get()));
    }

    //Particle Jump
    public void onPlayerJump(Player player) {

    }

    //Reload Plugin Config
    @Listener
    public void reload(GameReloadEvent event) {
        m_logger.info("Reloading...");
        m_logger.info("Reload complete.");
    }

    //Refresh Particle Tips
    private void func_SendParticleTitle(Player player, TextColor color) {
        UUID uuid = player.getUniqueId();
        player.sendTitle(Title.builder().actionBar(Text.of(color, "粒子总量:" + m_userDataManager.getParticlePoints(uuid) + "/" + m_userDataManager.getParticlePointsMax(uuid))).build());
    }

    //Regen particle
    private void func_RegenParticle(Player player) {
        UUID uuid = player.getUniqueId();
        long points = m_userDataManager.getParticlePoints(uuid), regen = m_userDataManager.getParticlePointsRegen(uuid);
        if (points != m_userDataManager.getParticlePointsMax(uuid)) {
            if ((m_userDataManager.getLastDamageTick(uuid) + 60) <= Sponge.getServer().getRunningTimeTicks()) {
                if (points < m_userDataManager.getParticlePointsMax(uuid)) {
                    m_userDataManager.setParticlePoints(uuid, Math.min((points + regen), m_userDataManager.getParticlePointsMax(uuid)));
                }
                if (points > m_userDataManager.getParticlePointsMax(uuid)) {
                    m_userDataManager.setParticlePoints(uuid, Math.max((points - 10), m_userDataManager.getParticlePointsMax(uuid)));
                }
            }
        }
    }

    //JumpAttack
    private void func_JumpAttack(Location<World> location, Player from, double damage) {
        int offset = 0;
        if (damage < 16) {
            offset = 2;
        } else {
            offset = 3;
            damage = 16;
        }
        location.setPosition(location.getPosition().sub(offset, 0, offset));
        Collection<Entity> entities = from.getNearbyEntities(offset);
        entities.remove(from);
        for (Entity target : entities) {
            target.damage(damage, EntityDamageSource.builder().type(DamageTypes.ATTACK).explosion().entity(from).build());
            //from
        }
        from.getWorld().spawnParticles(ParticleEffect.builder().type(ParticleTypes.EXPLOSION).quantity(20).velocity(Vector3d.ZERO).offset(Vector3d.from(1, 0, 0)).build(), from.getPosition());
        from.getWorld().spawnParticles(ParticleEffect.builder().type(ParticleTypes.EXPLOSION).quantity(20).velocity(Vector3d.ZERO).offset(Vector3d.from(-1, 0, 0)).build(), from.getPosition());
        from.getWorld().spawnParticles(ParticleEffect.builder().type(ParticleTypes.EXPLOSION).quantity(20).velocity(Vector3d.ZERO).offset(Vector3d.from(0, 0, 1)).build(), from.getPosition());
        from.getWorld().spawnParticles(ParticleEffect.builder().type(ParticleTypes.EXPLOSION).quantity(20).velocity(Vector3d.ZERO).offset(Vector3d.from(0, 0, -1)).build(), from.getPosition());
    }
}

class CommandReturnInfo {
    public static Text SUCCESS = Text.of("Done.");
    public static Text ERROR_DATAERROR = Text.of("Error:Error data,maybe initialization has some error.Please restart server.");
    public static Text ERROR_ONLYUSER = Text.of("Error:Please don't use Console or CommandBlock run this command.");
    public static Text ERROR_USERANDBLOCK = Text.of("Error:Please don't use Console run this command.");
    public static Text ERROR_USERANDCONSOLE = Text.of("Error:Please don't use CommandBlock run this command.");
    public static Text ERROR_NOPP = Text.of("Error:No enough ParticlePoints.");
    public static Text ERROR_UNKOWN = Text.of("Error:Unkown error.");
    public static Text ERRPR_NOPER = Text.of("Error:No enough permission.");
}