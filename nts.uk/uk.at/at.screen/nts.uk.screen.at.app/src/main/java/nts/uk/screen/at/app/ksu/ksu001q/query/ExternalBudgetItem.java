package nts.uk.screen.at.app.ksu.ksu001q.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalBudgetItem {

	/** コード */
	private String code;

	/** 名称 */
	private String name;

	/** 属性 */
	private String attribute;

	/** 単位 */
	private String unit;
}
