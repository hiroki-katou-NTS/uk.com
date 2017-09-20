package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.adapter.WorkLocationAdapter;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.adapter.dto.WorkLocationImport;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GoBackDirectAppSetDefault implements GoBackDirectAppSetService {
	@Inject
	private GoBackDirectlyRepository goBackRepo;

	@Inject
	private ApplicationSettingRepository appSetRepo;

	@Inject
	private WorkLocationAdapter workLocationAdapter;

	@Inject
	private WorkTimeRepository workTimeRepo;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Override
	public GoBackDirectAppSet getGoBackDirectAppSet(String appID) {
		String companyID = AppContexts.user().companyId();
		GoBackDirectAppSet data = new GoBackDirectAppSet();
		GoBackDirectly goBackDirect = goBackRepo.findByApplicationID(companyID, appID).get();
		String workLocationName1 = workLocationAdapter.getByWorkLocationCD(companyID, goBackDirect.getWorkLocationCD1())
				.getWorkLocationName();
		String workLocationName2 = workLocationAdapter.getByWorkLocationCD(companyID, goBackDirect.getWorkLocationCD2())
				.getWorkLocationName();
		String cd = goBackDirect.getWorkTypeCD().v();

		String workTypeName = "type name is ERROR";
		// workTypeRepo.findByPK(companyID, cd).get().getName().v();
		String workTimeName = workTimeRepo.findByCode(companyID, goBackDirect.getSiftCd().v()).get()
				.getWorkTimeDisplayName().getWorkTimeName().v();
		// get List Work Location
		/**
		 * 
		 */
		data.goBackDirectly = goBackDirect;
		data.workLocationName1 = workLocationName1;
		data.workLocationName2 = workLocationName2;
		data.workTypeName = workTypeName;
		data.workTimeName = workTimeName;
		int prePostAtr = appSetRepo.getApplicationSettingByComID(companyID).get().getDisplayPrePostFlg().value;
		data.prePostAtr = prePostAtr;
		return data;
	}
}
