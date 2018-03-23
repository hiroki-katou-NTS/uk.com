package nts.uk.ctx.exio.app.find.exi.opmanage;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exi.opmanage.ExAcOpManage;

/**
 * 外部受入動作管理
 */
@AllArgsConstructor
@Value
public class ExAcOpManageDto {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 外部受入処理ID
	 */
	private String processId;

	/**
	 * エラー件数
	 */
	private int errorCount;

	/**
	 * 中断するしない
	 */
	private int interruption;

	/**
	 * 処理カウント
	 */
	private int processCount;

	/**
	 * 処理トータルカウント
	 */
	private int processTotalCount;

	/**
	 * 動作状態
	 */
	private int stateBehavior;

	private Long version;

	public static ExAcOpManageDto fromDomain(ExAcOpManage domain) {
		return new ExAcOpManageDto(domain.getCid(), domain.getProcessId(), domain.getErrorCount(),
				domain.getInterruption(), domain.getProcessCount(), domain.getProcessTotalCount(),
				domain.getStateBehavior(), domain.getVersion());
	}

}
