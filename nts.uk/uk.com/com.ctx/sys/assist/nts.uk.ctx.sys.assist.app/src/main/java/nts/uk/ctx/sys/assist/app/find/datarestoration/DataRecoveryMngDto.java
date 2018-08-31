package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryOperatingCondition;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@AllArgsConstructor
@Value
public class DataRecoveryMngDto {
	/**
	 * 処理ID
	 */
	private String dataRecoveryProcessId;

	/**
	 * エラー件数
	 */
	private int errorCount;

	/**
	 * カテゴリカウント
	 */
	private int categoryCnt;

	/**
	 * カテゴリトータルカウント
	 */
	private int categoryTotalCount;

	/**
	 * トータル処理件数
	 */
	private int totalNumOfProcesses;

	/**
	 * 処理件数
	 */
	private int numOfProcesses;

	/**
	 * 処理対象社員コード
	 */
	private String processTargetEmpCode;

	/**
	 * 中断状態
	 */
	private int suspendedState;

	/**
	 * 動作状態
	 */

	private int operatingCondition;

	/**
	 * 復旧日付
	 */
	private String recoveryDate;

	public static DataRecoveryMngDto fromDomain(DataRecoveryMng domain) {
		return new DataRecoveryMngDto(domain.getDataRecoveryProcessId(), domain.getErrorCount(),
				domain.getCategoryCnt(), domain.getCategoryTotalCount(), domain.getTotalNumOfProcesses().orElse(0),
				domain.getNumOfProcesses().orElse(0), domain.getProcessTargetEmpCode().orElse(""),
				domain.getSuspendedState().value, domain.getOperatingCondition().value, domain.getRecoveryDate());
	}

}
