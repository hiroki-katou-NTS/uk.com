package nts.uk.ctx.sys.assist.infra.entity.datarestoration;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.assist.dom.datarestoration.RestorationTarget;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 復旧対象
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_RESTORATION_TARGET")
public class SspmtRestorationTarget extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public SspmtRestorationTargetPk restorationTargetPk;

	/**
	 * 保存期間区分
	 */
	@Basic(optional = false)
	@Column(name = "RETENTION_PERIOD_INDICATOR")
	public int retentionPeriodIndicator;

	/**
	 * 復旧対象開始年
	 */
	@Basic(optional = true)
	@Column(name = "RECOVERY_TARGET_START_YEAR")
	public Integer recoveryTargetStartYear;

	/**
	 * 復旧対象終了年
	 */
	@Basic(optional = true)
	@Column(name = "RECOVERY_TARGET_END_YEAR")
	public Integer recoveryTargetEndYear;

	/**
	 * 復旧対象開始年月
	 */
	@Basic(optional = true)
	@Column(name = "RECOVERY_TARGET_START_YM")
	public Integer recoveryTargetStartYm;

	/**
	 * 復旧対象終了年月
	 */
	@Basic(optional = true)
	@Column(name = "RECOVERY_TARGET_END_YM")
	public Integer recoveryTargetEndYm;

	/**
	 * 復旧対象開始日
	 */
	@Basic(optional = true)
	@Column(name = "RECOVERY_TARGET_START_DATE")
	public GeneralDate recoveryTargetStartDate;

	/**
	 * 復旧対象終了日
	 */
	@Basic(optional = true)
	@Column(name = "RECOVERY_TARGET_END_DATE")
	public GeneralDate recoveryTargetEndDate;

	@Override
	protected Object getKey() {
		return restorationTargetPk;
	}

	public RestorationTarget toDomain() {
		return new RestorationTarget(this.restorationTargetPk.dataRecoveryProcessId,
				this.restorationTargetPk.recoveryCategory, this.retentionPeriodIndicator, this.recoveryTargetStartYear,
				this.recoveryTargetEndYear, this.recoveryTargetStartYm, this.recoveryTargetEndYm,
				this.recoveryTargetStartDate, this.recoveryTargetEndDate);
	}

	public static SspmtRestorationTarget toEntity(RestorationTarget domain) {
		return new SspmtRestorationTarget(
				new SspmtRestorationTargetPk(domain.getDataRecoveryProcessId(), domain.getRecoveryCategory()),
				domain.getRetentionPeriodIndicator().value,
				domain.getRecoveryTargetStartYear().orElse(null),
				domain.getRecoveryTargetEndYear().orElse(null),
				domain.getRecoveryTargetStartYM().orElse(null).v(),
				domain.getRecoveryTargetEndYM().orElse(null).v(),
				domain.getRecoveryTargetStartDate().orElse(null),
				domain.getRecoveryTargetEndDate().orElse(null));
	}
}
