package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import nts.uk.ctx.at.request.dom.applicationreflect.service.ReflectInformationResult;

/**
 * 勤務実績に反映
 * @author do_dt
 *
 */
public interface WorkRecordReflectService {
	/**
	 * 勤務実績に反映
	 * @return
	 */
	public ReflectInformationResult workRecordreflect(AppReflectRecordPara recordInfor);
	
	

}
