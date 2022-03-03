package nts.uk.screen.at.app.dailymodify.command.common;

import java.util.*;

import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.TypeError;
import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.dailyperform.checkdata.RCDailyCorrectionResult;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.pastmonth.AggregatePastMonthResult;
import nts.uk.screen.at.app.dailyperformance.correction.checkdata.dto.ErrorAfterCalcDaily;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataResultAfterIU;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ResultReturnDCUpdateData;

@AllArgsConstructor
@Data
public class DailyCalcResult {
	
	private DataResultAfterIU dataResultAfterIU;
	
	private RCDailyCorrectionResult resultUI;
	
	private ErrorAfterCalcDaily errorAfterCheck;
	
	private Map<Pair<String, GeneralDate>, ResultReturnDCUpdateData> lstResultDaiRowError = new HashMap<>();
	
	private List<AggregatePastMonthResult> listAggregatePastMonthResult = new ArrayList<>();

	public List<DPItemValue> getListItemErrorMonth(){
		if(this.errorAfterCheck == null || !this.listAggregatePastMonthResult.isEmpty()) {
			return Collections.emptyList();
		}

		List<DPItemValue> result = new ArrayList<>();
		List<DPItemValue> lstItemErrorMonth = errorAfterCheck.getResultErrorMonth()
			.get(TypeError.ERROR_MONTH.value);
		if (lstItemErrorMonth != null) {
			result.addAll(lstItemErrorMonth);

			List<DPItemValue> itemErrorMonth = dataResultAfterIU.getErrorMap()
				.get(TypeError.ERROR_MONTH.value);
			if (itemErrorMonth != null) {
				result.addAll(itemErrorMonth);
			}
		}
		return result;
	}
}
