package nts.uk.ctx.at.request.ac.schedule.appreflectprocess;

import javax.ejb.Stateless;
import javax.persistence.EnumType;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.applicationreflect.workschedule.ReflectScheDto;
import nts.uk.ctx.at.request.dom.applicationreflect.workschedule.ReflectedStatesInfo;
import nts.uk.ctx.at.request.dom.applicationreflect.workschedule.service.ApplicationReflectProcessSche;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ApplicationGobackScheInforDto;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.AppReflectProcessSchePub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ApplyTimeAtrPub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ChangeAtrAppGobackPub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.GoBackDirectlyReflectParamDto;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.OutsetBreakReflectScheAtrPub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ReflectedStatesScheInfoDto;

@Stateless
public class ApplicationReflectProcessScheImpl implements ApplicationReflectProcessSche{
	private AppReflectProcessSchePub appReflectSchePub;

	@Override
	public ReflectedStatesInfo goBackDirectlyReflect(ReflectScheDto reflectSche) {
		ApplicationGobackScheInforDto appInfo = new ApplicationGobackScheInforDto(EnumAdaptor.valueOf(reflectSche.getGoBackDirectly().getWorkChangeAtr().value, ChangeAtrAppGobackPub.class),
				reflectSche.getGoBackDirectly().getWorkTypeCD().v(),
				reflectSche.getGoBackDirectly().getSiftCD().v(),
				reflectSche.getGoBackDirectly().getWorkTimeStart1().v(),
				reflectSche.getGoBackDirectly().getWorkTimeEnd2().v(),
				reflectSche.getGoBackDirectly().getWorkTimeStart2().v(),
				reflectSche.getGoBackDirectly().getWorkTimeEnd2().v());
		GoBackDirectlyReflectParamDto dto = new GoBackDirectlyReflectParamDto(reflectSche.getEmployeeId(), 
				reflectSche.getDatePara(),
				EnumAdaptor.valueOf(reflectSche.getReflectAtr().value, OutsetBreakReflectScheAtrPub.class),
				appInfo, 
				EnumAdaptor.valueOf(reflectSche.getTimeAtr().value, ApplyTimeAtrPub.class)); 
		ReflectedStatesScheInfoDto data = appReflectSchePub.goBackDirectlyReflectSch(dto);
		ReflectedStatesInfo dataReturn = new ReflectedStatesInfo(EnumAdaptor.valueOf(data.getReflectedSate().value, ReflectedState_New.class),
				EnumAdaptor.valueOf(data.getNotReflectReson().value, ReasonNotReflect_New.class));
		return dataReturn;
	}

}
