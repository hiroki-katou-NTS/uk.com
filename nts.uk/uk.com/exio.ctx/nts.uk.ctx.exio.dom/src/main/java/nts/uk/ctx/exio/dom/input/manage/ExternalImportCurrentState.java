package nts.uk.ctx.exio.dom.input.manage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 外部受入の現在の状態
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Slf4j
public class ExternalImportCurrentState implements DomainAggregate {

	/** 会社ID */
	private final String companyId;
	
	/** 実行状態 */
	private ExecutionState executionState;
	
	public static ExternalImportCurrentState createNew(String companyId) {
		return new ExternalImportCurrentState(companyId, ExecutionState.IDLE);
	}
	
	/**
	 * 「受入処理の準備」の状態を制御する
	 * @param require
	 * @param prepare
	 */
	public void prepare(Require require, Runnable prepare) {
		
		try {
			preparing(require);
			prepare.run();
			prepared(require);
		} catch (BusinessException ex) {
			abortedByBusinessError(require);
		} catch (Exception ex) {
			log.error("受入処理の準備に失敗: " + companyId, ex);
			abortedBySystemError(require);
			throw ex;
		}
	}
	
	/**
	 * 「受入処理の実行」の状態を制御する
	 * @param require
	 * @param prepare
	 */
	public void execute(Require require, Runnable execute) {
		
		try {
			executing(require);
			execute.run();
			executed(require);
		} catch (BusinessException ex) {
			abortedByBusinessError(require);
		} catch (Exception ex) {
			log.error("受入処理の実行に失敗: " + companyId, ex);
			abortedBySystemError(require);
			throw ex;
		}
	}
	
	public static interface Require {
		
		void update(ExternalImportCurrentState currentState);
		
	}
	
	private void preparing(Require require) {
		executionState.checkIfCanPrepare();
		changeState(require, ExecutionState.ON_PREPARE);
	}
	
	private void prepared(Require require) {
		changeState(require, ExecutionState.PREPARED);
	}
	
	private void executing(Require require) {
		executionState.checkIfCanExecute();
		changeState(require, ExecutionState.ON_EXECUTE);
	}
	
	private void executed(Require require) {
		changeState(require, ExecutionState.IDLE);
	}
	
	private void abortedByBusinessError(Require require) {
		changeState(require, ExecutionState.IDLE);
	}
	
	private void abortedBySystemError(Require require) {
		changeState(require, ExecutionState.IDLE);
	}
	
	private void changeState(Require require, ExecutionState nextState) {
		executionState = nextState;
		require.update(this);
	}
}
