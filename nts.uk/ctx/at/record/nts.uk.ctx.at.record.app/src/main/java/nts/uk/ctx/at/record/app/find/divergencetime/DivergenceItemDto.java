package nts.uk.ctx.at.record.app.find.divergencetime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceItem;

@Getter
@Setter
@AllArgsConstructor
public class DivergenceItemDto {
	/*会社ID*/
	private String companyId;
	/*乖離項目ID*/
	private int id;
	/*乖離項目名称*/
	private String name;
	/*内部ID*/
	private int dislayNumber;
	/*使用区分*/
	private int useAtr;
	/*乖離項目属性*/	
	private int attendanceAtr;
	
	public static DivergenceItemDto fromDomain(DivergenceItem domain) {
		return new DivergenceItemDto(
				domain.getCompanyId(),
				domain.getId(),
				domain.getName().toString(),
				domain.getDislayNumber(),
				domain.getUseAtr().value,
				domain.getAttendanceAtr().value);
	}
}
