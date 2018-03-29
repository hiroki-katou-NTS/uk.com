package nts.uk.ctx.at.schedule.pubimp.appreflectprocess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.ApplyTimeAtr;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.appforleave.AppForLeaveScheInfor;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.appforleave.ForleaveReflectParam;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.appforleave.ForleaveReflectSche;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche.ApplicationGobackScheInfor;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche.ChangeAtrAppGoback;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche.GoBackDirectlyReflectSche;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche.GobackReflectParam;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.AppForLeavePubDto;
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
		GobackReflectParam data = new GobackReflectParam(reflectPara.getEmployeeId(), 
				reflectPara.getDatePara(), 
				true,
				gobackInfo, 
				EnumAdaptor.valueOf(reflectPara.getApplyTimeAtr().value, ApplyTimeAtr.class)); 
			
		return  goBackReflect.goBackDirectlyReflectSch(data);
	}

	@Override
	public void appForLeaveSche(AppForLeavePubDto appForleaverPara) {
		ForleaveReflectParam leaverPara = new ForleaveReflectParam(appForleaverPara.getEmployeeId(), appForleaverPara.getDatePara(), appForleaverPara.getWorktypeCode());
		leaveReflect.forlearveReflectSche(leaverPara);
	}

}
