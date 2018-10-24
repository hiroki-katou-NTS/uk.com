package nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

@Getter
@Setter
public class MonthlyRecordValueImport {

	/** 年月 */
	private YearMonth yearMonth;
	/** 項目値リスト */
	private List<ItemValue> itemValues;

	/**
	 * コンストラクタ
	 */
	public MonthlyRecordValueImport(YearMonth yearMonth){
		this.yearMonth = yearMonth;
		this.itemValues = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * 
	 * @param yearMonth
	 *            年月
	 * @param itemValues
	 *            項目値リスト
	 * @return 月別実績データ値
	 */
	public static MonthlyRecordValueImport of(YearMonth yearMonth, List<ItemValue> itemValues) {

		MonthlyRecordValueImport domain = new MonthlyRecordValueImport(yearMonth);
		domain.itemValues = itemValues;
		return domain;
	}
}
