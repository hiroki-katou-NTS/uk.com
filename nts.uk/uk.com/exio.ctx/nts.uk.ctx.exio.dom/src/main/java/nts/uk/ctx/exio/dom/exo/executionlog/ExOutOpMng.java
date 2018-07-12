package nts.uk.ctx.exio.dom.exo.executionlog;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 外部出力動作管理
 */

@Setter
@Getter
public class ExOutOpMng extends AggregateRoot {

	/**
	 * 外部出力処理ID
	 */
	private String exOutProId;

	/**
	 * 処理カウント
	 */
	private int proCnt;

	/**
	 * エラー件数
	 */
	private int errCnt;

	/**
	 * 処理トータルカウント
	 */
	private int totalProCnt;

	/**
	 * 中断するしない
	 */
	private NotUseAtr doNotInterrupt;

	/**
	 * 処理単位
	 */
	private String proUnit;

	/**
	 * 動作状態
	 */
	private ExIoOperationState opCond;

	public ExOutOpMng(String exOutProId, int proCnt, int errCnt, int totalProCnt, int doNotInterrupt, String proUnit,
			int opCond) {
		this.exOutProId = exOutProId;
		this.proCnt = proCnt;
		this.errCnt = errCnt;
		this.totalProCnt = totalProCnt;
		this.doNotInterrupt = EnumAdaptor.valueOf(doNotInterrupt, NotUseAtr.class);
		this.proUnit = proUnit;
		this.opCond = EnumAdaptor.valueOf(opCond, ExIoOperationState.class);
	}

}
