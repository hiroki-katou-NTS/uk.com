package nts.uk.ctx.at.function.app.find.annualworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalculationFormulaOfItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemsOutputToBookTable;

@Data
public class ItemsOutputToBookTableDto implements  ItemsOutputToBookTable.MementoSetter {

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
	private List<CalculationFormulaOfItemDto> listOperationSetting;

	@Override
	public void setListOperationSetting(List<CalculationFormulaOfItem> listOperationSetting) {
		this.listOperationSetting = listOperationSetting.stream().map(t -> {
			return CalculationFormulaOfItemDto.builder()
					.attendanceItemId(t.getAttendanceItemId())
					.operation(t.getOperation())
					.build();
		}).collect(Collectors.toList());
	}

}
