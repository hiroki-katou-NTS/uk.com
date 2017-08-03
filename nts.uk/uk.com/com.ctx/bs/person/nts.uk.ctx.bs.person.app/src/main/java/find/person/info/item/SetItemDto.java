package find.person.info.item;

import java.util.List;

import lombok.Value;

@Value
public class SetItemDto {
	private int itemType;
	private List<String> items;
}
