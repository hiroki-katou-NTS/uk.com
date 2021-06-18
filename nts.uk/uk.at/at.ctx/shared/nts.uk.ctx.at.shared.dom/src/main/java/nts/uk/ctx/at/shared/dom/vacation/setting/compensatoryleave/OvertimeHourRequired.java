package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OvertimeHourRequired {
	
		//使用区分 
		private boolean UseAtr;
		//時間設定
		private TimeSetting timeSetting;

}
