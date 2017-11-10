package nts.uk.shr.pereg.app.command;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.gul.reflection.ReflectionUtil;
import nts.gul.reflection.ReflectionUtil.Condition;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.PeregItem;

@Value
public class ItemsByCategory {

	/** category ID */
	private final String categoryId;
	
	/** Record Id, but this is null when new record */
	private final String recordId;
	
	/** input items */
	private final List<ItemValue> items;

	
	public Object createCommandForSystemDomain(Class<?> commandClass) {
		val inputsMap = this.items.stream()
				// exclude user defined
				.filter(item -> isDefinedBySystem(item.id()))
				.collect(Collectors.toMap(item -> item.id(), item -> item));
		
		val command = ReflectionUtil.newInstance(commandClass);
		
		ReflectionUtil.getStreamOfFieldsAnnotated(commandClass, Condition.ALL, PeregItem.class).forEach(field -> {
			field.setAccessible(true);
			val itemInfo = field.getAnnotation(PeregItem.class);
			
			String itemId = itemInfo.value();
			val inputItem = inputsMap.get(itemId);
			if (inputItem == null) {
				return; // go next field
			}
			
			ReflectionUtil.setFieldValue(field, command, inputItem.value());
			
			field.setAccessible(false);
		});
		
		return command;
	}
	
	public List<ItemValue> collectItemsDefinedByUser() {
		return this.items.stream()
				.filter(item -> isDefinedByUser(item.id()))
				.collect(Collectors.toList());
	}
	
	private static boolean isDefinedBySystem(String itemId) {
		return !isDefinedByUser(itemId);
	}
	
	private static boolean isDefinedByUser(String itemId) {
		return itemId.charAt(1) == 'O';
	}
}
