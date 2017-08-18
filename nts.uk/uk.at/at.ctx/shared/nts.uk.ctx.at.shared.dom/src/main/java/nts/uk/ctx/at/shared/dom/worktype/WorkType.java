package nts.uk.ctx.at.shared.dom.worktype;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@Getter
public class WorkType {

	/* 会社ID */
	private String companyId;
	/* 勤務種類コード */
	private WorkTypeCode workTypeCode;
	/* 勤務種類名称 */
	private WorkTypeName name;
	/* 勤務種類略名 */
	private WorkTypeAbbreviationName abbreviationName;
	/* 勤務種類記号名 */
	private WorkTypeSymbolicName symbolicName;
	/* 廃止区分 */
	private DisplayAtr displayAtr;
	/* 勤務種類備考 */
	private WorkTypeMemo memo;
	/* 並び順 */
	private int sortOrder;
	/* 勤務の単位 */
	private WorkAtr workAtr;

	private DutyTypeAtr oneDayCls;

	private DutyTypeAtr afternoonCls;

	private DutyTypeAtr morningCls;

	public WorkType(String companyId, WorkTypeCode workTypeCode, WorkTypeName name,
			WorkTypeAbbreviationName abbreviationName, WorkTypeSymbolicName symbolicName, DisplayAtr displayAtr,
			WorkTypeMemo memo, int sortOrder, WorkAtr workAtr, DutyTypeAtr oneDayCls, DutyTypeAtr afternoonCls,
			DutyTypeAtr morningCls) {
		super();
		this.companyId = companyId;
		this.workTypeCode = workTypeCode;
		this.name = name;
		this.abbreviationName = abbreviationName;
		this.symbolicName = symbolicName;
		this.displayAtr = displayAtr;
		this.memo = memo;
		this.sortOrder = sortOrder;
		this.workAtr = workAtr;
		this.oneDayCls = oneDayCls;
		this.afternoonCls = afternoonCls;
		this.morningCls = morningCls;
	}

	public static WorkType createSimpleFromJavaType(String companyId,
										String workTypeCode,
										int sortOrder,
										String symbolicName,
										String name,
										String abbreviationName,
										String memo,
										int displayAtr,
										int workAtr,
										int oneDayCls,
										int afternoonCls,
										int morningCls){
		        
		 return new WorkType(companyId,
				             new WorkTypeCode(workTypeCode),
				             new WorkTypeName(name),
				             new WorkTypeAbbreviationName(abbreviationName),
				             new WorkTypeSymbolicName(symbolicName),
				             EnumAdaptor.valueOf(displayAtr, DisplayAtr.class),
				             new WorkTypeMemo(memo),
				             sortOrder,
				             EnumAdaptor.valueOf(workAtr, WorkAtr.class),
                             EnumAdaptor.valueOf(oneDayCls, DutyTypeAtr.class),
                             EnumAdaptor.valueOf(afternoonCls, DutyTypeAtr.class),
				             EnumAdaptor.valueOf(morningCls, DutyTypeAtr.class));
	}

}
