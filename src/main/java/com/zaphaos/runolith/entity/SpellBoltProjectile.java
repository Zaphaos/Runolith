package com.zaphaos.runolith.entity;

import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class SpellBoltProjectile extends Projectile {

	protected SpellBoltProjectile(EntityType<? extends Projectile> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void defineSynchedData(Builder builder) {
		
	}
	
}

