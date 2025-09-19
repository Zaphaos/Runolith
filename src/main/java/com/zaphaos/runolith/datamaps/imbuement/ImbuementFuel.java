package com.zaphaos.runolith.datamaps.imbuement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ImbuementFuel(float speed) {
	public static final Codec<ImbuementFuel> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.FLOAT.fieldOf("amount").forGetter(ImbuementFuel::speed)
    ).apply(instance, ImbuementFuel::new));
}
