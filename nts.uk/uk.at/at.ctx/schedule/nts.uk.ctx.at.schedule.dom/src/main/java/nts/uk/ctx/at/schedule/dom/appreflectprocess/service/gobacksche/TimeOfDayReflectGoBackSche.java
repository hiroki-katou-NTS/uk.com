package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche;

import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.ApplicationReflectParam;
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
	public boolean stampReflectGobackSche(ApplicationReflectParam reflectPara);
	/**
	 * 反映する時刻を求める
	 * @param reflectPara
	 * @return
	 */
	public TimeOfDayReflectFindDto timeReflectFind(ApplicationReflectParam reflectPara);

}
