package nts.uk.ctx.at.record.dom.standardtime.repository;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.standardtime.AgreementUnitSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.UseClassificationAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * ドメインサービス実装：36協定
 * @author shuichi_ishida
 */
@Stateless
public class AgreementDomainServiceImpl implements AgreementDomainService {

	/** 36協定単位設定 */
	@Inject
	private AgreementUnitSettingRepository agreementUnitSetRepository;
	/** 分類36協定設定 */
	@Inject
	private AgreementTimeOfClassificationRepository agreementTimeClassRepository;
	/** 職場36協定設定 */
	@Inject
	private AgreementTimeOfWorkPlaceRepository agreementTimeWorkPlaceRepository;
	/** 雇用36協定設定 */
	@Inject
	private AgreementTimeOfEmploymentRepostitory agreementTimeEmploymentRepository;
	/** 会社36協定設定 */
	@Inject
	private AgreementTimeCompanyRepository agreementTimeCompanyRepository;
	/** 36協定基本設定 */
	@Inject
	private BasicAgreementSettingRepository basicAgreementSetRepository;
	
	/** 所属分類履歴 */
	@Inject
	private AffClassificationAdapter affClassficationAdapter;
	/** 所属職場履歴 */
	@Inject
	private AffWorkplaceAdapter affWorkplaceAdapter;
	/** 所属雇用履歴 */
	@Inject
	private SyEmploymentAdapter syEmploymentAdapter;
	
	/** 36協定基本設定を取得する */
	@Override
	public BasicAgreementSettings getBasicSet(String companyId, String employeeId, GeneralDate criteriaDate,
			WorkingSystem workingSystem) {
		
		// 「36協定単位設定」を取得する
		AgreementUnitSetting agreementUnitSet = new AgreementUnitSetting(companyId,
				UseClassificationAtr.NOT_USE, UseClassificationAtr.NOT_USE, UseClassificationAtr.NOT_USE);
		val agreementUnitSetOpt = this.agreementUnitSetRepository.find(companyId);
		if (agreementUnitSetOpt.isPresent()) agreementUnitSet = agreementUnitSetOpt.get();

		// 36協定労働制を確認する
		LaborSystemtAtr laborSystemAtr = LaborSystemtAtr.GENERAL_LABOR_SYSTEM;
		if (workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			laborSystemAtr = LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM;
		}
		
		if (agreementUnitSet.getClassificationUseAtr() == UseClassificationAtr.USE){
			
			// 分類36協定時間を取得する
			val affClassficationOpt = this.affClassficationAdapter.findByEmployeeId(companyId, employeeId, criteriaDate);
			if (affClassficationOpt.isPresent()){
				val classCd = affClassficationOpt.get().getClassificationCode();
				val agreementTimeOfCls = this.agreementTimeClassRepository.find(companyId, laborSystemAtr, classCd);
				if (agreementTimeOfCls.isPresent()){
					val basicAgreementSetOpt = this.basicAgreementSetRepository.find(
							agreementTimeOfCls.get().getBasicSettingId());
					if (basicAgreementSetOpt.isPresent()) {
						return BasicAgreementSettings.of(
								basicAgreementSetOpt.get(), agreementTimeOfCls.get().getUpperAgreementSetting());
					}
				}
			}
		}
		if (agreementUnitSet.getWorkPlaceUseAtr() == UseClassificationAtr.USE){
			
			// 職場36協定時間を取得する
			val workplaceIds = this.affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRoot(
					companyId, employeeId, criteriaDate);
			for (String workplaceId : workplaceIds){
				val agreementTimeOfWkp = this.agreementTimeWorkPlaceRepository.findAgreementTimeOfWorkPlace(
						workplaceId, laborSystemAtr);
				if (agreementTimeOfWkp.isPresent()){
					val basicAgreementSetOpt = this.basicAgreementSetRepository.find(
							agreementTimeOfWkp.get().getBasicSettingId());
					if (basicAgreementSetOpt.isPresent()) {
						return BasicAgreementSettings.of(
								basicAgreementSetOpt.get(), agreementTimeOfWkp.get().getUpperAgreementSetting());
					}
				}
			}
		}
		if (agreementUnitSet.getEmploymentUseAtr() == UseClassificationAtr.USE){
			
			// 雇用36協定時間を取得する
			val syEmploymentOpt = this.syEmploymentAdapter.findByEmployeeId(companyId, employeeId, criteriaDate);
			if (syEmploymentOpt.isPresent()){
				val employmentCd = syEmploymentOpt.get().getEmploymentCode();
				val agreementTimeOfEmp = this.agreementTimeEmploymentRepository.find(
						companyId, employmentCd, laborSystemAtr);
				if (agreementTimeOfEmp.isPresent()){
					val basicAgreementSetOpt = this.basicAgreementSetRepository.find(
							agreementTimeOfEmp.get().getBasicSettingId());
					if (basicAgreementSetOpt.isPresent()) {
						return BasicAgreementSettings.of(
								basicAgreementSetOpt.get(), agreementTimeOfEmp.get().getUpperAgreementSetting());
					}
				}
			}
		}
		
		// 会社36協定時間を取得する
		val agreementTimeOfCmpOpt = this.agreementTimeCompanyRepository.find(companyId, laborSystemAtr);
		if (agreementTimeOfCmpOpt.isPresent()){
			val basicAgreementSetOpt = this.basicAgreementSetRepository.find(
					agreementTimeOfCmpOpt.get().getBasicSettingId());
			if (basicAgreementSetOpt.isPresent()) {
				return BasicAgreementSettings.of(
						basicAgreementSetOpt.get(), agreementTimeOfCmpOpt.get().getUpperAgreementSetting());
			}
		}
		
		// 全ての値を0で返す
		return new BasicAgreementSettings();	
	}
}
