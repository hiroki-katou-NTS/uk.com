package nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.service;

import nts.uk.ctx.at.schedule.dom.appreflectprocess.ReflectedStatesScheInfo;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.GoBackDirectlyReflectParam;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.TimeOfDayReflectFindDto;
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
