package nts.uk.ctx.at.record.infra.entity.affiliationinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;

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
	
	@Column(name = "WORK_TYPE_CODE")
	public String businessTypeCode;
	
	// redmine 119637
	// 職場グループID
	@Column(name = "WKP_GROUP_ID")
	public String workplaceGroupId;
	// 看護免許区分
	@Column(name = "NURSE_LICENSE_ATR")
	public Integer nursingLicenseClass;
	// 看護管理者か
	@Column(name = "IS_NURSE_ADMINISTRATOR")
	public Boolean nursingManager;

	@Override
	protected Object getKey() {
		return this.krcdtDaiAffiliationInfPK;
	}

	public AffiliationInforOfDailyPerfor toDomain(){
		BusinessTypeCode businessTypeCode = this.businessTypeCode == null ? null : new BusinessTypeCode(this.businessTypeCode);
		AffiliationInforOfDailyPerfor domain = new AffiliationInforOfDailyPerfor(
				new EmploymentCode(this.employmentCode),
				this.krcdtDaiAffiliationInfPK.employeeId,
				this.jobtitleID,
				this.workplaceID,
				this.krcdtDaiAffiliationInfPK.ymd,
				new ClassificationCode(this.classificationCode),
				this.bonusPayCode == null ? null : new BonusPaySettingCode(this.bonusPayCode),
				businessTypeCode,
				workplaceGroupId,
				nursingLicenseClass == null ? null : EnumAdaptor.valueOf(nursingLicenseClass, LicenseClassification.class),
				nursingManager		
				);
		return domain;
	}
	
	public static KrcdtDayAffInfo toEntity(AffiliationInforOfDailyPerfor domain){
		AffiliationInforOfDailyAttd  affInfor = domain.getAffiliationInfor();
		return new KrcdtDayAffInfo(
				new KrcdtDaiAffiliationInfPK(domain.getEmployeeId(), domain.getYmd()),
				affInfor.getEmploymentCode().v(),
				affInfor.getJobTitleID(),
				affInfor.getClsCode().v(),
				affInfor.getWplID(),
				affInfor.getBonusPaySettingCode().isPresent() ? affInfor.getBonusPaySettingCode().get().v() : null,
				affInfor.getBusinessTypeCode().isPresent()? affInfor.getBusinessTypeCode().get().v():null,
				affInfor.getWorkplaceGroupId().isPresent()? affInfor.getWorkplaceGroupId().get() : null,
				affInfor.getNursingLicenseClass().isPresent()? affInfor.getNursingLicenseClass().get().value : null,
				affInfor.getIsNursingManager().isPresent() ? affInfor.getIsNursingManager().get() : null
				);
	}
}
