package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.app.find.alarm.AlarmPatternSettingDto;
//import nts.uk.ctx.at.function.app.find.condset.dto.StdOutputCondSetDto;
import nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod.AggrPeriodDto;

import java.util.List;

/**
 * The class Master info dto.
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class MasterInfoDto {

	private List<AggrPeriodDto> aggrPeriodList;

	private List<AlarmPatternSettingDto> alarmPatternSettingList;

//	private List<StdOutputCondSetDto> stdOutputCondSetList;

	private List<?> x2List;

	private List<?> x3List;

	private List<?> x4List;

	private List<?> x5List;

}
