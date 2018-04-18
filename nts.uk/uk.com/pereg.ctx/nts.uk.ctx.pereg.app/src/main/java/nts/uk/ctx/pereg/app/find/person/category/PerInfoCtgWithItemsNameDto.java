package nts.uk.ctx.pereg.app.find.person.category;

import java.util.List;

import lombok.Value;

@Value
public class PerInfoCtgWithItemsNameDto {
	private String id;
	private String categoryName;
	private int categoryType;
	private int isFixed;
	private int personEmployeeType;
	private boolean initValMasterObjCls;
	private boolean addItemObjCls;
	private List<String> itemNameList;
	private boolean isChangeAbleCtgType;
}
