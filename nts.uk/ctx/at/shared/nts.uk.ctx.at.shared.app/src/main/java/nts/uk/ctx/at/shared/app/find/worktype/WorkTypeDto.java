package nts.uk.ctx.at.shared.app.find.worktype;

import lombok.Value;
import nts.ctx.at.shared.dom.worktype.WorkType;
@Value
public class WorkTypeDto {
	/*会社ID*/
	private String companyId;
	/*勤務種類コード*/
	private String workTypeCode;
	/*ソート順*/
	private int sortOrder;
	/*勤務種類記号名*/
	private String symbolicName;
	/*勤務種類名称*/
	private String name;
	/*勤務種類略名*/
	private String abbreviationName;
	/*勤務種類備考*/
	private String memo;
	/*使用区分*/
	private int useAtr;
	
	public static WorkTypeDto fromDomain(WorkType domain){
		return new WorkTypeDto(domain.getCompanyId(),
					domain.getWorkTypeCode().v(),
					domain.getSortOrder(),
					domain.getSymbolicName().v(),
					domain.getName().v(),
					domain.getAbbreviationName().v(),
					domain.getMemo().v(),
					domain.getUseAtr().value
					);
	}
}
