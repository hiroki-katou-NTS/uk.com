package nts.uk.ctx.at.record.app.find.monthlyclosureupdate;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class MonthlyClosureUpdateLogDto {

	// ID
	private String id;

	// 会社ID
	private String companyId;

	// 実行状況
	private int executionStatus;

	// 完了状態
	private int completeStatus;

	// 実行日時
	private GeneralDateTime executionDateTime;

	// 実行期間
	private GeneralDate executionStart;
	private GeneralDate executionEnd;

	// 実行社員ID
	private String executeEmployeeId;

	// 対象年月
	private int targetYearMonth;

	// 締めID
	private int closureId;

	// 締め日
	private int closureDay;
	private boolean isLastDayOfMont;

}
