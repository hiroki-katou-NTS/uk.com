package nts.uk.ctx.sys.assist.infra.entity.datarestoration;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.assist.dom.datarestoration.RestorationTarget;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 復旧対象
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPDT_RECOVER_TARGET_COND")
public class SspdtRecoverTargetCond extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public SspdtRecoverTargetCondPk restorationTargetPk;

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

	public static SspdtRecoverTargetCond toEntity(RestorationTarget domain) {
		Integer recoveryTargetStartYear     = null;
		Integer recoveryTargetEndYear       = null;
		Integer recoveryTargetStartYM       = null;
		Integer recoveryTargetEndYM         = null;
		GeneralDate recoveryTargetStartDate = null;
		GeneralDate recoveryTargetEndDate   = null;
		if (domain.getRecoveryTargetYear().isPresent()) {
			if (!Objects.isNull(domain.getRecoveryTargetYear().get().start())) {
				recoveryTargetStartYear = domain.getRecoveryTargetYear().get().start().v();
			}
			if (!Objects.isNull(domain.getRecoveryTargetYear().get().end())) {
				recoveryTargetEndYear   = domain.getRecoveryTargetYear().get().end().v();
			}
		}
		if (domain.getRecoveryTargetYM().isPresent()) {
			if (!Objects.isNull(domain.getRecoveryTargetYM().get().start())) {
				recoveryTargetStartYM = domain.getRecoveryTargetYM().get().start().v();
			}
			if (!Objects.isNull(domain.getRecoveryTargetYM().get().end())) {
				recoveryTargetEndYM   = domain.getRecoveryTargetYM().get().end().v();
			}
		}
		if(domain.getRecoveryTargetDate().isPresent()){
			if (!Objects.isNull(domain.getRecoveryTargetDate().get().start())) {
				recoveryTargetStartDate = domain.getRecoveryTargetDate().get().start();
			}
			if (!Objects.isNull(domain.getRecoveryTargetDate().get().end())) {
				recoveryTargetEndDate   = domain.getRecoveryTargetDate().get().end();
			}
		}
		return new SspdtRecoverTargetCond(
				new SspdtRecoverTargetCondPk(domain.getDataRecoveryProcessId(), 
				domain.getRecoveryCategory()),
				domain.getRetentionPeriodIndicator().value,
				recoveryTargetStartYear, recoveryTargetEndYear,
				recoveryTargetStartYM, recoveryTargetEndYM,
				recoveryTargetStartDate, recoveryTargetEndDate);
	}
}
