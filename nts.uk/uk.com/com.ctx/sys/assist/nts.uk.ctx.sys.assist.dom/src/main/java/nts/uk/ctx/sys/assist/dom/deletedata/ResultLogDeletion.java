package nts.uk.ctx.sys.assist.dom.deletedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Getter
@Setter
@AllArgsConstructor
/**
 * データ削除の結果ログ
 */
public class ResultLogDeletion extends AggregateRoot {

	// SEG_ID
	/** The sequence id */
	private int seqId;

	// データ削除処理ID
	/** The deletion Id. */
	private String delId;

	// 会社ID
	/** The company Id. */
	private String companyId;

	// ログ登録日時
	/** The log time. */
	private GeneralDateTime logTime;

	// 処理内容
	/** The processing content. */
	private ProcessingContent processingContent;

	// エラー内容
	/** The error content. */
	private ErrorContent errorContent;

	// エラー社員
	/** The error employee id. */
	private String errorEmployeeId;

	// エラー日付
	/** The error date. */
	private GeneralDate errorDate;

	public static ResultLogDeletion createFromJavatype(int seqId, String delId, String companyId, GeneralDateTime logTime,
			String processingContent, String errorContent, String errorEmployeeId,
			GeneralDate errorDate) {
		return new ResultLogDeletion(seqId, delId, companyId, logTime,
				new ProcessingContent(processingContent), new ErrorContent(errorContent),
				errorEmployeeId, errorDate);
	}
}
