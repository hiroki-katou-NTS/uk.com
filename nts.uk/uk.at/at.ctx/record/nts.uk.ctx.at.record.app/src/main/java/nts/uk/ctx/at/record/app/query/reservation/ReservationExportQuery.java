package nts.uk.ctx.at.record.app.query.reservation;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReservationExportQuery {
	
	@Inject 
	private RecordDomRequireService requireService;
	
	public DatePeriod startup() {
		String employeeID = AppContexts.user().employeeId();
		GeneralDate baseDate = GeneralDate.today();
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		// 社員に対応する処理締めを取得する
		Closure closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, 
				employeeID, baseDate);
		
		// 当月の年月を取得する
		YearMonth processingYm = closure.getClosureMonth().getProcessingYm();
		
		// 当月の期間を算出する
		DatePeriod period = ClosureService.getClosurePeriod(closure.getClosureId().value,
				processingYm, Optional.of(closure));
		
		return period;
	}
	
}
