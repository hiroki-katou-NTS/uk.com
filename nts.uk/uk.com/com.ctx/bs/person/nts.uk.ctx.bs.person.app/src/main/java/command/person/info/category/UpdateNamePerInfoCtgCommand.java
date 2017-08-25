package command.person.info.category;

import lombok.Value;

@Value
public class UpdateNamePerInfoCtgCommand {
	private String categoryId;
	private String categoryName;
	private boolean isAbolition;
	

}
