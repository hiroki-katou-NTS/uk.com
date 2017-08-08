package command.person.info.category;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryName;
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryType;

@Getter
public class AddPerInfoCtgCommand {
	private CategoryName categoryName;
	private CategoryType categoryType;
}
