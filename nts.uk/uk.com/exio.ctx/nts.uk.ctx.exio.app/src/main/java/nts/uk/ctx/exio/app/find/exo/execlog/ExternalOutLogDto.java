package nts.uk.ctx.exio.app.find.exo.execlog;

import lombok.Value;
import nts.uk.ctx.exio.dom.exo.execlog.ExternalOutLog;

/**
 * 外部出力結果ログ
 */
@Value
public class ExternalOutLogDto {
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
	private String errorContent;

	/**
	 * エラー対象値
	 */
	private String errorTargetValue;

	/**
	 * エラー日付
	 */
	private String errorDate;

	/**
	 * エラー社員
	 */
	private String errorEmployee;

	/**
	 * エラー項目
	 */
	private String errorItem;

	/**
	 * ログ登録日時
	 */
	private String logRegisterDateTime;

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
	private int processContent;

	public static ExternalOutLogDto fromDomain(ExternalOutLog domain) {
		return new ExternalOutLogDto(domain.getCompanyId(), 
				domain.getOutputProcessId(),
				domain.getErrorContent().orElse(null), 
				domain.getErrorTargetValue().orElse(null),
				domain.getErrorDate().map(i -> i.toString()).orElse(null),
				domain.getErrorEmployee().map(i ->i.toString()).orElse(null),
				domain.getErrorItem().map(i ->i.toString()).orElse(null),
				domain.getLogRegisterDateTime().toString(),
				domain.getLogSequenceNumber(), 
				domain.getProcessCount(), 
				domain.getProcessContent().value);
	}

}
