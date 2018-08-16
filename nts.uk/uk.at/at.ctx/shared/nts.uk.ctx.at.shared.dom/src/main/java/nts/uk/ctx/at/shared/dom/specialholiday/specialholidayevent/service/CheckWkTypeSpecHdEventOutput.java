package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEvent;
@Getter
@Setter
@AllArgsConstructor
public class CheckWkTypeSpecHdEventOutput {

	//事象に応じた特休フラグ(true, false)
	private boolean specHdForEventFlag;
	//ドメインモデル「事象に対する特別休暇」 (optional)
	private Optional<SpecialHolidayEvent> specHdEvent;
	//特休枠NO (optional)
	private Optional<Integer> frameNo;
}
