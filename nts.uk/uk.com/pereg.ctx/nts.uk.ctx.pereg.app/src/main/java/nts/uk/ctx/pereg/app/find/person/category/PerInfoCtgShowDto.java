package nts.uk.ctx.pereg.app.find.person.category;

import lombok.Value;

@Value
public class PerInfoCtgShowDto {
	private String id;
	private String categoryName;
	private int categoryType;
	private String categoryCode;
	private int isAbolition;
	private String categoryParentCode;
	private int initValMasterObjCls;
	private int addItemObjCls;
	private boolean  canAbolition;
	private int salaryUseAtr;
	private int personnelUseAtr;
	private int employmentUseAtr;
}
