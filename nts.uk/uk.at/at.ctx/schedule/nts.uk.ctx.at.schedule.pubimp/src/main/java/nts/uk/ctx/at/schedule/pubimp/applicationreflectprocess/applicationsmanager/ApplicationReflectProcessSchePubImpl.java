package nts.uk.ctx.at.schedule.pubimp.applicationreflectprocess.applicationsmanager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EnumType;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.ApplyTimeAtr;
import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.ReflectedStateSche;
import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.ReflectedStatesScheInfo;
import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.ApplicationGobackScheInfor;
import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.ChangeAtrAppGoback;
import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.GoBackDirectlyReflectParam;
import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.OutsetBreakReflectScheAtr;
import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.service.reflectprocess.GoBackDirectlyReflectSche;
import nts.uk.ctx.at.schedule.pub.applicationreflectprocess.applicationsmanager.ApplicationReflectProcessSchePub;
import nts.uk.ctx.at.schedule.pub.applicationreflectprocess.applicationsmanager.GoBackDirectlyReflectParamDto;
import nts.uk.ctx.at.schedule.pub.applicationreflectprocess.applicationsmanager.ReasonNotReflectSchePub;
import nts.uk.ctx.at.schedule.pub.applicationreflectprocess.applicationsmanager.ReflectedStateSchePub;
import nts.uk.ctx.at.schedule.pub.applicationreflectprocess.applicationsmanager.ReflectedStatesScheInfoDto;

@Stateless
public class ApplicationReflectProcessSchePubImpl implements ApplicationReflectProcessSchePub{
	@Inject
	private GoBackDirectlyReflectSche goBackReflect;

	@Override
	public ReflectedStatesScheInfoDto goBackDirectlyReflectSch(GoBackDirectlyReflectParamDto reflectPara) {
		ApplicationGobackScheInfor gobackInfo = new ApplicationGobackScheInfor(EnumAdaptor.valueOf(reflectPara.getAppInfor().getChangeAtrAppGoback().value, ChangeAtrAppGoback.class),
				reflectPara.getAppInfor().getWorkType(),
				reflectPara.getAppInfor().getWorkTime(),
				reflectPara.getAppInfor().getWorkTimeStart1(),
				reflectPara.getAppInfor().getWorkTimeEnd1(),
				reflectPara.getAppInfor().getWorkTimeStart2(),
				reflectPara.getAppInfor().getWorkTimeEnd2()); 
		GoBackDirectlyReflectParam data = new GoBackDirectlyReflectParam(reflectPara.getEmployeeId(), 
				reflectPara.getDatePara(), 
				EnumAdaptor.valueOf(reflectPara.getOutsetBreakReflectAtr().value, OutsetBreakReflectScheAtr.class),
				gobackInfo, 
				EnumAdaptor.valueOf(reflectPara.getApplyTimeAtr().value, ApplyTimeAtr.class)); 
		ReflectedStatesScheInfo reflectInfo = goBackReflect.goBackDirectlyReflectSch(data);
		ReflectedStatesScheInfoDto dataReturn = new ReflectedStatesScheInfoDto(EnumAdaptor.valueOf(reflectInfo.getReflectedSate().value, ReflectedStateSchePub.class), 
				EnumAdaptor.valueOf(reflectInfo.getNotReflectReson().value, ReasonNotReflectSchePub.class));		
		return dataReturn;
	}

}
