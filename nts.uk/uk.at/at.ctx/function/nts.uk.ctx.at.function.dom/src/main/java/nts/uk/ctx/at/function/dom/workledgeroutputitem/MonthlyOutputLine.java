package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;

import java.util.List;

/**
 * 月次出力１行
 */
@Getter
@Setter
@AllArgsConstructor
public class MonthlyOutputLine {

	// 勤怠項目の値
    private List<MonthlyValue> attendanceItemValueList;

	// 勤怠項目名称
	private String attendanceItemName;

	// 印刷順位
	private int printOrder;

	// 合計
	private double total;

	// 属性
	private CommonAttributesOfForms attribute;
}
