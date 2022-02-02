package nts.uk.ctx.exio.dom.input.canonicalize.methods;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.Either;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;

/**
 *正準化の中間結果が持ってる開始日・終了日を期間に正準化する 
 *開始日・終了日を期間に正準化する 
 */
@AllArgsConstructor
public class DatePeriodCanonicalization {
	
	private final int startItemNo;
	private final int endItemNo;
	
	 public Either<ErrorMessage, DatePeriod> getPeriod(IntermediateResult interm){
		
		val startDate = interm.getItemByNo(startItemNo).get().getDate();
		val endDate = interm.getItemByNo(endItemNo).get().getDate();
		
		val period = new DatePeriod(startDate, endDate);
		
		if (period.isReversed()) {
			return Either.left(new ErrorMessage("開始日と終了日が逆転しています。"));
		}
		return Either.right(period);
	}
}
