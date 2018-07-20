package nts.uk.ctx.exio.dom.exo.execlog;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * 外部出力結果ログ
 */
@Getter
@Setter
@NoArgsConstructor
public class ExternalOutLog extends AggregateRoot {
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 外部出力処理ID
	 */
	private String outputProcessId;

	/**
	 * エラー内容
	 */
	private Optional<String> errorContent;

	/**
	 * エラー対象値
	 */
	private Optional<String> errorTargetValue;

	/**
	 * エラー日付
	 */
	private Optional<GeneralDate> errorDate;

	/**
	 * エラー社員
	 */
	private Optional<String> errorEmployee;

	/**
	 * エラー項目
	 */
	private Optional<String> errorItem;

	/**
	 * ログ登録日時
	 */
	private GeneralDateTime logRegisterDateTime;

	/**
	 * ログ連番
	 */
	private int logSequenceNumber;

	/**
	 * 処理カウント
	 */
	private int processCount;

	/**
	 * 処理内容
	 */
	private ProcessingClassification processContent;

	public ExternalOutLog(String companyId, String outputProcessId, String errorContent, String errorTargetValue,
			GeneralDate errorDate, String errorEmployee, String errorItem, GeneralDateTime logRegisterDateTime,
			int logSequenceNumber, int processCount, int processContent) {
		super();
		this.companyId = companyId;
		this.outputProcessId = outputProcessId;
		this.errorContent = Optional.ofNullable(errorContent);
		this.errorTargetValue = Optional.ofNullable(errorTargetValue);
		this.errorDate = Optional.ofNullable(errorDate);
		this.errorEmployee = Optional.ofNullable(errorEmployee);
		this.errorItem = Optional.ofNullable(errorItem);
		this.logRegisterDateTime = logRegisterDateTime;
		this.logSequenceNumber = logSequenceNumber;
		this.processCount = processCount;
		this.processContent = EnumAdaptor.valueOf(processContent, ProcessingClassification.class);
	}

}
