package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;


public interface FixedConWorkRecordAdapter {

	/**
	 * get all fixed condition work record by list errorAlarmID
	 * @return
	 */
	List<FixedConWorkRecordAdapterDto> getAllFixedConWorkRecordByListID(List<String> listErrorAlarmID);

	/**
	 * get fixed condition work record by dailyAlarmConID and fixConWorkRecordNo
	 * @param errorAlarmID
	 * @param fixConWorkRecordNo
	 * @return
	 */
	FixedConWorkRecordAdapterDto getFixedConWRByCode(String errorAlarmID);
}
