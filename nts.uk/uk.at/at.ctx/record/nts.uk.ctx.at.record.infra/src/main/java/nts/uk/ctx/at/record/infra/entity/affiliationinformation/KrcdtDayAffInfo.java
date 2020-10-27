package nts.uk.ctx.at.record.infra.entity.affiliationinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt
 * 日別実績の所属情報
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_AFF_INFO")
public class KrcdtDayAffInfo extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDayAffInfoPK krcdtDayAffInfoPK;

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
	
	@Column(name = "WORK_TYPE_CODE")
	public String businessTypeCode;

	@Override
	protected Object getKey() {
		return this.krcdtDayAffInfoPK;
	}

	public AffiliationInforOfDailyPerfor toDomain(){
		BusinessTypeCode businessTypeCode = this.businessTypeCode == null ? null : new BusinessTypeCode(this.businessTypeCode);
		AffiliationInforOfDailyPerfor domain = new AffiliationInforOfDailyPerfor(
				new EmploymentCode(this.employmentCode),
				this.krcdtDayAffInfoPK.employeeId,
				this.jobtitleID,
				this.workplaceID,
				this.krcdtDayAffInfoPK.ymd,
				new ClassificationCode(this.classificationCode),
				this.bonusPayCode == null ? null : new BonusPaySettingCode(this.bonusPayCode),
				businessTypeCode);
		return domain;
	}
	
	public static KrcdtDayAffInfo toEntity(AffiliationInforOfDailyPerfor affiliationInforOfDailyPerfor){
		return new KrcdtDayAffInfo(
				new KrcdtDayAffInfoPK(affiliationInforOfDailyPerfor.getEmployeeId(), affiliationInforOfDailyPerfor.getYmd()),
				affiliationInforOfDailyPerfor.getAffiliationInfor().getEmploymentCode().v(),
				affiliationInforOfDailyPerfor.getAffiliationInfor().getJobTitleID(),
				affiliationInforOfDailyPerfor.getAffiliationInfor().getClsCode().v(),
				affiliationInforOfDailyPerfor.getAffiliationInfor().getWplID(),
				affiliationInforOfDailyPerfor.getAffiliationInfor().getBonusPaySettingCode() == null 
					? null : affiliationInforOfDailyPerfor.getAffiliationInfor().getBonusPaySettingCode().v(),
				affiliationInforOfDailyPerfor.getAffiliationInfor().getBusinessTypeCode().isPresent()
					? affiliationInforOfDailyPerfor.getAffiliationInfor().getBusinessTypeCode().get().v():null		
				);
	}
}
