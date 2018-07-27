package nts.uk.screen.at.app.monthlyperformance.correction;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.spi.ThrowableRendererSupport;

import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.divergence.time.message.ErrorAlarmMessage;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ActualTime;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ActualTimeState;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class MonthlyPerformanceCheck {
	
	@Inject
	private ClosureService closureService;
	
	@Inject
	private ClosureRepository closureRepo;
	
	/**
	 * アルゴリズム「実績の時系列をチェックする」を実行する
	 */	
	public ActualTimeState checkActualTime(Integer closureId, Integer processingYm, ActualTime actualTime){
		String companyId = AppContexts.user().companyId();
		Optional<Closure> optClosure = closureRepo.findById(companyId, closureId);
		if (!optClosure.isPresent()) throw new BusinessException("Cannot find closure with ID=" + closureId);
		//当月の期間を算出する  tính toán thời gian tháng này		
		DatePeriod actualDate = closureService.getClosurePeriod(closureId, optClosure.get().getClosureMonth().getProcessingYm());
		//取得した期間を、実績期間と比較する so sánh thời gian đã lấy với thời gian thực tế
		//実績期間　＜　開始年月日(thời gian thực tế < start date
		if(actualTime.getEndDate().before(actualDate.start())){
			return ActualTimeState.Past;
		}
		//終了年月日　＜　実績期間(end date < thời gian thực tế)
		else if(actualTime.getStartDate().after(actualDate.end())){
			return ActualTimeState.Future;
		}
		//開始年月日　＜＝　実績期間　＜＝　終了年月日(start date <=thời gian thực tế<=end date)
//		else if(actualTime.getStartDate().afterOrEquals(actualDate.start()) && actualTime.getEndDate().beforeOrEquals(actualDate.end()))
		else {
			return ActualTimeState.CurrentMonth;
		}
	}
}
