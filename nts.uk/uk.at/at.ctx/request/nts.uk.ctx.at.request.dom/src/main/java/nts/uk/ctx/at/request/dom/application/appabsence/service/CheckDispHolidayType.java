package nts.uk.ctx.at.request.dom.application.appabsence.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckDispHolidayType {

	//A4_3 - 年休設定
	private boolean yearManage;
	//A4_4 - 代休管理設定
	private boolean subHdManage;
	//A4_5 - 振休管理設定
	private boolean subVacaManage;
	//A4_8 - 積立年休設定
	private boolean retentionManage;
}
