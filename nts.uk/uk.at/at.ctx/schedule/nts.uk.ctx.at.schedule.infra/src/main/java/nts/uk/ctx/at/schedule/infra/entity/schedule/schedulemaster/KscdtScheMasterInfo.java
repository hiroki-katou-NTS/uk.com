package nts.uk.ctx.at.schedule.infra.entity.schedule.schedulemaster;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedule;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定マスタ情報
 * 
 * @author TanLV
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSCDT_SCHE_MASTER")
public class KscdtScheMasterInfo extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscdtScheMasterInfoPK kscdtScheMasterInfoPk;

	@Column(name = "EMP_CD")
	public String employmentCd;

	@Column(name = "CLS_CD")
	public String classificationCd;

	// 勤務種別コード
	@Column(name = "BUSINESS_TYPE_CD")
	public String businessTypeCd;

	@Column(name = "JOB_ID")
	public String jobId;

	@Column(name = "WKP_ID")
	public String workplaceId;

	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "SID", referencedColumnName = "KSCDT_SCHE_BASIC.SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "KSCDT_SCHE_BASIC.YMD", insertable = false, updatable = false) })
	public KscdtBasicSchedule kscdtBasicSchedule;

	@Override
	protected Object getKey() {
		return kscdtScheMasterInfoPk;
	}

	public KscdtScheMasterInfo(KscdtScheMasterInfoPK kscdtScheMasterInfoPk, String employmentCd,
			String classificationCd, String businessTypeCd, String jobId, String workplaceId) {
		super();
		this.kscdtScheMasterInfoPk = kscdtScheMasterInfoPk;
		this.employmentCd = employmentCd;
		this.classificationCd = classificationCd;
		this.businessTypeCd = businessTypeCd;
		this.jobId = jobId;
		this.workplaceId = workplaceId;
	}

}
