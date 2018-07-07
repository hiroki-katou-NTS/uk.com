package nts.uk.ctx.exio.app.command.exo.condset;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;


@Value
public class StdOutputCondSetCommand {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 外部出力条件コード
	 */
	private String conditionSetCd;

	/**
	 * カテゴリID
	 */
	private String categoryId;

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
	
	private String screenMode;
	
	private String destinationName;
	
	private String standType;
	
	private boolean checkAutoExecution;
	
	private List<StandardOutputItemOrder>  stdOutItemOrder;

}
