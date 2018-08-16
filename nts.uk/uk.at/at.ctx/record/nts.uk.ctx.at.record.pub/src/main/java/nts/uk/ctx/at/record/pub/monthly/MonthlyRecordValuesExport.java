package nts.uk.ctx.at.record.pub.monthly;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

/**
 * 月別実績データ値
 * @author shuichu_ishida
 */
@Getter
public class MonthlyRecordValuesExport {

	/** 年月 */
	private YearMonth yearMonth;
	/** 項目値リスト */
	private List<ItemValue> itemValues;
	
	/**
	 * コンストラクタ
	 */
	public MonthlyRecordValuesExport(YearMonth yearMonth){
		this.yearMonth = yearMonth;
		this.itemValues = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param yearMonth 年月
	 * @param itemValues 項目値リスト
	 * @return 月別実績データ値
	 */
	public static MonthlyRecordValuesExport of(
			YearMonth yearMonth,
			List<ItemValue> itemValues){
	
		MonthlyRecordValuesExport domain = new MonthlyRecordValuesExport(yearMonth);
		domain.itemValues = itemValues;
		return domain;
	}
}
