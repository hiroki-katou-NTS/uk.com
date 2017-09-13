package nts.uk.ctx.at.request.app.find.application.requestofearch;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchWorkplaceRepository;

@Stateless
public class GetDataAppCfDetailFinder {
	@Inject
	private RequestOfEarchCompanyRepository detailCompanyRepo;
	
	@Inject
	private RequestOfEarchWorkplaceRepository detailWorkplaceRepo;
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepo;
	
	 public OutputMessageDeadline getDataConfigDetail(String companyID,String workplaceID,int appType){
		String message = "";
		String deadline = "";
		GeneralDate date = GeneralDate.today();
		
		 Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = appTypeDiscreteSettingRepo
				 .getAppTypeDiscreteSettingByAppType(companyID, appType);
		//事後申請の受付は7月27日分まで。
		if(appTypeDiscreteSetting.get().getRetrictPostAllowFutureFlg().value  == 1 && 
		   appTypeDiscreteSetting.get().getRetrictPreUseFlg().value == 1) {
			deadline = "事後申請の受付は"+date+"分まで";
		}
		if(workplaceID.isEmpty()) {
			//this is company
			Optional<AppConfigDetailDto> appConfigDetailCom =  detailCompanyRepo.getRequestDetail(companyID, appType)
					.map(c -> AppConfigDetailDto.fromDomain(c));
			if (appConfigDetailCom.isPresent()) { //ton tai
				if(!appConfigDetailCom.get().getMemo().isEmpty()) {
					message = appConfigDetailCom.get().getMemo();
				}
			}
			
		}else {
			//this is company workplace
			Optional<AppConfigDetailDto> appConfigDetailWP = detailWorkplaceRepo.getRequestDetail(companyID, workplaceID, appType)
			.map(c -> AppConfigDetailDto.fromDomain(c));
			if (appConfigDetailWP.isPresent()) { //ton tai
				if(!appConfigDetailWP.get().getMemo().isEmpty()) {
					message = appConfigDetailWP.get().getMemo();
				}
				
			}
		}
		
		 return new OutputMessageDeadline(message,deadline);
	}
}
