package nts.uk.ctx.at.record.app.query.reservation;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReservationExportQuery {
	
	@Inject
	private ClosureService closureService;
	
	public DatePeriod startup() {
		String employeeID = AppContexts.user().employeeId();
		GeneralDate baseDate = GeneralDate.today();
		
		// 社員に対応する処理締めを取得する
		Closure closure = closureService.getClosureDataByEmployee(employeeID, baseDate);
		
		// 当月の年月を取得する
		YearMonth processingYm = closure.getClosureMonth().getProcessingYm();
		
		// 当月の期間を算出する
		DatePeriod period = closureService.getClosurePeriod(closure.getClosureId().value, processingYm);
		
		return period;
	}
	
}
