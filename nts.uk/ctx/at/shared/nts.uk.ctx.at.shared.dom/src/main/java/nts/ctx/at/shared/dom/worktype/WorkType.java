package nts.ctx.at.shared.dom.worktype;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;

@Getter
public class WorkType {
	/*会社ID*/
	private String companyId;
	/*勤務種類コード*/
	private WorkTypeCode workTypeCode;
	/*勤務種類コード*/
	private WorkTypeCode sortOrder;
	/*勤務種類コード*/
	private WorkTypeSymbolicName symbolicName;
	/*勤務種類コード*/
	private WorkTypeName name;
	/*勤務種類コード*/
	private WorkTypeAbbreviationName abbreviationName;
	/*勤務種類コード*/
	private WorkTypeMemo memo;
	/*勤務種類コード*/
	private UseSetting useAtr;
	
	public WorkType(String companyId, WorkTypeCode workTypeCode, WorkTypeCode sortOrder,
			WorkTypeSymbolicName symbolicName, WorkTypeName name, WorkTypeAbbreviationName abbreviationName,
			WorkTypeMemo memo, UseSetting useAtr) {
		super();
		this.companyId = companyId;
		this.workTypeCode = workTypeCode;
		this.sortOrder = sortOrder;
		this.symbolicName = symbolicName;
		this.name = name;
		this.abbreviationName = abbreviationName;
		this.memo = memo;
		this.useAtr = useAtr;
	}
	
	public static WorkType createSimpleFromJavaType(String companyId,
										String workTypeCode,
										String sortOrder,
										String symbolicName,
										String name,
										String abbreviationName,
										String Memo,
										int useAtr){
		return new WorkType(companyId, 
				new WorkTypeCode(workTypeCode),
				new WorkTypeCode(sortOrder),
				new WorkTypeSymbolicName(symbolicName),
				new WorkTypeName(name),
				new WorkTypeAbbreviationName(abbreviationName),
				new WorkTypeMemo(Memo),
				EnumAdaptor.valueOf(useAtr, UseSetting.class));
	}
}
