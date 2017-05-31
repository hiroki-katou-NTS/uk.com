package nts.uk.ctx.at.shared.app.find.worktype;

import lombok.Value;
import nts.ctx.at.shared.dom.worktype.WorkType;
@Value
public class WorkTypeDto {
	/*会社ID*/
	private String companyId;
	/*勤務種類コード*/
	private String workTypeCode;
	/*勤務種類コード*/
	private String sortOrder;
	/*勤務種類コード*/
	private String symbolicName;
	/*勤務種類コード*/
	private String name;
	/*勤務種類コード*/
	private String abbreviationName;
	/*勤務種類コード*/
	private String memo;
	/*勤務種類コード*/
	private int useAtr;
	
	public static WorkTypeDto fromDomain(WorkType domain){
		return new WorkTypeDto(domain.getCompanyId(),
					domain.getWorkTypeCode().v(),
					domain.getSortOrder().v(),
					domain.getSymbolicName().v(),
					domain.getName().v(),
					domain.getAbbreviationName().v(),
					domain.getMemo().v(),
					domain.getUseAtr().value
					);
	}
}
