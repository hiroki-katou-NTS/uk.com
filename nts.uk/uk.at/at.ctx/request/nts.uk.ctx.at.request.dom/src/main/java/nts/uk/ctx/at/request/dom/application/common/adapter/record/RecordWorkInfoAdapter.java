package nts.uk.ctx.at.request.dom.application.common.adapter.record;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface RecordWorkInfoAdapter {
	
	public RecordWorkInfoImport_Old getRecordWorkInfo(String employeeId, GeneralDate ymd);
	
	public RecordWorkInfoImport getRecordWorkInfoRefactor(String employeeId, GeneralDate ymd);

}
