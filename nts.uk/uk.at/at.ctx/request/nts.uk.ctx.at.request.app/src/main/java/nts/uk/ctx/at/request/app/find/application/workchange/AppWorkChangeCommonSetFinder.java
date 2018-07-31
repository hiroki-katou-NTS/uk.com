package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.service.IWorkChangeCommonService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppWorkChangeCommonSetFinder {
	@Inject
	private IWorkChangeCommonService workChangeCommonService;

	public AppWorkChangeCommonSetDto getWorkChangeCommonSetting(List<String> sIds, String paramDate) {
		String sId = CollectionUtil.isEmpty(sIds) ? AppContexts.user().employeeId() : sIds.get(0);
		String companyId = AppContexts.user().companyId();
		GeneralDate appDate = GeneralDate.fromString(paramDate, "yyyy/MM/dd");
		return AppWorkChangeCommonSetDto.fromDomain(workChangeCommonService.getSettingData(companyId, sId,sIds, appDate));
	}
}
