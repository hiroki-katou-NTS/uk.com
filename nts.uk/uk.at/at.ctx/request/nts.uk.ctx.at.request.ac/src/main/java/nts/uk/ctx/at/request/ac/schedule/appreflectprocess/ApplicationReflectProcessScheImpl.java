package nts.uk.ctx.at.request.ac.schedule.appreflectprocess;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ApplicationGobackScheInforDto;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule.ApplicationReflectProcessSche;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule.ReflectScheDto;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.CommonReflectSchePubParam;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.AppReflectProcessSchePub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ApplyTimeAtrPub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ChangeAtrAppGobackPub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ApplicationReflectParamScheDto;

@Stateless
public class ApplicationReflectProcessScheImpl implements ApplicationReflectProcessSche{
	@Inject
	private AppReflectProcessSchePub appReflectSchePub;

	@Override
	public boolean goBackDirectlyReflect(ReflectScheDto reflectSche) {
		ApplicationGobackScheInforDto appInfo = new ApplicationGobackScheInforDto(
				reflectSche.getGoBackDirectly().getWorkChangeAtr().isPresent() ? EnumAdaptor.valueOf(reflectSche.getGoBackDirectly().getWorkChangeAtr().get().value, ChangeAtrAppGobackPub.class) : null,
				reflectSche.getGoBackDirectly().getWorkTypeCD().isPresent() ? reflectSche.getGoBackDirectly().getWorkTypeCD().get().v() : null,
				reflectSche.getGoBackDirectly().getSiftCD().isPresent() ? reflectSche.getGoBackDirectly().getSiftCD().get().v() : null,
				reflectSche.getGoBackDirectly().getWorkTimeStart1().isPresent() ? reflectSche.getGoBackDirectly().getWorkTimeStart1().get().v() : null,
				reflectSche.getGoBackDirectly().getWorkTimeEnd1().isPresent() ? reflectSche.getGoBackDirectly().getWorkTimeEnd1().get().v() : null,
				reflectSche.getGoBackDirectly().getWorkTimeStart2().isPresent() ? reflectSche.getGoBackDirectly().getWorkTimeStart2().get().v() : null,
				reflectSche.getGoBackDirectly().getWorkTimeEnd2().isPresent() ? reflectSche.getGoBackDirectly().getWorkTimeEnd2().get().v() : null);
		ApplicationReflectParamScheDto dto = new ApplicationReflectParamScheDto(reflectSche.getEmployeeId(), 
				reflectSche.getDatePara(),
				true,
				appInfo, 
				EnumAdaptor.valueOf(reflectSche.getTimeAtr().value, ApplyTimeAtrPub.class)); 
		return appReflectSchePub.goBackDirectlyReflectSch(dto);
	}

	@Override
	public boolean forleaveReflect(ReflectScheDto reflectSche) {
		CommonReflectSchePubParam leavePra = new CommonReflectSchePubParam(reflectSche.getEmployeeId(),
				reflectSche.getDatePara(),
				reflectSche.getForLeave().getWorkTypeCode().v(), //勤務種類=INPUT．勤務種類コード chi update workType
				null,
				null,
				null);
		return appReflectSchePub.appForLeaveSche(leavePra);
	}

	@Override
	public boolean workChangeReflect(ReflectScheDto reflectSche) {
		CommonReflectSchePubParam workChangePara = new CommonReflectSchePubParam(reflectSche.getEmployeeId(), 
				reflectSche.getDatePara(), 
				reflectSche.getWorkChange().getWorkTypeCd(), 
				reflectSche.getWorkChange().getWorkTimeCd(),
				reflectSche.getAppInfor().getStartDate() == null ? null : reflectSche.getAppInfor().getStartDate().get(),
				reflectSche.getAppInfor().getEndDate() == null ? null : reflectSche.getAppInfor().getEndDate().get());
		
		return appReflectSchePub.appWorkChangeReflect(workChangePara);
	}
	
	

}
