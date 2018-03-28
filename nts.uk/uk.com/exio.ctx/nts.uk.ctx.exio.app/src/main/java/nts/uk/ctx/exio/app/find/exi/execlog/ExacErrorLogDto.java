package nts.uk.ctx.exio.app.find.exi.execlog;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLog;

/**
 * 外部受入エラーログ
 */
@AllArgsConstructor
@Value
public class ExacErrorLogDto {

	/**
	 * ログ連番
	 */
	private int logSeqNumber;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 外部受入処理ID
	 */
	private String externalProcessId;

	/**
	 * CSVエラー項目名
	 */
	private String csvErrorItemName;

	/**
	 * CSV受入値
	 */
	private String csvAcceptedValue;

	/**
	 * エラー内容
	 */
	private String errorContents;

	/**
	 * レコード番号
	 */
	private int recordNumber;

	/**
	 * ログ登録日時
	 */
	private GeneralDateTime logRegDateTime;

	/**
	 * 項目名
	 */
	private String itemName;

	/**
	 * エラー発生区分
	 */
	private int errorAtr;

	private Long version;

	public static ExacErrorLogDto fromDomain(ExacErrorLog domain) {
		return new ExacErrorLogDto(domain.getLogSeqNumber(), domain.getCid(), domain.getExternalProcessId(),
				domain.getCsvErrorItemName().get(), domain.getCsvAcceptedValue().get(), domain.getErrorContents().get(),
				domain.getRecordNumber().v(), domain.getLogRegDateTime(), domain.getItemName().get(),
				domain.getErrorAtr().value, domain.getVersion());
	}

}
