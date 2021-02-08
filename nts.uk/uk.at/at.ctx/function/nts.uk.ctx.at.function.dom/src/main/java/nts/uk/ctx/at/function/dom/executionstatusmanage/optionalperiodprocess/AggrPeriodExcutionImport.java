package nts.uk.ctx.at.function.dom.executionstatusmanage.optionalperiodprocess;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDateTime;

/**
 *	 任意期間集計実行ログ
 */
@Data
@Builder
public class AggrPeriodExcutionImport {
	/** 会社ID */
	private String companyId;

	/** 実行社員ID */
	private String executionEmpId;

	/** 集計枠コード */
	private String aggrFrameCode;

	/** ID */
	private String aggrId;

	/** 開始日時 */
	private GeneralDateTime startDateTime;

	/** 終了日時 */
	private GeneralDateTime endDateTime;

	/** 実行区分 */
	private int executionAtr;

	/** 実行状況 */
	private Optional<Integer> executionStatus;

	/** エラーの有無 */
	private int presenceOfError;
}
