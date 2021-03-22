package nts.uk.ctx.exio.app.command.exo.condset;

import lombok.Data;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;

import java.util.List;

/**
 * The class Standard output condition setting command.<br>
 * Command 出力条件設定（定型）
 */
@Data
public class StdOutputCondSetCommand implements StdOutputCondSet.MementoGetter {

	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 外部出力条件コード
	 */
	private String conditionSetCode;

	/**
	 * カテゴリID
	 */
	private int categoryId;

	/**
	 * 区切り文字
	 */
	private int delimiter;

	/**
	 * するしない区分
	 */
	private int itemOutputName;

	/**
	 * するしない区分
	 */
	private int autoExecution;

	/**
	 * 外部出力条件名称
	 */
	private String conditionSetName;

	/**
	 * するしない区分
	 */
	private int conditionOutputName;

	/**
	 * 文字列形式
	 */
	private int stringFormat;

	private Long version;

	private String copyDestinationCode;

	private boolean overWrite;

	private boolean newMode;

	private String destinationName;

	private int standType;

	private List<StdOutItemOrderCommand> listStandardOutputItem;

}
