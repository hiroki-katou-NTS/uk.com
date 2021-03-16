package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.SpecialHolidayEventDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaxHolidayDayParamMobile {
	// 会社ID
	private String companyId;
	// 特休枠NO
	private Integer specHdFrame;
	// メインモデル「事象に対する特別休暇」
	private SpecialHolidayEventDto specHdEvent;
	// 続柄コード
	private String relationCDOp;
}
