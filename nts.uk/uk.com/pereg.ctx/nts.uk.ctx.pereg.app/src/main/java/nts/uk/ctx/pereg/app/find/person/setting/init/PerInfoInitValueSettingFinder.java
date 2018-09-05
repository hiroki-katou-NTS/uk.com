package nts.uk.ctx.pereg.app.find.person.setting.init;

import java.util.List;
import java.util.Optional;
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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.find.person.setting.init.category.PerInfoInitValueSettingCtgFinder;
import nts.uk.ctx.pereg.dom.person.setting.init.PerInfoInitValueSetting;
import nts.uk.ctx.pereg.dom.person.setting.init.PerInfoInitValueSettingRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.category.PerInfoInitValueSettingCtg;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerInfoInitValueSettingFinder {
	@Inject
	private PerInfoInitValueSettingRepository settingRepo;

	@Inject
	private PerInfoInitValueSettingCtgFinder cgtFinder;

	/**
	 * getAllInitValueSetting
	 * 
	 * @return List<PerInfoInitValueSettingDto>
	 */
	public List<PerInfoInitValueSettingDto> getAllInitValueSetting() {
		String companyId = AppContexts.user().companyId();
		return this.settingRepo.getAllInitValueSetting(companyId).stream()
				.map(c -> PerInfoInitValueSettingDto.fromDomain(c)).collect(Collectors.toList());
	}

	/**
	 * getAllInitValueSetting
	 * 
	 * @param settingId
	 * @return PerInitValueSettingDto
	 */
	public PerInitValueSettingDto getAllInitValueSetting(String settingId) {
		PerInfoInitValueSettingDto settingDto = this.getDetailInitValSetting(settingId);
		List<PerInfoInitValueSettingCtg> ctgLst = this.cgtFinder.getAllCategory(settingId);
		if (settingDto != null && !CollectionUtil.isEmpty(ctgLst)) {
			return PerInitValueSettingDto.fromDomain(settingDto, ctgLst);
		}
		return new PerInitValueSettingDto();
	}

	/**
	 * getDetailInitValSetting
	 * 
	 * @param settingId
	 * @return PerInfoInitValueSettingDto
	 */
	public PerInfoInitValueSettingDto getDetailInitValSetting(String settingId) {
		Optional<PerInfoInitValueSetting> setting = this.settingRepo.getDetailInitValSetting(settingId);
		if (setting.isPresent()) {
			return PerInfoInitValueSettingDto.fromDomain(setting.get());
		}

		return null;
	}

	/**
	 * getDetailInitValSetting
	 * 
	 * @param settingId
	 * @param settingCode
	 * @return PerInfoInitValueSettingDto
	 */
	public PerInfoInitValueSettingDto getDetailInitValSetting(String settingCode, String settingId) {
		String companyId = AppContexts.user().companyId();
		Optional<PerInfoInitValueSetting> setting = this.settingRepo.getDetailInitValSetting(companyId, settingCode, settingId);
		if (setting.isPresent()) {
			return PerInfoInitValueSettingDto.fromDomain(setting.get());
		}

		return null;
	}

	/**
	 * getAllInitValueSettingHasChild
	 * 
	 * @return List<PerInfoInitValueSettingDto>
	 */
	public List<PerInfoInitValueSettingDto> getAllInitValueSettingHasChild() {

		String companyId = AppContexts.user().companyId();

		List<PerInfoInitValueSettingDto> settingList = this.settingRepo.getAllInitValueSettingHasChild(companyId)
				.stream().map(c -> PerInfoInitValueSettingDto.fromDomain(c)).collect(Collectors.toList());
		if (settingList.isEmpty()) {
			String role = AppContexts.user().roles().forPersonalInfo();
			if (role == "" || role == null) {
				throw new BusinessException(new RawErrorMessage("Msg_351"));
			} else {
				throw new BusinessException(new RawErrorMessage("Msg_350"));
			}
		}

		return settingList;

	}

}
