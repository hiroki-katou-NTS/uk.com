package command.person.info.category;

import lombok.Getter;

@Getter
public class UpdatePerInfoCtgCommand {
	private String id;
	private String categoryName;
	private int categoryType;
}
