package nts.uk.ctx.at.shared.dom.workrule.closure;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 締め情報
 * @author shuichu_ishida
 */
@Getter
public class ClosureInfo {

	/** 締めID */
	private ClosureId closureId;
	
	/** 締め日 */
	private ClosureDate closureDate;
	/** 締め名称 */
	private ClosureName closureName;
	/** 当月 */
	private YearMonth currentMonth;
	/** 期間 */
	private DatePeriod period;
	
	/**
	 * コンストラクタ
	 * @param closureId 締めID
	 */
	public ClosureInfo(ClosureId closureId){
		
		this.closureId = closureId;
		this.closureDate = new ClosureDate(0, true);
		this.closureName = new ClosureName("");
		this.currentMonth = YearMonth.of(GeneralDate.today().year(), GeneralDate.today().month()); 
		this.period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
	}
	
	/**
	 * ファクトリー
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param closureName 締め名称
	 * @param currentMonth 当月
	 * @param period 期間
	 * @return 締め情報
	 */
	public static ClosureInfo of(
			ClosureId closureId,
			ClosureDate closureDate,
			ClosureName closureName,
			YearMonth currentMonth,
			DatePeriod period){
		
		ClosureInfo domain = new ClosureInfo(closureId);
		domain.closureDate = closureDate;
		domain.closureName = closureName;
		domain.currentMonth = currentMonth;
		domain.period = period;
		return domain;
	}
}
