package nts.uk.ctx.exio.dom.input.manage;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 外部受入の実行状態
 */
@RequiredArgsConstructor
public enum ExecutionState {

	/** 待機 */
	IDLE(0),
	
	/** 準備中 */
	ON_PREPARE(1),
	
	/** 準備完了 */
	PREPARED(2),
	
	/** 実行中 */
	ON_EXECUTE(3),
	
	;
	
	public final int value;
	
	public static ExecutionState valueOf(int value) {
		return EnumAdaptor.valueOf(value, ExecutionState.class);
	}
	
	public void checkIfCanPrepare() throws ExternalImportStateException {
		
		if (this == ON_PREPARE || this == ON_EXECUTE) {
			throw new ExternalImportStateException("他の受入処理を実行中です。処理が完了するまでお待ちください。");
		}
	}
	
	public void checkIfCanExecute() throws ExternalImportStateException {
		
		if (this == ON_PREPARE || this == ON_EXECUTE) {
			throw new ExternalImportStateException("他の受入処理を実行中です。処理が完了するまでお待ちください。");
		}
		
		if (this == IDLE) {
			throw new ExternalImportStateException("受入の準備処理が未実行です。先に準備処理を実行してください。");
		}
	}
}
