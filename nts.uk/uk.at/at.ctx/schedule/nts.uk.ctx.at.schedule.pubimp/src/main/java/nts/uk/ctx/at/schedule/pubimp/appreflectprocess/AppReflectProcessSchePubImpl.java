package nts.uk.ctx.at.schedule.pubimp.appreflectprocess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EnumType;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.ApplyTimeAtr;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.ReflectedStateSche;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.ReflectedStatesScheInfo;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.ApplicationGobackScheInfor;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.ChangeAtrAppGoback;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.GoBackDirectlyReflectParam;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.OutsetBreakReflectScheAtr;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.service.GoBackDirectlyReflectSche;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.AppReflectProcessSchePub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.GoBackDirectlyReflectParamDto;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ReasonNotReflectSchePub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ReflectedStateSchePub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ReflectedStatesScheInfoDto;

@Stateless
public class AppReflectProcessSchePubImpl implements AppReflectProcessSchePub{
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
