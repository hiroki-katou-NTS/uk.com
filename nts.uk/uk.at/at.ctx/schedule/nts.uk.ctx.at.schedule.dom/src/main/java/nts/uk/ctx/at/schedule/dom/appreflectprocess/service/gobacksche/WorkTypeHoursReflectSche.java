package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche;

import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.ApplicationReflectParam;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;

public interface WorkTypeHoursReflectSche {
	/**
	 * 勤種・就時の反映
	 * @param gobackPara
	 * @return
	 */
	public boolean isReflectFlag(ApplicationReflectParam gobackPara);
	/**
	 * 勤種・就時を反映できるかチェックする
	 * @param gobackPara
	 * @return 反映できるフラグ（true, false）
	 */
	public boolean isCheckReflect(ApplicationReflectParam gobackPara, BasicSchedule basicScheOpt);
}
