package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * データ復旧動作管理
 */
@Getter
public class DataRecoveryMng extends AggregateRoot {

	/**
	 * 処理ID
	 */
	private String dataRecoveryProcessId;

	/**
	 * エラー件数
	 */
	private Integer errorCount;

	/**
	 * カテゴリカウント
	 */
	private Integer categoryCnt;

	/**
	 * カテゴリトータルカウント
	 */
	private Integer categoryTotalCount;

	/**
	 * トータル処理件数
	 */
	private Optional<Integer> totalNumOfProcesses;

	/**
	 * 処理件数
	 */
	private Optional<Integer> numOfProcesses;

	/**
	 * 処理対象社員コード
	 */
	private Optional<String> processTargetEmpCode;

	/**
	 * 中断状態
	 */
	private NotUseAtr suspendedState;

	/**
	 * 動作状態
	 */
	private DataRecoveryOperatingCondition operatingCondition;

	/**
	 * 復旧日付
	 */
	private GeneralDate recoveryDate;

	public DataRecoveryMng(String dataRecoveryProcessId, Integer errorCount, Integer categoryCnt,
			Integer categoryTotalCount, Integer totalNumOfProcesses, Integer numOfProcesses,
			String processTargetEmpCode, Integer suspendedState, Integer operatingCondition, GeneralDate recoveryDate) {
		this.dataRecoveryProcessId = dataRecoveryProcessId;
		this.errorCount            = errorCount;
		this.categoryCnt           = categoryCnt;
		this.categoryTotalCount    = categoryTotalCount;
		this.totalNumOfProcesses   = Optional.ofNullable(totalNumOfProcesses);
		this.numOfProcesses        = Optional.ofNullable(numOfProcesses);
		this.processTargetEmpCode  = Optional.ofNullable(processTargetEmpCode);
		this.suspendedState        = EnumAdaptor.valueOf(suspendedState, NotUseAtr.class);
		this.operatingCondition    = EnumAdaptor.valueOf(operatingCondition, DataRecoveryOperatingCondition.class);
		this.recoveryDate          = recoveryDate;
	}
}
