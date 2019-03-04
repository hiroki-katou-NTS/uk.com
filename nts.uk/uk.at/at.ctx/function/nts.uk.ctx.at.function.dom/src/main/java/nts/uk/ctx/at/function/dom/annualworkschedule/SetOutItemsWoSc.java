package nts.uk.ctx.at.function.dom.annualworkschedule;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.AnnualWorkSheetPrintingForm;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.MonthsInTotalDisplay;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.TotalAverageDisplay;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScCode;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScName;

/**
* 年間勤務表（36チェックリスト）の出力項目設定
*/
@Getter
public class SetOutItemsWoSc extends AggregateRoot {
	/**
	* 会社ID
	*/
	private String cid;
	
	/**
	* コード
	*/
	private OutItemsWoScCode cd;
	
	/**
	* 名称
	*/
	private OutItemsWoScName name;

	/**
	* 36協定時間を超過した月数を出力する
	*/
	private boolean outNumExceedTime36Agr;
	
	/**
	 * 年間勤務表印刷形式
	 */
	private AnnualWorkSheetPrintingForm printForm;

	private List<ItemOutTblBook> listItemOutTblBook;
	
	//đối ứng ver22
	
	//複数月表示項目  --  Multi-month display items
	
	/**
	* 複数月表示
	*/
	private boolean multiMonthDisplay;
	
	/**
	* 合計表示の月数
	*/
	private Optional<MonthsInTotalDisplay> monthsInTotalDisplay;
	
	/**
	* 合計平均表示
	*/
	private TotalAverageDisplay totalAverageDisplay;
		
	
	public static SetOutItemsWoSc createFromJavaType(String cid, String cd, String name, boolean outNumExceedTime36Agr,
			int printForm, List<ItemOutTblBook> listItemOutTblBook, boolean multiMonthDisplay,
			Integer monthsInTotalDisplay, int totalAverageDisplay) {
		return new SetOutItemsWoSc(cid, new OutItemsWoScCode(cd),
				new OutItemsWoScName(name), outNumExceedTime36Agr,
				EnumAdaptor.valueOf(printForm, AnnualWorkSheetPrintingForm.class),listItemOutTblBook, multiMonthDisplay,
				monthsInTotalDisplay == null ? Optional.empty(): Optional.of(EnumAdaptor.valueOf(monthsInTotalDisplay, MonthsInTotalDisplay.class)),
				EnumAdaptor.valueOf(totalAverageDisplay, TotalAverageDisplay.class));
	}

	public SetOutItemsWoSc(String cid, OutItemsWoScCode cd, OutItemsWoScName name, boolean outNumExceedTime36Agr,
			AnnualWorkSheetPrintingForm printForm, List<ItemOutTblBook> listItemOutTblBook,
			boolean multiMonthDisplay, Optional<MonthsInTotalDisplay> monthsInTotalDisplay,
			TotalAverageDisplay totalAverageDisplay) {
		super();
		this.cid = cid;
		this.cd = cd;
		this.name = name;
		this.outNumExceedTime36Agr = outNumExceedTime36Agr;
		this.printForm = printForm;
		this.listItemOutTblBook = listItemOutTblBook;
		this.multiMonthDisplay = multiMonthDisplay;
		this.monthsInTotalDisplay = monthsInTotalDisplay;
		this.totalAverageDisplay = totalAverageDisplay;
	}
}
