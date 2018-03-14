package nts.uk.ctx.at.schedule.pubimp.appreflectprocess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.ApplicationReflectParam;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.ApplyTimeAtr;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.appforleave.AppForLeaveScheInfor;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.appforleave.ForleaveReflectSche;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche.ApplicationGobackScheInfor;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche.ChangeAtrAppGoback;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche.GoBackDirectlyReflectSche;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.AppReflectProcessSchePub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ApplicationReflectParamScheDto;

@Stateless
public class AppReflectProcessSchePubImpl implements AppReflectProcessSchePub{
	@Inject
	private GoBackDirectlyReflectSche goBackReflect;
	@Inject
	private ForleaveReflectSche leaveReflect;

	@Override
	public boolean goBackDirectlyReflectSch(ApplicationReflectParamScheDto reflectPara) {
		ApplicationGobackScheInfor gobackInfo = new ApplicationGobackScheInfor(EnumAdaptor.valueOf(reflectPara.getGobackInfor().getChangeAtrAppGoback().value, ChangeAtrAppGoback.class),
				reflectPara.getGobackInfor().getWorkType(),
				reflectPara.getGobackInfor().getWorkTime(),
				reflectPara.getGobackInfor().getWorkTimeStart1(),
				reflectPara.getGobackInfor().getWorkTimeEnd1(),
				reflectPara.getGobackInfor().getWorkTimeStart2(),
				reflectPara.getGobackInfor().getWorkTimeEnd2()); 
		ApplicationReflectParam data = new ApplicationReflectParam(reflectPara.getEmployeeId(), 
				reflectPara.getDatePara(), 
				true,
				gobackInfo, 
				EnumAdaptor.valueOf(reflectPara.getApplyTimeAtr().value, ApplyTimeAtr.class),
				new AppForLeaveScheInfor(null)); 
		boolean reflectInfo = goBackReflect.goBackDirectlyReflectSch(data);	
		return reflectInfo;
	}

	@Override
	public void appForLeaveSche(ApplicationReflectParamScheDto reflectPara) {
		ApplicationReflectParam para = new ApplicationReflectParam(reflectPara.getEmployeeId(),
				reflectPara.getDatePara(),
				reflectPara.isOutsetBreakReflectAtr(),
				new ApplicationGobackScheInfor(null, null, null, null, null, null, null), 
				ApplyTimeAtr.START, 
				new AppForLeaveScheInfor(reflectPara.getLeaveInfo().getWorktypeCode()));
		leaveReflect.forlearveReflectSche(para);
	}

}
