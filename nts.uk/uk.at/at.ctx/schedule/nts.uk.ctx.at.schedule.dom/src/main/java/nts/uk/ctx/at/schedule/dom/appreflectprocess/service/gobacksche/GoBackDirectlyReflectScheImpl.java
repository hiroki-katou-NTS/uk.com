package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.ApplicationReflectParam;

@Stateless
public class GoBackDirectlyReflectScheImpl implements GoBackDirectlyReflectSche{
	@Inject
	private WorkTypeHoursReflectSche workTypeHoursReflectSche;
	@Inject
	private TimeOfDayReflectGoBackSche gobackSche;
	@Override
	public boolean goBackDirectlyReflectSch(ApplicationReflectParam reflectPara) {
		//勤種・就時の反映
		boolean workTypeReflect = workTypeHoursReflectSche.isReflectFlag(reflectPara);
		//時刻の反映
		reflectPara.setOutsetBreakReflectAtr(workTypeReflect);
		boolean scheInfo = gobackSche.stampReflectGobackSche(reflectPara);
		return scheInfo;
	}

}
