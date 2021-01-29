package nts.uk.ctx.at.function.app.command.annualworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalculationFormulaOfItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemsOutputToBookTable;

@Value
public class ItemsOutputToBookTableCommand implements ItemsOutputToBookTable.MementoGetter {

	/** コード. */
	private String itemOutCd;

	/** 並び順. */
	private int sortBy;

	/** 見出し名称. */
	private String headingName;
	
	/** 使用区分 */
	private boolean useClass;

	/** 値の出力形式 */
	private int valOutFormat;

	/** 出力対象項目 */
	private List<CalculationFormulaOfItemCommand> listOperationSetting;
	
	public List<CalculationFormulaOfItem> getListOperationSetting() {
		return this.listOperationSetting.stream()
				.map(t -> new CalculationFormulaOfItem(t.getOperation(), t.getAttendanceItemId()))
				.collect(Collectors.toList());
	}
}
