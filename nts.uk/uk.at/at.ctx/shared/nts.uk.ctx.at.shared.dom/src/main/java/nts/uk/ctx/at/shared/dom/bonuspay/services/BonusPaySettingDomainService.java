package nts.uk.ctx.at.shared.dom.bonuspay.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.SpecBPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;

@Stateless
public class BonusPaySettingDomainService implements BonusPaySettingService {
	@Inject
	private BPTimesheetRepository bpTimesheetRepository;
	@Inject
	private SpecBPTimesheetRepository specBPTimesheetRepository;
	@Inject
	private BPSettingRepository bpSettingRepository;

	@Override
	public List<BonusPaySetting> getAllBonusPaySetting(String companyId) {

		List<BonusPaySetting> lstBonusPaySetting = bpSettingRepository.getAllBonusPaySetting(companyId);
		List<BonusPaySetting> lstBonusPaySettingRefer = new ArrayList<BonusPaySetting>();
		lstBonusPaySetting.forEach(a -> lstBonusPaySettingRefer.add(this.toBonusPaySettingRefer(a)));

		return lstBonusPaySettingRefer;
	}

	@Override
	public void addBonusPaySetting(BonusPaySetting domain) {
		bpTimesheetRepository.addListTimesheet(domain.getCompanyId().toString(), domain.getCode().toString(),
				domain.getLstBonusPayTimesheet());
		specBPTimesheetRepository.addListTimesheet(domain.getCompanyId().toString(), domain.getCode().toString(),
				domain.getLstSpecBonusPayTimesheet());
		bpSettingRepository.addBonusPaySetting(domain);
	}

	@Override
	public void updateBonusPaySetting(BonusPaySetting domain) {
		bpTimesheetRepository.updateListTimesheet(domain.getCompanyId().toString(), domain.getCode().toString(),
				domain.getLstBonusPayTimesheet());
		specBPTimesheetRepository.updateListTimesheet(domain.getCompanyId().toString(), domain.getCode().toString(),
				domain.getLstSpecBonusPayTimesheet());
		bpSettingRepository.updateBonusPaySetting(domain);
	}

	private BonusPaySetting toBonusPaySettingRefer(BonusPaySetting bonusPaySetting) {
		List<BonusPayTimesheet> lstBonusPayTimesheet = bpTimesheetRepository
				.getListTimesheet(bonusPaySetting.getCompanyId().toString(), bonusPaySetting.getCode().toString());
		List<SpecBonusPayTimesheet> lstSpecBonusPayTimesheet = specBPTimesheetRepository
				.getListTimesheet(bonusPaySetting.getCompanyId().toString(), bonusPaySetting.getCode().toString());
		BonusPaySetting bonusPaySettingRefer = BonusPaySetting.createFromJavaType(
				bonusPaySetting.getCompanyId().toString(), bonusPaySetting.getCode().toString(),
				bonusPaySetting.getName().toString());
		bonusPaySettingRefer.setListTimesheet(lstBonusPayTimesheet);
		bonusPaySettingRefer.setListSpecialTimesheet(lstSpecBonusPayTimesheet);
		return bonusPaySettingRefer;

	}

}
