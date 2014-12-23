package net.morbz.minecraft.tags;

import org.jnbt.Tag;

/**
 * An interface for all classes that support a NBT-Tag representation.
 * 
 * @author MorbZ
 */
public interface ITagProvider {
	/**
	 * Returns an instance of a subclass of the NBT-Tag class.
	 * 
	 * @return The Tag representation of this class
	 */
	public Tag getTag();
}
