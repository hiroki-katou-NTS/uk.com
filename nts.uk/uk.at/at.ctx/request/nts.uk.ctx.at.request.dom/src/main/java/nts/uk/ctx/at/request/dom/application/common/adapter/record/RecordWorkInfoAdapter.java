package nts.uk.ctx.at.request.dom.application.common.adapter.record;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface RecordWorkInfoAdapter {
	
	public RecordWorkInfoImport getRecordWorkInfo(String employeeId, GeneralDate ymd);

}
