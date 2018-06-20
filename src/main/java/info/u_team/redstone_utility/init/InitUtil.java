package info.u_team.redstone_utility.init;

import net.minecraftforge.registries.IForgeRegistryEntry;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class InitUtil {

	@SuppressWarnings("unchecked")
	public static <T extends IForgeRegistryEntry<T>> Set<T> getRegistryEntries(Class<T> clazz, Class<?> init) {
		Set<T> set = new HashSet<>();
		try {
			for (Field field : init.getDeclaredFields()) {
				if (clazz.isAssignableFrom(field.getType())) {
					set.add((T) field.get(null));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return set;
	}

}