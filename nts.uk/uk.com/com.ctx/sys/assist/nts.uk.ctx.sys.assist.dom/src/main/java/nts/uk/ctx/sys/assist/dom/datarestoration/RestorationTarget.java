package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Objects;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.Year;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.com.time.calendar.period.YearPeriod;

/**
 * 復旧対象
 */
/**
 * @author sang.nv
 *
 */
@AllArgsConstructor
@Getter
public class RestorationTarget extends DomainObject {

	/**
	 * データ復旧処理ID
	 */
	private String dataRecoveryProcessId;

	/**
	 * 復旧カテゴリ
	 */
	private String recoveryCategory;

	/**
	 * 保存期間区分
	 */
	private RetentionPeriodIndicator retentionPeriodIndicator;

	/**
	 * 復旧対象年
	 */
	private Optional<YearPeriod> recoveryTargetYear;

	/**
	 * 復旧対象年月
	 */
	private Optional<YearMonthPeriod> recoveryTargetYM;

	/**
	 * 復旧対象日
	 */
	private Optional<DatePeriod> recoveryTargetDate;

	public RestorationTarget(String dataRecoveryProcessId, String recoveryCategory, int retentionPeriodIndicator,
			Integer recoveryTargetStartYear, Integer recoveryTargetEndYear, Integer recoveryTargetStartYM,
			Integer recoveryTargetEndYM, GeneralDate recoveryTargetStartDate, GeneralDate recoveryTargetEndDate) {
		this.dataRecoveryProcessId    = dataRecoveryProcessId;
		this.recoveryCategory         = recoveryCategory;
		this.retentionPeriodIndicator = EnumAdaptor.valueOf(retentionPeriodIndicator, RetentionPeriodIndicator.class);
		// 復旧対象年
		if (Objects.isNull(recoveryTargetStartYear) && Objects.isNull(recoveryTargetEndYear)) {
			this.recoveryTargetYear = Optional.empty();
		} else {
			this.recoveryTargetYear = Optional.of(new YearPeriod(
					!Objects.isNull(recoveryTargetStartYear) ? new Year(recoveryTargetStartYear) : null,
					!Objects.isNull(recoveryTargetEndYear)   ? new Year(recoveryTargetEndYear)   : null));
		}
		// 復旧対象年月
		if (Objects.isNull(recoveryTargetStartYM) && Objects.isNull(recoveryTargetEndYM)) {
			this.recoveryTargetYM = Optional.empty();
		} else {
			this.recoveryTargetYM  = Optional.of(new YearMonthPeriod(
					!Objects.isNull(recoveryTargetStartYM) ? new YearMonth(recoveryTargetStartYM) : null,
					!Objects.isNull(recoveryTargetEndYM)   ? new YearMonth(recoveryTargetEndYM)   : null));
		}
		// 復旧対象日
		if (!Objects.isNull(recoveryTargetStartDate) && !Objects.isNull(recoveryTargetEndDate)) {
			this.recoveryTargetDate = Optional.empty();
		} else {
			this.recoveryTargetDate = Optional.of(new DatePeriod(
					!Objects.isNull(recoveryTargetStartDate) ? recoveryTargetStartDate : null,
					!Objects.isNull(recoveryTargetEndDate)   ? recoveryTargetEndDate   : null));
		}
	}
}
