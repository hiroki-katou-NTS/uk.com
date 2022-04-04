package nts.uk.ctx.exio.app.command.exo.condset;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;

/**
 * The class Standard output condition setting command.<br>
 * Command 出力条件設定（定型）
 */
@Data
@NoArgsConstructor
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

	private int encodeType;

	private String fileName;

	private List<StdOutItemOrderCommand> listStandardOutputItem;

	public StdOutputCondSetCommand(String companyId, String conditionSetCode, int categoryId, int delimiter,
			int itemOutputName, int autoExecution, String conditionSetName, int conditionOutputName, int stringFormat,
			Long version, String copyDestinationCode, boolean overWrite, boolean newMode, String destinationName,
			int standType, int encodeType, String fileName, List<StdOutItemOrderCommand> listStandardOutputItem) {
		super();
		this.companyId = companyId;
		this.conditionSetCode = conditionSetCode;
		this.categoryId = categoryId;
		this.delimiter = delimiter;
		this.itemOutputName = itemOutputName;
		this.autoExecution = autoExecution;
		this.conditionSetName = conditionSetName;
		this.conditionOutputName = conditionOutputName;
		this.stringFormat = stringFormat;
		this.version = version;
		this.copyDestinationCode = copyDestinationCode;
		this.overWrite = overWrite;
		this.newMode = newMode;
		this.destinationName = destinationName;
		this.standType = standType;
		this.encodeType = encodeType;
		this.fileName = fileName;
		this.listStandardOutputItem = listStandardOutputItem;
	}

}
