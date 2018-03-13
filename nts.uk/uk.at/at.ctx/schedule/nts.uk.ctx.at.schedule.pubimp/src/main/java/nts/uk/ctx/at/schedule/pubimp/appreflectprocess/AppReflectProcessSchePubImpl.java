package nts.uk.ctx.at.schedule.pubimp.appreflectprocess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.ApplyTimeAtr;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.ApplicationGobackScheInfor;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.ChangeAtrAppGoback;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.GoBackDirectlyReflectParam;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.service.GoBackDirectlyReflectSche;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.AppReflectProcessSchePub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.GoBackDirectlyReflectParamDto;

@Stateless
public class AppReflectProcessSchePubImpl implements AppReflectProcessSchePub{
	@Inject
	private GoBackDirectlyReflectSche goBackReflect;

	@Override
	public boolean goBackDirectlyReflectSch(GoBackDirectlyReflectParamDto reflectPara) {
		ApplicationGobackScheInfor gobackInfo = new ApplicationGobackScheInfor(EnumAdaptor.valueOf(reflectPara.getAppInfor().getChangeAtrAppGoback().value, ChangeAtrAppGoback.class),
				reflectPara.getAppInfor().getWorkType(),
				reflectPara.getAppInfor().getWorkTime(),
				reflectPara.getAppInfor().getWorkTimeStart1(),
				reflectPara.getAppInfor().getWorkTimeEnd1(),
				reflectPara.getAppInfor().getWorkTimeStart2(),
				reflectPara.getAppInfor().getWorkTimeEnd2()); 
		GoBackDirectlyReflectParam data = new GoBackDirectlyReflectParam(reflectPara.getEmployeeId(), 
				reflectPara.getDatePara(), 
				true,
				gobackInfo, 
				EnumAdaptor.valueOf(reflectPara.getApplyTimeAtr().value, ApplyTimeAtr.class)); 
		boolean reflectInfo = goBackReflect.goBackDirectlyReflectSch(data);	
		return reflectInfo;
	}

}
