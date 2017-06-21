/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.acquisitionrule.find;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class VacationAcquisitionRuleFinder.
 */
@Stateless
public class AcquisitionRuleFinder {

	/** The repo. */
	@Inject
	private AcquisitionRuleRepository repo;

	/** The paid leave repo. */
	@Inject
	private AnnualPaidLeaveSettingRepository paidLeaveRepo;

	/** The compens leave com set repository. */
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
	
	/** The nursing repo. */
	@Inject
	private NursingLeaveSettingRepository nursingRepo;

	/** The com 60 H repo. */
	@Inject
	private Com60HourVacationRepository com60HRepo;

	/** The com subt repo. */
	@Inject
	private ComSubstVacationRepository comSubtRepo;

	/**
	 * Find.
	 *
	 * @param companyId
	 *            the company id
	 * @return the optional
	 */
	public AcquisitionRuleDto find() {
		String companyId = AppContexts.user().companyId();
		Optional<AcquisitionRule> vaAcRule = repo.findById(companyId);
		if (vaAcRule.isPresent()) {
			AcquisitionRuleDto dto = AcquisitionRuleDto.builder().build();
			vaAcRule.get().saveToMemento(dto);
			return dto;
		} else {
			return null;
		}

	}
	
	/**
	 * Find by setting.
	 *
	 * @return the apply setting dto
	 */
	public ApplySettingDto findBySetting(){
		String companyId = AppContexts.user().companyId();
		ApplySettingDto dto = new ApplySettingDto();
		
		//check setting annual paid leave
		AnnualPaidLeaveSetting paidLeave = paidLeaveRepo.findByCompanyId(companyId);
		if (paidLeave != null) {
			dto.setPaidLeaveSetting(paidLeave.getYearManageType() == ManageDistinct.YES);
		}
		
		//check setting compensatory leave
		CompensatoryLeaveComSetting compensLeave = compensLeaveComSetRepository.find(companyId);
		if(compensLeave != null){
			dto.setCompensLeaveComSetSetting(compensLeave.getIsManaged() == ManageDistinct.YES);
		}
		
		//check setting comSubt
		Optional<ComSubstVacation> optComSubVacation = comSubtRepo.findById(companyId);
		if(optComSubVacation.isPresent()){
			dto.setComSubtSetting(optComSubVacation.get().getSetting().getIsManage() == ManageDistinct.YES);
		}
				
		//check setting com60H
		Optional<Com60HourVacation> optCom60HVacation = com60HRepo.findById(companyId);
		if (optCom60HVacation.isPresent()){
			dto.setCom60HSetting(optCom60HVacation.get().getSetting().getIsManage() == ManageDistinct.YES);
		}
		
		//check setting nursing leave
		List<NursingLeaveSetting> lstNursingLeave = nursingRepo.findByCompanyId(companyId);
		if (!lstNursingLeave.isEmpty()) {
			int indexNursing = 0;
			int indexChildNursing = 1;
			dto.setNursingSetting(lstNursingLeave.get(indexNursing).getManageType() == ManageDistinct.YES
					|| lstNursingLeave.get(indexChildNursing).getManageType() == ManageDistinct.YES);
		}
		return dto;
		
	}

}
