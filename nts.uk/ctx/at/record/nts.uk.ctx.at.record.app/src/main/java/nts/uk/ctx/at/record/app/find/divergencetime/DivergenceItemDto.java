package nts.uk.ctx.at.record.app.find.divergencetime;

import lombok.Value;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceItem;
@Value
public class DivergenceItemDto {
	/*会社ID*/
	private String companyId;
	/*乖離項目ID*/
	private int divItemId;
	/*乖離項目名称*/
	private String divItemName;
	/*内部ID*/
	private int dislayNumber;
	/*使用区分*/
	private int useAtr;
	/*乖離項目属性*/	
	private int attendanceAtr;
	public static DivergenceItemDto fromDomain(DivergenceItem domain){
		return new DivergenceItemDto(domain.getCompanyId(),
					domain.getDivItemId(),
					domain.getDivItemName().v(),
					domain.getDislayNumber(),
					domain.getUseAtr().value,
					domain.getAttendanceAtr().value
					);
	}
}
