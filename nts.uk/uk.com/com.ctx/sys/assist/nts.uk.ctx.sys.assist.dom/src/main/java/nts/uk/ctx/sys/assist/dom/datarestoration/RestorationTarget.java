package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Objects;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

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
	 * 復旧対象開始年
	 */
	private Optional<Integer> recoveryTargetStartYear;

	/**
	 * 復旧対象終了年
	 */
	private Optional<Integer> recoveryTargetEndYear;

	/**
	 * 復旧対象開始年月
	 */
	private Optional<YearMonth> recoveryTargetStartYM;

	/**
	 * 復旧対象終了年月
	 */
	private Optional<YearMonth> recoveryTargetEndYM;

	/**
	 * 復旧対象開始日
	 */
	private Optional<GeneralDate> recoveryTargetStartDate;

	/**
	 * 復旧対象終了日
	 */
	private Optional<GeneralDate> recoveryTargetEndDate;

	public RestorationTarget(String dataRecoveryProcessId, String recoveryCategory,
			int retentionPeriodIndicator, Integer recoveryTargetStartYear,
			Integer recoveryTargetEndYear, Integer recoveryTargetStartYM,
			Integer recoveryTargetEndYM, GeneralDate recoveryTargetStartDate,
			GeneralDate recoveryTargetEndDate) {
		this.dataRecoveryProcessId    = dataRecoveryProcessId;
		this.recoveryCategory         = recoveryCategory;
		this.retentionPeriodIndicator = EnumAdaptor.valueOf(retentionPeriodIndicator, RetentionPeriodIndicator.class);
		this.recoveryTargetStartYear  = Optional.ofNullable(recoveryTargetStartYear);
		this.recoveryTargetEndYear    = Optional.ofNullable(recoveryTargetEndYear);
		this.recoveryTargetStartYM    = Objects.isNull(recoveryTargetStartYM) ? Optional.empty() : Optional.of(new YearMonth(recoveryTargetStartYM));
		this.recoveryTargetEndYM      = Objects.isNull(recoveryTargetEndYM) ? Optional.empty() : Optional.of(new YearMonth(recoveryTargetEndYM));
		this.recoveryTargetStartDate  = Optional.ofNullable(recoveryTargetStartDate);
		this.recoveryTargetEndDate    = Optional.ofNullable(recoveryTargetEndDate);
	}
}
