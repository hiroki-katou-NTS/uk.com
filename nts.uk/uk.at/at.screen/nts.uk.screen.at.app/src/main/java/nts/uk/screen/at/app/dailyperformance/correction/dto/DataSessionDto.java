package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;

@Getter
@Setter
@NoArgsConstructor
public class DataSessionDto {
	
	List<DailyRecordDto> domainOlds;
	
	List<DailyRecordDto> domainOldForLog;
	
	List<DailyRecordDto> domainEdits;
	
	//mapDPAttendance
	Map<Integer, DPAttendanceItem> itemIdRCs ;
	
	//lstData
	List<DPDataDto> dataSource;
	
	Integer closureId;
	
	DailyPerformanceCorrectionDto resultReturn;
	
	ApprovalConfirmCache approvalConfirmCache;
	
	List<Pair<String, GeneralDate>> lstSidDateErrorCalc = new ArrayList<>();
	
	boolean errorAllCalc = false;
}
