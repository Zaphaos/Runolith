package com.zaphaos.runolith.datamaps.imbuement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ImbuementFuel(float speed, int tier) {
	public static final Codec<ImbuementFuel> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.FLOAT.fieldOf("speed").forGetter(ImbuementFuel::speed),
			Codec.INT.fieldOf("tier").forGetter(ImbuementFuel::tier)
    ).apply(instance, ImbuementFuel::new));
}
