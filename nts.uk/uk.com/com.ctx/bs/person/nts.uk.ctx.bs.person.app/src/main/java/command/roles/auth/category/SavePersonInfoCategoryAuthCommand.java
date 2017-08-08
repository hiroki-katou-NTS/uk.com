package command.roles.auth.category;

import java.util.List;

import command.roles.auth.item.PersonInfoItemAuthCommand;
import lombok.Getter;
import lombok.Setter;

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

	List<PersonInfoItemAuthCommand> roleItemList;
}
