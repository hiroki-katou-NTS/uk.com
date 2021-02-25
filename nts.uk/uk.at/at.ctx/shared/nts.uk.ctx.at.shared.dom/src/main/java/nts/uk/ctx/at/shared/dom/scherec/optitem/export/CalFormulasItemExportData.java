package nts.uk.ctx.at.shared.dom.scherec.optitem.export;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalFormulasItemExportData {
	// 任意項目NO
	private String optionalItemNo;

	// 任意項目名称
	private String optionalItemName;

	//// 雇用条件区分
	private String empConditionAtr;

	private Map<String, String> empConditions;
}
