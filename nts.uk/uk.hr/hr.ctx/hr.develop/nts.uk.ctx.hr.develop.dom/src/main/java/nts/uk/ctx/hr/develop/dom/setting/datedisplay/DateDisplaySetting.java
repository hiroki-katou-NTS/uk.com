package nts.uk.ctx.hr.develop.dom.setting.datedisplay;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
/**
 * @author anhdt
 * 日付表示設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DateDisplaySetting extends AggregateRoot implements IDateDisplaySetting {
	// プログラムID
	private String programId;
	// 会社ID
	private String companyId;
	// 終了日設定
	private DateDisplaySettingValue startDateSetting;
	// 開始日設定
	private Optional<DateDisplaySettingValue> endDateSetting;
	
	@Override
	public GeneralDate getDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DatePeriod getStartDateEndDate() {
		// TODO Auto-generated method stub
		return null;
	}
}
