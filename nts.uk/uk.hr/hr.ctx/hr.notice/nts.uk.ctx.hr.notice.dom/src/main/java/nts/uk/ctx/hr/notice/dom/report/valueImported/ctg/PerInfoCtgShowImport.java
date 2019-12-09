package nts.uk.ctx.hr.notice.dom.report.valueImported.ctg;

import lombok.Value;

@Value
public class PerInfoCtgShowImport {
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
