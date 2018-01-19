package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;


public interface WorkRecordExtraConAdapter {
	List<WorkRecordExtraConAdapterDto> getAllWorkRecordExtraConByListID(List<String> listErrorAlarmID );
	
	WorkRecordExtraConAdapterDto getWorkRecordExtraConById(String errorAlarmCheckID);
}
