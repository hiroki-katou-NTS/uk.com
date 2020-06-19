package nts.uk.ctx.at.record.infra.entity.affiliationinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
import nts.uk.ctx.at.shared.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.affiliationinformation.primitivevalue.ClassificationCode;
=======
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
>>>>>>> 6a951869f1a133c8a2a28d251e823dcd67e7a548
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 * 日別実績の所属情報
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAI_AFFILIATION_INF")
public class KrcdtDaiAffiliationInf extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDaiAffiliationInfPK krcdtDaiAffiliationInfPK;

	@Column(name = "EMP_CODE")
	public String employmentCode;
	
	@Column(name = "JOB_ID")
	public String jobtitleID;
	
	@Column(name = "CLS_CODE")
	public String classificationCode;
	
	@Column(name = "WKP_ID")
	public String workplaceID;
	
	@Column(name = "BONUS_PAY_CODE")
	public String bonusPayCode;

	@Override
	protected Object getKey() {
		return this.krcdtDaiAffiliationInfPK;
	}

	public AffiliationInforOfDailyPerfor toDomain(){
		AffiliationInforOfDailyPerfor domain = new AffiliationInforOfDailyPerfor(
				new EmploymentCode(this.employmentCode),
				this.krcdtDaiAffiliationInfPK.employeeId,
				this.jobtitleID,
				this.workplaceID,
				this.krcdtDaiAffiliationInfPK.ymd,
				new ClassificationCode(this.classificationCode),
				this.bonusPayCode == null ? null : new BonusPaySettingCode(this.bonusPayCode));
		return domain;
	}
	
	public static KrcdtDaiAffiliationInf toEntity(AffiliationInforOfDailyPerfor affiliationInforOfDailyPerfor){
		return new KrcdtDaiAffiliationInf(
				new KrcdtDaiAffiliationInfPK(affiliationInforOfDailyPerfor.getEmployeeId(), affiliationInforOfDailyPerfor.getYmd()),
				affiliationInforOfDailyPerfor.getEmploymentCode().v(),
				affiliationInforOfDailyPerfor.getJobTitleID(),
				affiliationInforOfDailyPerfor.getClsCode().v(),
				affiliationInforOfDailyPerfor.getWplID(),
				affiliationInforOfDailyPerfor.getBonusPaySettingCode() == null 
					? null : affiliationInforOfDailyPerfor.getBonusPaySettingCode().v());
	}
}
