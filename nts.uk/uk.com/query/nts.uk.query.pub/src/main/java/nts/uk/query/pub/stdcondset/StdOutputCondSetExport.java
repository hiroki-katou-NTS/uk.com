package nts.uk.query.pub.stdcondset;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class EmployeeInfoModel.
 */
@Data
@AllArgsConstructor
public class StdOutputCondSetExport {

	/** 会社ID */
	private String cid;

	/** 外部出力条件コード */
	private String conditionSetCd;

	/** カテゴリID */
	private int categoryId;

	/** 区切り文字 */
	private int delimiter;

	/** するしない区分 */
	private int itemOutputName;

	/** するしない区分 */
	private int autoExecution;

	/** 外部出力条件名称 */
	private String conditionSetName;

	/** するしない区分 */
	private int conditionOutputName;

	/** 文字列形式 */
	private int stringFormat;

}
