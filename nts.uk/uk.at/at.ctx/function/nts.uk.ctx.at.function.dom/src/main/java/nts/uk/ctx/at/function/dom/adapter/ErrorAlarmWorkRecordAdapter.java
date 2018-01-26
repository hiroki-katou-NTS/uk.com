package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;



public interface ErrorAlarmWorkRecordAdapter {
	//get error by eralCheckId
	ErrorAlarmWorkRecordAdapterDto findByErrorAlamCheckId(String eralCheckId);
			
	//get error by list eralCheckId
	List<ErrorAlarmWorkRecordAdapterDto> findByListErrorAlamCheckId(List<String> listEralCheckId);
	
	//	get all error by companyID
	List<ErrorAlarmWorkRecordAdapterDto> getAllErrorAlarmWorkRecord(String companyID);
}
