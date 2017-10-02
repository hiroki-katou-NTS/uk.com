package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FixedVerticalSettingDto {
	
	/* 会社ID */
	private String companyId;
	
	/**/
	private int fixedVerticalNo;
	
	/*利用区分*/
	private int useAtr;
	
	/*固定項目区分*/
	private int fixedItemAtr;
	
	/*縦計詳細設定*/
	private int verticalDetailedSettings;
	
}
