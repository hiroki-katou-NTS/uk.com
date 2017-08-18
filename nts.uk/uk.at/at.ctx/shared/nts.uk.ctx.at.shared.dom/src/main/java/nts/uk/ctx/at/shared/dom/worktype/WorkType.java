/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * The Class WorkType.
 */
// 勤務種類
@Getter
public class WorkType {
	
	/** The company id. */
	/*会社ID*/
	private String companyId;
	
	/** The work type code. */
	/*勤務種類コード*/
	private WorkTypeCode workTypeCode;
	
	/** The sort order. */
	/*ソート順*/
	private int sortOrder;
	
	/** The symbolic name. */
	/*勤務種類記号名*/
	private WorkTypeSymbolicName symbolicName;
	
	/** The name. */
	/*勤務種類名称*/
	private WorkTypeName name;
	
	/** The abbreviation name. */
	/*勤務種類略名*/
	private WorkTypeAbbreviationName abbreviationName;
	
	/** The memo. */
	/*勤務種類備考*/
	private WorkTypeMemo memo;
	
	/** The display atr. */
	/*使用区分*/
	private DisplayAtr displayAtr;

	/** The daily work. */
	// 1日の勤務
	private DailyWork dailyWork;  

	/** The deprecate. */
	//廃止区分
	private DeprecateClassification deprecate; 
	
	/**
	 * Instantiates a new work type.
	 *
	 * @param companyId the company id
	 * @param workTypeCode the work type code
	 * @param sortOrder the sort order
	 * @param symbolicName the symbolic name
	 * @param name the name
	 * @param abbreviationName the abbreviation name
	 * @param memo the memo
	 * @param displayAtr the display atr
	 */
	public WorkType(String companyId, WorkTypeCode workTypeCode, int sortOrder,
			WorkTypeSymbolicName symbolicName, WorkTypeName name,
			WorkTypeAbbreviationName abbreviationName, WorkTypeMemo memo, DisplayAtr displayAtr) {
		super();
		this.companyId = companyId;
		this.workTypeCode = workTypeCode;
		this.sortOrder = sortOrder;
		this.symbolicName = symbolicName;
		this.name = name;
		this.abbreviationName = abbreviationName;
		this.memo = memo;
		this.displayAtr = displayAtr;
	}
	
	/**
	 * Creates the simple from java type.
	 *
	 * @param companyId the company id
	 * @param workTypeCode the work type code
	 * @param sortOrder the sort order
	 * @param symbolicName the symbolic name
	 * @param name the name
	 * @param abbreviationName the abbreviation name
	 * @param memo the memo
	 * @param displayAtr the display atr
	 * @return the work type
	 */
	public static WorkType createSimpleFromJavaType(String companyId, String workTypeCode,
			int sortOrder, String symbolicName, String name, String abbreviationName, String memo,
			int displayAtr,int workTypeUnit, int oneDay, int morning, int afternoon, int deprecate) {
		DailyWork dailyWork = new DailyWork();
		dailyWork.setWorkTypeUnit(EnumAdaptor.valueOf(workTypeUnit, WorkTypeUnit.class));
		dailyWork.setOneDay(EnumAdaptor.valueOf(oneDay, WorkTypeClassification.class));
		dailyWork.setMorning(EnumAdaptor.valueOf(morning, WorkTypeClassification.class));
		dailyWork.setAfternoon(EnumAdaptor.valueOf(afternoon, WorkTypeClassification.class));
		return new WorkType(companyId, new WorkTypeCode(workTypeCode), sortOrder,
				new WorkTypeSymbolicName(symbolicName), new WorkTypeName(name),
				new WorkTypeAbbreviationName(abbreviationName), new WorkTypeMemo(memo),
				EnumAdaptor.valueOf(displayAtr, DisplayAtr.class), dailyWork,
				EnumAdaptor.valueOf(deprecate, DeprecateClassification.class));
	}
	
	/**
	 * Instantiates a new work type.
	 *
	 * @param companyId the company id
	 * @param workTypeCode the work type code
	 * @param sortOrder the sort order
	 * @param symbolicName the symbolic name
	 * @param name the name
	 * @param abbreviationName the abbreviation name
	 * @param memo the memo
	 * @param displayAtr the display atr
	 * @param dailyWork the daily work
	 */
	public WorkType(String companyId, WorkTypeCode workTypeCode, int sortOrder,
			WorkTypeSymbolicName symbolicName, WorkTypeName name,
			WorkTypeAbbreviationName abbreviationName, WorkTypeMemo memo, DisplayAtr displayAtr,
			DailyWork dailyWork, DeprecateClassification deprecate) {
		super();
		this.companyId = companyId;
		this.workTypeCode = workTypeCode;
		this.sortOrder = sortOrder;
		this.symbolicName = symbolicName;
		this.name = name;
		this.abbreviationName = abbreviationName;
		this.memo = memo;
		this.displayAtr = displayAtr;
		this.dailyWork = dailyWork;
		this.deprecate = deprecate;
	}
}
