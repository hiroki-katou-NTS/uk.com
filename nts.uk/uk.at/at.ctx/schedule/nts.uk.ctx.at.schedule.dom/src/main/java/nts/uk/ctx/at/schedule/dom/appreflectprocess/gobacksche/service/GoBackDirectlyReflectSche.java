package nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.service;

import nts.uk.ctx.at.schedule.dom.appreflectprocess.ReflectedStatesScheInfo;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.GoBackDirectlyReflectParam;

public interface GoBackDirectlyReflectSche {
	/**
	 * 直行直帰申請: 勤務予定への反映
	 * @param reflectPara
	 * @return
	 */
	public ReflectedStatesScheInfo goBackDirectlyReflectSch(GoBackDirectlyReflectParam reflectPara);
}
