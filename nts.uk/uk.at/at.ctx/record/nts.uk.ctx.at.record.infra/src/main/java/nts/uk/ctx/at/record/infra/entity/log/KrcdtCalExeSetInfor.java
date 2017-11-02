package nts.uk.ctx.at.record.infra.entity.log;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author hieult
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_CAL_EXE_SET_INFOR")
public class KrcdtCalExeSetInfor extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KrcdtCalExeSetInforPK krcdtCalExeSetInforPK;

	/** 確定済みの場合にも強制的に反映する */
	@Column(name = "REF_EVEN_CONFIRM")
	public boolean alsoForciblyReflectEvenIfItIsConfirmed;

	/** 作成区分 */
	@Column(name = "CREATION_TYPE")
	public int creationType;

	/** マスタ再設定 */
	@Column(name = "MASTER_RECONFIG")
	public boolean masterReconfiguration;

	/** 休業再設定 */
	@Column(name = "CLOSED_HOLIDAYS")
	public boolean closedHolidays;

	/** 就業時間帯再設定 */
	@Column(name = "RESET_WORK_HOURS")
	public boolean resettingWorkingHours;

	/** 打刻のみ再度反映 */
	@Column(name = "REF_NUMBER_FINGER_CHECK")
	public boolean reflectsTheNumberOfFingerprintChecks;

	/** 特定日区分再設定 */
	@Column(name = "SPEC_DATE_CLASS_RESET")
	public boolean specificDateClassificationResetting;

	/** 申し送り時間再設定 */
	@Column(name = "RESET_TIME_ASSIGNMENT")
	public boolean resetTimeAssignment;

	/** 育児・介護短時間再設定 */
	@Column(name = "RESET_TIME_CHILD_OR_NURCE")
	public boolean resetTimeChildOrNurseCare;

	/** 計算区分再設定 */
	@Column(name = "CALCULA_CLASS_RESET")
	public boolean calculationClassificationResetting;

	@Override
	protected Object getKey() {
		return this.krcdtCalExeSetInforPK;
	}

	

}
