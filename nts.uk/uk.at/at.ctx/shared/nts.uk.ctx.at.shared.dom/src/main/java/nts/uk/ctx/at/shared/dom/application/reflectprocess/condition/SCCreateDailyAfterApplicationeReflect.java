package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author thanh_nx
 *
 *         [RQ668]申請反映後の日別勤怠(work）を作成する（勤務予定）
 */
public class SCCreateDailyAfterApplicationeReflect {
	
	//TODO: Can xu ly all
	public static DailyAfterAppReflectResult process(Require require, ApplicationShare application, IntegrationOfDaily domainDaily, GeneralDate date) {
		
		return new DailyAfterAppReflectResult(domainDaily, new ArrayList<>());
	}

	public static interface Require{
		
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class DailyAfterAppReflectResult{
		
		private IntegrationOfDaily domainDaily;
		
		private List<Integer> lstItemId;
		
	}
}
