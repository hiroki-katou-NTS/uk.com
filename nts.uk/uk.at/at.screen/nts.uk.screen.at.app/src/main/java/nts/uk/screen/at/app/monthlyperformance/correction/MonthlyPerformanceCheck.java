package nts.uk.screen.at.app.monthlyperformance.correction;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ActualTime;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ActualTimeState;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class MonthlyPerformanceCheck {
	@Inject
	ClosureService closureService;
	/**
	 * アルゴリズム「実績の時系列をチェックする」を実行する
	 */	
	public ActualTimeState checkActualTime(Integer closureId, Integer processingYm, ActualTime actualTime){
		//当月の期間を算出する  tính toán thời gian tháng này		
		DatePeriod actualDate = closureService.getClosurePeriod(closureId, YearMonth.of(processingYm));
		//取得した期間を、実績期間と比較する so sánh thời gian đã lấy với thời gian thực tế
		//実績期間　＜　開始年月日(thời gian thực tế < start date
		if(actualTime.getStartDate().before(actualDate.start())){
			return ActualTimeState.Past;
		}
		//開始年月日　＜＝　実績期間　＜＝　終了年月日(start date <=thời gian thực tế<=end date)
		else if(actualTime.getStartDate().afterOrEquals(actualDate.start()) && actualTime.getEndDate().beforeOrEquals(actualDate.end())){
			return ActualTimeState.CurrentMonth;
		}
		//終了年月日　＜　実績期間(end date < thời gian thực tế)
		else if(actualTime.getEndDate().after(actualDate.end())){
			return ActualTimeState.Future;
		}
		
		//default null
		return null;
	}
}
