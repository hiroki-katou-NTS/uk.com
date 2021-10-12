package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import lombok.val;
import nts.gul.reflection.FieldReflection;

public class ItemNoMap {

	private final Map<String, Integer> nameToNo = new HashMap<>();
	
	public static ItemNoMap reflection(Class<?> itemsClass) {
		
		ItemNoMap map = new ItemNoMap();
		
		for (val field : itemsClass.getDeclaredFields()) {
			
			if (field.getType() != int.class
					|| !Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			
			String itemName = field.getName();
			int itemNo = FieldReflection.getField(field, null);
			
			map.put(itemName, itemNo);
		}
		
		return map;
	}
	
	public ItemNoMap put(String name, int itemNo) {
		nameToNo.put(name, itemNo);
		return this;
	}
	
	public int getItemNo(String itemName) {
		return nameToNo.get(itemName);
	}
	
	public String getItemName(int itemNo) {
		return nameToNo.entrySet().stream()
				.filter(e -> e.getValue()== itemNo)
				.findFirst()
				.get()
				.getKey();
	}
	
	public ItemNoMap merge(ItemNoMap target) {
		
		val newMap = new ItemNoMap();
		
		target.nameToNo.forEach((name, itemNo) -> {
			newMap.put(name, itemNo);
		});
		
		return newMap;
	}
}
