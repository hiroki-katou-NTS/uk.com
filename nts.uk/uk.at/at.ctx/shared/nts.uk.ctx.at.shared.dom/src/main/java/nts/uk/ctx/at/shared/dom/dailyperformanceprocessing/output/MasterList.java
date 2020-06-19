package nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.specificdatesetting.RecSpecificDateSettingImport;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.BaseAutoCalSetting;

/**
 * 期間ごとのマスタ一覧
 * @author nampt
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class MasterList {
	
	private DatePeriod datePeriod;
	
	/** 自動計算設定 **/
	private BaseAutoCalSetting baseAutoCalSetting;
	
	/** 加給設定 **/
	private Optional<BonusPaySetting> bonusPaySettingOpt;
	
	/** 特定日設定 **/
	private Optional<RecSpecificDateSettingImport> specificDateSettingImport;
}
