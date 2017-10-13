package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VerticalTimeSettingDto {

	/** 会社ID **/
	private String companyId;
	
	/****/
	private int fixedItemAtr;
	
	/** 表示区分 **/
	private int displayAtr;
	
	/** 時刻 **/
	private int startClock;

}
