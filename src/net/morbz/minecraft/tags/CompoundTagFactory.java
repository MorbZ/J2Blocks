package net.morbz.minecraft.tags;

import java.util.HashMap;
import java.util.Map;

import org.jnbt.CompoundTag;
import org.jnbt.Tag;

/** 
 * This class generates a Compound tag.
 * 
 * @author MorbZ
 */
public class CompoundTagFactory implements ITagProvider {
	private Map<String, Tag> values;
	private String name;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param name The name of this tag
	 */
	public CompoundTagFactory(String name) {
		values = new HashMap<String, Tag>();
		this.name = name;
	}
	
	/**
	 * Set a tag. Overwrites other tags that have the same tag name.
	 * 
	 * @param tag The tag to set
	 */
	public void set(Tag tag) {
		values.put(tag.getName(), tag);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tag getTag() {
		return new CompoundTag(name, values);
	}
}
