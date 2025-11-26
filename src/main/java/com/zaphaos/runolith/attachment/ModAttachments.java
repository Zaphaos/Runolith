package com.zaphaos.runolith.attachment;

import java.util.*;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;
import com.zaphaos.runolith.Runolith;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttachments {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Runolith.MOD_ID);
	
	public static final Supplier<AttachmentType<ArrayList<String>>> KNOWN_SPELL_PARTS =
	        ATTACHMENT_TYPES.register(
	                "known_spell_parts",
	                () -> AttachmentType.<ArrayList<String>>builder((Supplier<ArrayList<String>>) ArrayList::new)
	                        .serialize(Codec.STRING.listOf().xmap(ArrayList::new, list -> list))
	                        .copyOnDeath()
	                        .build()
	        );
}
