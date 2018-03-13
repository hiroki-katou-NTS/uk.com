package nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.appreflectprocess.ReflectedStatesScheInfo;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.GoBackDirectlyReflectParam;

@Stateless
public class GoBackDirectlyReflectScheImpl implements GoBackDirectlyReflectSche{
	@Inject
	private WorkTypeHoursReflectSche workTypeHoursReflectSche;
	@Inject
	private TimeOfDayReflectGoBackSche gobackSche;
	@Override
	public ReflectedStatesScheInfo goBackDirectlyReflectSch(GoBackDirectlyReflectParam reflectPara) {
		//勤種・就時の反映
		boolean workTypeReflect = workTypeHoursReflectSche.isReflectFlag(reflectPara);
		//時刻の反映
		ReflectedStatesScheInfo scheInfo = gobackSche.stampReflectGobackSche(reflectPara);
		return scheInfo;
	}

}
