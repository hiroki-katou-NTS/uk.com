package nts.uk.ctx.at.shared.dom.bonuspay.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
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
	public void addBonusPaySetting(BonusPaySetting domain) {
		bpTimesheetRepository.addListTimesheet(domain.getCompanyId().toString(),
				new BonusPaySettingCode(domain.getCode().toString()), domain.getLstBonusPayTimesheet());
		specBPTimesheetRepository.addListTimesheet(domain.getCompanyId().toString(),
				new BonusPaySettingCode(domain.getCode().toString()), domain.getLstSpecBonusPayTimesheet());
		bpSettingRepository.addBonusPaySetting(domain);
	}

	@Override
	public void updateBonusPaySetting(BonusPaySetting domain) {
		bpTimesheetRepository.updateListTimesheet(domain.getCompanyId().toString(),
				new BonusPaySettingCode(domain.getCode().toString()), domain.getLstBonusPayTimesheet());
		specBPTimesheetRepository.updateListTimesheet(domain.getCompanyId().toString(),
				new BonusPaySettingCode(domain.getCode().toString()), domain.getLstSpecBonusPayTimesheet());
		bpSettingRepository.updateBonusPaySetting(domain);
	}

	public void deleteBonusPaySetting(String companyId, String bonusPaySettingCode) {
		List<BonusPayTimesheet> lstBonusPayTimesheet = bpTimesheetRepository.getListTimesheet(companyId,
				new BonusPaySettingCode(bonusPaySettingCode));
		List<SpecBonusPayTimesheet> lstSpecBonusPayTimesheet = specBPTimesheetRepository.getListTimesheet(companyId,
				new BonusPaySettingCode(bonusPaySettingCode));
		bpTimesheetRepository.removeListTimesheet(companyId, new BonusPaySettingCode(bonusPaySettingCode),
				lstBonusPayTimesheet);
		specBPTimesheetRepository.removeListTimesheet(companyId, new BonusPaySettingCode(bonusPaySettingCode),
				lstSpecBonusPayTimesheet);
		bpSettingRepository.removeBonusPaySetting(companyId, new BonusPaySettingCode(bonusPaySettingCode));
	}

	@Override
	public boolean isExisted(String companyId, BonusPaySettingCode code) {
		return bpSettingRepository.isExisted(companyId, code);
	}
}
