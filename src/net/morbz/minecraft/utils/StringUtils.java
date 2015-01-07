package net.morbz.minecraft.utils;

/**
 * A class for common string methods.
 * 
 * @author MorbZ
 */
public class StringUtils {
	/**
	 * Joins an array of strings to a single String where the parts are seperated by the glue 
	 * string.
	 * 
	 * @param parts The string parts to be joined
	 * @param glue The seperator between the parts
	 * @return The joined string
	 */
	public static String join(String[] parts, String glue) {
		String str = "";
		boolean isFirst = true;
		for(String part : parts) {
			if(!isFirst) {
				str += glue;
			} else {
				isFirst = false;
			}
			str += part;
		}
		return str;
	}
}
