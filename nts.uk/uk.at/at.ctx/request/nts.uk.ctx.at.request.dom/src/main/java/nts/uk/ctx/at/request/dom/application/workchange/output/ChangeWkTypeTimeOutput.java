package nts.uk.ctx.at.request.dom.application.workchange.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeWkTypeTimeOutput {
	
	/**
	 * 就業時間帯の必須区分
	 */
	private SetupType setupType;
	
	/**
	 * ドメインモデル「所定時間設定」<Optional>
	 */
	private Optional<PredetemineTimeSetting> opPredetemineTimeSetting;
	
}
