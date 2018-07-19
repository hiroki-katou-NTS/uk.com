package nts.uk.ctx.at.request.dom.vacation.history.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 取得期間の算出 OUTPUT
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PeriodVactionCalInfor {
	/**
	 * 日別実績 の期間
	 */
	private Optional<DatePeriod> recordDate;
	/**
	 * 暫定　の期間
	 */
	private Optional<DatePeriod> interimDate;
	/**
	 * 日別実績取得するフラグ: True: 取得する, False: 取得しない
	 */
	private boolean chkRecordData;
	/**
	 * 暫定データ取得するフラグ True: 取得する, False: 取得しない
	 */
	private boolean chkInterimData;
}
