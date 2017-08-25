package command.person.info.category;

import lombok.Value;
/**
 * The class UpdateNamePerInfoCtgCommand 
 * @author lanlt
 *
 */
@Value
public class UpdateNamePerInfoCtgCommand {
	private String categoryId;
	private String categoryName;
	private boolean isAbolition;
	

}
