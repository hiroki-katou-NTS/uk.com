package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimeItemSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BPTimeItemSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class BPTimeItemSettingFinder {
	@Inject
	private BPTimeItemSettingRepository bpTimeItemSettingRepository;

	public List<BPTimeItemSettingDto> getListSetting() {
		String companyId = AppContexts.user().companyId();
		List<BPTimeItemSetting> lstBPTimeItemSetting = bpTimeItemSettingRepository.getListSetting(companyId);
		return lstBPTimeItemSetting.stream().map(c -> toBPTimeItemSettingDto(c)).collect(Collectors.toList());
	}

	public List<BPTimeItemSettingDto> getListSpecialSetting() {
		String companyId = AppContexts.user().companyId();
		List<BPTimeItemSetting> lstBPTimeItemSetting = bpTimeItemSettingRepository.getListSpecialSetting(companyId);
		return lstBPTimeItemSetting.stream().map(c -> toBPTimeItemSettingDto(c)).collect(Collectors.toList());

	}

	private BPTimeItemSettingDto toBPTimeItemSettingDto(BPTimeItemSetting bpTimeItemSetting) {
		return new BPTimeItemSettingDto(bpTimeItemSetting.getCompanyId().toString(),
				bpTimeItemSetting.getTimeItemNo(),
				bpTimeItemSetting.getHolidayCalSettingAtr().value,
				bpTimeItemSetting.getOvertimeCalSettingAtr().value,
				bpTimeItemSetting.getWorktimeCalSettingAtr().value,
				bpTimeItemSetting.getTimeItemTypeAtr().value);
	}

}
