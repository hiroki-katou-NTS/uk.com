package nts.uk.ctx.at.request.app.find.setting.request.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.applist.service.AppListInitialRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApproverRegisterSet;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbycompany.RequestByCompanyRepository;
import nts.uk.shr.com.context.AppContexts;
//HOATT - CMM018_2
@Stateless
public class ApplicationUseAtrFinder {

	@Inject
	private RequestByCompanyRepository requestByCompanyRepository;
	@Inject
	private ApplicationSettingRepository repoAppSet;
	@Inject
	private WorkplaceAdapter wkpAdapter;
	@Inject
	private AppListInitialRepository repoAppLst;
	
	/**
	 * 利用している申請種類を取得
	 * @param tab(0: company, 1: work place, 2: person)
	 * @param workplaceID
	 * @param sId
	 * @return
	 */
	public List<AppUseAtrDto> getAppUseAtr(int tab, String workplaceID, String sId){
		
		List<AppUseAtrDto> lstResult = new ArrayList<>();
		//申請利用設定
		List<ApplicationUseSetting> lstAppUseSet = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		//会社別申請承認設定の取得
		if(tab == 0) {//lay setting theo company
			Optional<ApprovalFunctionSet> comS = requestByCompanyRepository.findByCompanyID(companyId);
			if(comS.isPresent()) {
				lstAppUseSet = comS.get().getAppUseSetLst();
			}
			lstResult = lstAppUseSet.stream().map(c -> new AppUseAtrDto(c.getAppType().value, c.getUseDivision().value)).collect(Collectors.toList());
			return lstResult;
		}
		//lay setting theo work place || person
		if(tab == 2 || (tab == 1 && Strings.isBlank(workplaceID))) {
			if(Strings.isBlank(sId)) {
				sId = AppContexts.user().employeeId();
			}
			//lấy workplace login
			WkpHistImport wkp = wkpAdapter.findWkpBySid(sId, GeneralDate.today());
			if(wkp != null) workplaceID = wkp.getWorkplaceId();
		}
		//職場IDから申請承認設定情報取得
		List<ApplicationUseSetting> lstSet = repoAppLst.detailSetKAF022(companyId, workplaceID, GeneralDate.today());
		
		lstResult = lstSet.stream().map(c -> new AppUseAtrDto(c.getAppType().value, c.getUseDivision().value))
				.collect(Collectors.toList());
		Collections.sort(lstResult, Comparator.comparing(AppUseAtrDto::getAppType));
		return lstResult;
	}
	
	public ApproverRegisterSetDto getAppSet(String companyId){
		Optional<ApplicationSetting> appSet = repoAppSet.getApplicationSettingByComID(companyId);
		if(!appSet.isPresent()) return new ApproverRegisterSetDto(1, 1, 1);
		ApproverRegisterSet c =  appSet.get().getApproverResSet();
		return new ApproverRegisterSetDto(c.getCompanyUnit().value, 
				c.getWorkplaceUnit().value, c.getEmployeeUnit().value);
	}
}
