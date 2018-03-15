package nts.uk.ctx.at.request.dom.applicationreflect.workschedule.service;

import nts.uk.ctx.at.request.dom.applicationreflect.workschedule.ReflectScheDto;
import nts.uk.ctx.at.request.dom.applicationreflect.workschedule.ReflectedStatesInfo;

public interface ApplicationReflectProcessSche {
	/**
	 * 直行直帰申請
	 * @param reflectSche
	 * @return
	 */
	public boolean goBackDirectlyReflect(ReflectScheDto reflectSche);
	/**
	 * 休暇申請
	 * @param reflectSche
	 */
	public void forleaveReflect(ReflectScheDto reflectSche);

}
