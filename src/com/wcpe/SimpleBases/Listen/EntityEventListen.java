package com.wcpe.SimpleBases.Listen;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;

import com.wcpe.SimpleBases.Main;

public class EntityEventListen implements Listener {
	public EntityEventListen(Main a) {
		this.a = a;
	}

	private Main a;
	private static boolean swtich = true;
	@SuppressWarnings("unchecked")
	@EventHandler
	public void anti(EntitySpawnEvent e) {
		/**
		 * @author WCPE
		 * @AntiSpawnWorld
		 **/
		if ((boolean) a.getSetting().getAntiSpawnWorld().get("Enable")) {
			for (String st : (List<String>) a.getSetting().getAntiSpawnWorld().get("Worlds")) {
				String[] pd = st.split(";");
				if (e.getEntity().getWorld().getName().equals(pd[0])) {
					if (e.getLocation().distance(Bukkit.getWorld(pd[0]).getSpawnLocation()) <= Integer.valueOf(pd[1])) {
						if (e.getEntityType() != EntityType.DROPPED_ITEM) {
							e.setCancelled(true);
						}
						return;
					}
				}
			}
		}
		/**
		 * @author clear
		 * @AntiSpawnWorld
		 **/
		if ((boolean) a.getSetting().getClear().get("Enable")) {
			int c = 0;
			List<World> worlds = Bukkit.getWorlds();
			for (World world : worlds) {
				c += world.getEntities().size();
			}
			if (c >= (int) a.getSetting().getClear().get("ClearMobsNumber")) {
				clear();
			}
		}
	}

	@EventHandler
	public void cc(ItemSpawnEvent e) {
		/**
		 * @author WCPE
		 * @clear
		 **/
		if ((boolean) a.getSetting().getClear().get("Enable")) {
			int c = 0;
			List<World> worlds = Bukkit.getWorlds();
			for (World world : worlds) {
				c += world.getEntities().size();
			}
			if (c >= (int) a.getSetting().getClear().get("ClearDropNumber")) {
				clear();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void clear() {
		if ((boolean) a.getSetting().getClear().get("Enable")) {
			if (!swtich) {
				return;
			}
			swtich = false;
			new Thread(() -> {
				for (int t = (int) a.getSetting().getClear().get("ClearCountDown"); t >= 0; t--) {
					if (t == (int) a.getSetting().getClear().get("ClearCountDown") || t == t / 2 || t == 5 || t == 4
							|| t == 3 || t == 2 || t == 1 || t == 0) {
						Bukkit.broadcastMessage(a.getLang().getMessage().get("clearCountDown")
								.replaceAll("%t%", "" + t));
						if (t == 0) {
							List<World> worlds = Bukkit.getWorlds();
							int a = 0;
							int b = 0;
							for (World wd : worlds) {
								List<Entity> entity = wd.getEntities();
								for (Entity st : entity) {
									if (st.getType() == EntityType.DROPPED_ITEM) {
										a++;
										st.remove();
									}
									for (String ent : (List<String>) this.a.getSetting().getClear().get("ClearList")) {
										if (st.getType() == EntityType.valueOf(ent.toUpperCase())) {
											b++;
											st.remove();
										}
									}
								}
							}
							Bukkit.broadcastMessage(this.a.getLang().getMessage().get("clearDropMob")
									.replaceAll("%drop%", "" + a).replaceAll("%mobs%", "" + b));
							return;
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
			new Thread(() -> {
				try {
					Thread.sleep(((int) a.getSetting().getClear().get("ClearCooling")) * 1000L);
					swtich = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
	}

}
