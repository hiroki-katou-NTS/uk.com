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
	public void addBonusPaySetting(BonusPaySetting domain, List<BonusPayTimesheet> listBonusPayTimeSheet, List<SpecBonusPayTimesheet> listSpecBonusPayTimeSheet) {
		String code = domain.getCode().v();
		if(code.length()==1){
			code = "00"+code;
		}else if(code.length()==2){
			code = "0"+code;
		}
		bpTimesheetRepository.addListTimesheet(domain.getCompanyId().toString(),
				new BonusPaySettingCode(code), listBonusPayTimeSheet);
		specBPTimesheetRepository.addListTimesheet(domain.getCompanyId().toString(),
				new BonusPaySettingCode(code), listSpecBonusPayTimeSheet);
		bpSettingRepository.addBonusPaySetting(domain);
	}

	@Override
	public void updateBonusPaySetting(BonusPaySetting domain, List<BonusPayTimesheet> listBonusPayTimeSheet, List<SpecBonusPayTimesheet> listSpecBonusPayTimeSheet) {
		bpTimesheetRepository.updateListTimesheet(domain.getCompanyId().toString(),
				new BonusPaySettingCode(domain.getCode().toString()), listBonusPayTimeSheet);
		specBPTimesheetRepository.updateListTimesheet(domain.getCompanyId().toString(),
				new BonusPaySettingCode(domain.getCode().toString()), listSpecBonusPayTimeSheet);
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
