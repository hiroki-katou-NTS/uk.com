package nts.uk.ctx.pereg.pub.person.info.ctg;

import lombok.Value;

@Value
public class PerInfoCtgShowExport {
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
	// 0:固定なし(Not Fixed), 1:固定(Fixed)
	private int fixed;
}
