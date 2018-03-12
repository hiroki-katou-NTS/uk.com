package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;

import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.MessageWRExtraConAdapterDto;


public interface WorkRecordExtraConAdapter {
	List<WorkRecordExtraConAdapterDto> getAllWorkRecordExtraConByListID(List<String> listErrorAlarmID );
	
	WorkRecordExtraConAdapterDto getWorkRecordExtraConById(String errorAlarmCheckID);
	
	void addWorkRecordExtraConPub(WorkRecordExtraConAdapterDto workRecordExtraConPubExport);
	
	void updateWorkRecordExtraConPub(WorkRecordExtraConAdapterDto workRecordExtraConPubExport);
	
	void deleteWorkRecordExtraConPub(List<String> listErrorAlarmID);
	
	List<String> checkUpdateListErAl(List<String> listErrorAlarmCheckID,List<WorkRecordExtraConAdapterDto> listErroAlarm);
	
	 List<String> addNewListErAl(List<WorkRecordExtraConAdapterDto> listErroAlarm);
	 
	 List<MessageWRExtraConAdapterDto> getMessageWRExtraConByListID(List<String> listErrorAlarmID );
	
}
