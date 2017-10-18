package find.person.setting.init;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
/**
 * The class PerInfoInitValueSettingFinder
 * @author lanlt
 *
 */
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.bs.person.dom.person.setting.init.PerInfoInitValueSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerInfoInitValueSettingFinder {
	@Inject
	private PerInfoInitValueSettingRepository settingRepo;

	public List<PerInfoInitValueSettingDto> getAllInitValueSetting() {
		String companyId = AppContexts.user().companyId();
		return this.settingRepo.getAllInitValueSetting(companyId).stream()
				.map(c -> PerInfoInitValueSettingDto.fromDomain(c)).collect(Collectors.toList());
	}

	public List<PerInfoInitValueSettingDto> getAllInitValueSettingHasChild() {

		String companyId = AppContexts.user().companyId();

		List<PerInfoInitValueSettingDto> settingList = this.settingRepo.getAllInitValueSettingHasChild(companyId)
				.stream().map(c -> PerInfoInitValueSettingDto.fromDomain(c)).collect(Collectors.toList());

		if (settingList.isEmpty()) {

			throw new BusinessException(new RawErrorMessage("Msg_350"));

			// throw new BusinessException(new RawErrorMessage("Msg_351"));
		}

		return settingList;

	}

}
