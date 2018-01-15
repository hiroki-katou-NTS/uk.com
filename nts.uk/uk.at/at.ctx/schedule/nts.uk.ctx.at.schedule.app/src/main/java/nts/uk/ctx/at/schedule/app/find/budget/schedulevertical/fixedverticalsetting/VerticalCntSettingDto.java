package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VerticalCntSettingDto {
	/** 会社ID **/
	private String companyId;
	
	/** 固定項目区分 **/
	private int fixedItemAtr;
	
	private int verticalCountNo;

}
