package nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.service.reflectprocess;

import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.ReflectedStatesScheInfo;
import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.GoBackDirectlyReflectParam;

public interface GoBackDirectlyReflectSche {
	/**
	 * 直行直帰申請: 勤務予定への反映
	 * @param reflectPara
	 * @return
	 */
	public ReflectedStatesScheInfo goBackDirectlyReflectSch(GoBackDirectlyReflectParam reflectPara);
}
