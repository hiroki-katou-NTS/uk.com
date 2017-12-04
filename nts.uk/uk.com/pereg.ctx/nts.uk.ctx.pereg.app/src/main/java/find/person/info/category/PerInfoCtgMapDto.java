package find.person.info.category;

import lombok.Value;

@Value
public class PerInfoCtgMapDto {
	private String id;
	private String categoryCode;
	private String categoryName;
	private boolean alreadyCopy;

}
