package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.adapter.specificdatesetting.RecSpecificDateSettingImport;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.BaseAutoCalSetting;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
