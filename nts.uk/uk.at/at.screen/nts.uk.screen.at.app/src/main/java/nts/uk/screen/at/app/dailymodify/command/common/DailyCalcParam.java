package nts.uk.screen.at.app.dailymodify.command.common;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyCalcParam {

	private Map<Pair<String, GeneralDate>, List<DPItemValue>> mapSidDateItemEdit;

	private List<DPItemValue> lstNotFoundWorkType;

	private List<DailyModifyResult> resultOlds;
	
	private DateRange dateRange;
	
	private List<DailyRecordDto> dailyDtoEditAll;
	
	private List<DPItemValue> itemValues;
}
