package nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.service.reflectprocess;

import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.ReflectedStatesScheInfo;
import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.GoBackDirectlyReflectParam;
import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.TimeOfDayReflectFindDto;
/**
 * 時刻の反映
 * @author dudt
 *
 */
public interface TimeOfDayReflectGoBackSche {
	/**
	 * 時刻の反映
	 * @param reflectPara
	 * @return
	 */
	public ReflectedStatesScheInfo stampReflectGobackSche(GoBackDirectlyReflectParam reflectPara);
	/**
	 * 反映する時刻を求める
	 * @param reflectPara
	 * @return
	 */
	public TimeOfDayReflectFindDto timeReflectFind(GoBackDirectlyReflectParam reflectPara);

}
