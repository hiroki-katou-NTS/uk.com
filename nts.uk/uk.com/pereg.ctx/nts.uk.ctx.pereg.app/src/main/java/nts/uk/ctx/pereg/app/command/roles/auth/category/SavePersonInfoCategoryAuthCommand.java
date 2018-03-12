package nts.uk.ctx.pereg.app.command.roles.auth.category;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pereg.app.command.roles.auth.item.PersonInfoItemAuthCommand;

@Setter
@Getter
public class SavePersonInfoCategoryAuthCommand {

	private String categoryId;

	private String categoryName;

	private int categoryType;

	private int setting;

	private int allowPersonRef;

	private int allowOtherRef;

	private int allowOtherCompanyRef;

	private int selfPastHisAuth;

	private int selfFutureHisAuth;

	private int selfAllowDelHis;

	private int selfAllowAddHis;

	private int otherPastHisAuth;

	private int otherFutureHisAuth;

	private int otherAllowDelHis;

	private int otherAllowAddHis;

	private int selfAllowDelMulti;

	private int selfAllowAddMulti;

	private int otherAllowDelMulti;

	private int otherAllowAddMulti;

	List<PersonInfoItemAuthCommand> items;
}
