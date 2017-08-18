package nts.uk.ctx.at.shared.dom.worktype;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@Getter
public class WorkType {
	/*会社ID*/
	private String companyId;
	/*勤務種類コード*/
	private WorkTypeCode workTypeCode;
	/*ソート順*/
	private int sortOrder;
	/*勤務種類記号名*/
	private WorkTypeSymbolicName symbolicName;
	/*勤務種類名称*/
	private WorkTypeName name;
	/*勤務種類略名*/
	private WorkTypeAbbreviationName abbreviationName;
	/*勤務種類備考*/
	private WorkTypeMemo memo;
	/*使用区分*/
	private DisplayAtr displayAtr;
	
	public WorkType(String companyId, WorkTypeCode workTypeCode, int sortOrder,
			WorkTypeSymbolicName symbolicName, WorkTypeName name, WorkTypeAbbreviationName abbreviationName,
			WorkTypeMemo memo, DisplayAtr displayAtr) {
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
	
	public static WorkType createSimpleFromJavaType(String companyId,
										String workTypeCode,
										int sortOrder,
										String symbolicName,
										String name,
										String abbreviationName,
										String memo,
										int displayAtr){
		return new WorkType(companyId, 
				new WorkTypeCode(workTypeCode),
				sortOrder,
				new WorkTypeSymbolicName(symbolicName),
				new WorkTypeName(name),
				new WorkTypeAbbreviationName(abbreviationName),
				new WorkTypeMemo(memo),
				EnumAdaptor.valueOf(displayAtr, DisplayAtr.class));
	}
}
