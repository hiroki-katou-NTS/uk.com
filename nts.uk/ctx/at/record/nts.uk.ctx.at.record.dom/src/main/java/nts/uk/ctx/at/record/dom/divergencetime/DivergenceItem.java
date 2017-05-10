package nts.uk.ctx.at.record.dom.divergencetime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DivergenceItem {
	/*会社ID*/
	private String companyId;
	/*乖離項目ID*/
	private int divItemId;
	/*乖離項目名称*/
	private DivergenceTimeName divItemName;
	/*内部ID*/
	private int internalId;
	/*使用区分*/
	private UseSetting useAtr;
	/*乖離項目属性*/	
	private AttendanceAtr attendanceAtr;
}
