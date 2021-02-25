package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SixtyHourSettingOutput {

	/** 60H超休管理区分(true, false) */
	private boolean sixtyHourOvertimeMngDistinction;

	/** 60H超休消化単位 */
	private int sixtyHourOverDigestion;

}
