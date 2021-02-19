package nts.uk.ctx.at.request.app.find.setting.company.displayname;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class HdAppDispNameFinder {
	@Inject
	private HolidayApplicationSettingRepository hdAppRep;
	/**
	 * get hd app disp name by company id
	 * @return
	 * @author yennth
	 */
	public List<HdAppDispNameDto> findByCid(){
		String companyId = AppContexts.user().companyId();
		Optional<HolidayApplicationSetting> hdAppSet = hdAppRep.findSettingByCompanyId(companyId);
		if (hdAppSet.isPresent()) {
			return hdAppSet.get().getHolidayApplicationTypeDisplayName()
					.stream()
					.map(c -> HdAppDispNameDto.convertToDto(companyId, c))
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	/**
	 * get hd app disp name by company id and hd app type
	 * @param hdType
	 * @return
	 * @author yennth
	 */
	public HdAppDispNameDto findItem(int hdType){
		String companyId = AppContexts.user().companyId();
		Optional<HolidayApplicationSetting> hdApp = hdAppRep.findSettingByCompanyId(companyId);
		if(hdApp.isPresent()){
			return hdApp.get().getHolidayApplicationTypeDisplayName()
					.stream()
					.filter(i -> i.getHolidayApplicationType().value == hdType)
					.findFirst().map(c -> HdAppDispNameDto.convertToDto(companyId, c)).orElse(null);
		}
		return null;
	}
}
