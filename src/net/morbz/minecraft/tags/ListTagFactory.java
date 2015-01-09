package net.morbz.minecraft.tags;

/*
* The MIT License (MIT)
* 
* Copyright (c) 2014-2015 Merten Peetz
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

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
