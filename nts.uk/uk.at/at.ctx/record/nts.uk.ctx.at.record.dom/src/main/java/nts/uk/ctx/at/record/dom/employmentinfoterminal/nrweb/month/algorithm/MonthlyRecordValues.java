package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.month.algorithm;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 月別実績データ値
 * @author shuichu_ishida
 */
@Getter
public class MonthlyRecordValues {

	/** 年月 */
	private YearMonth yearMonth;
	/** 締めID */
	private ClosureId closureId;
	/** 締め日付 */
	private ClosureDate closureDate;
	/** 項目値リスト */
	private List<ItemValue> itemValues;
	
	/**
	 * コンストラクタ
	 */
	public MonthlyRecordValues(YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate){
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.itemValues = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param itemValues 項目値リスト
	 * @return 月別実績データ値
	 */
	public static MonthlyRecordValues of(
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			List<ItemValue> itemValues){
	
		MonthlyRecordValues domain = new MonthlyRecordValues(yearMonth, closureId, closureDate);
		domain.itemValues = itemValues;
		return domain;
	}
}
