package net.morbz.minecraft.tags;

import java.util.ArrayList;

import org.jnbt.ListTag;
import org.jnbt.Tag;

/**
 * This class generates a ListTag.
 * 
 * @author MorbZ
 */
public class ListTagFactory implements ITagProvider {
	private ArrayList<Tag> values;
	private Class<? extends Tag> type;
	private String name;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param name The name of this tag
	 * @param type The type of tags that will be in the list
	 */
	public ListTagFactory(String name, Class<? extends Tag> type) {
		values = new ArrayList<Tag>();
		this.type = type;
		this.name = name;
	}
	
	/**
	 * Adds a tag to the list. There can be multiple tags with the same name.
	 * 
	 * @param tag The tag to add
	 */
	public void add(Tag tag) {
		values.add(tag);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tag getTag() {
		return new ListTag(name, type, values);
	}
}
