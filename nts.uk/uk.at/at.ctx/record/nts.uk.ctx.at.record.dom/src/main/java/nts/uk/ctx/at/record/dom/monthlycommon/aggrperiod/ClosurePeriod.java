package nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 締め処理期間
 * @author shuichu_ishida
 */
@Getter
public class ClosurePeriod {

	/** 締めID */
	private ClosureId closureId;
	/** 締め日 */
	private ClosureDate closureDate;
	/** 年月 */
	private YearMonth yearMonth;
	/** 締め年月日 */
	private GeneralDate closureYmd;
	/** 集計期間 */
	private List<AggrPeriodEachActualClosure> aggrPeriods;
	
	/**
	 * コンストラクタ
	 */
	public ClosurePeriod(){
	
		this.closureId = ClosureId.RegularEmployee;
		this.closureDate = new ClosureDate(0, true);
		this.yearMonth = YearMonth.of(GeneralDate.today().year(), GeneralDate.today().month());
		this.closureYmd = GeneralDate.today();
		this.aggrPeriods = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param yearMonth 年月
	 * @param closureYmd 締め年月日
	 * @param aggrPeriods 集計期間
	 * @return 締め処理期間
	 */
	public static ClosurePeriod of(
			ClosureId closureId, ClosureDate closureDate, YearMonth yearMonth, GeneralDate closureYmd,
			List<AggrPeriodEachActualClosure> aggrPeriods){
		
		ClosurePeriod domain = new ClosurePeriod();
		domain.closureId = closureId;
		domain.closureDate = closureDate;
		domain.yearMonth = yearMonth;
		domain.closureYmd = closureYmd;
		domain.aggrPeriods = aggrPeriods;
		return domain;
	}
}
