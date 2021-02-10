/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;

/**
 * The Class LogoutData.
 */
@Getter
@AllArgsConstructor
//ロックアウトデータ
public class LockoutData extends AggregateRoot{
	
	/** The テナントコード */
	private ContractCode contractCode;

	/** ユーザID */
	private String userId;
	
	/** ロックアウト日時. */
	private GeneralDateTime lockOutDateTime;
	
	/** The ロック種別 */
	private LockType logType;
	
    /**
     * Instantiates a new logout data.
     *
     * @param memento the memento
     */
    public LockoutData(LockOutDataGetMemento memento) {
        this.userId = memento.getUserId();
        this.lockOutDateTime = memento.getLockOutDateTime();
        this.logType = memento.getLogType();
        this.contractCode = memento.getContractCode();
    }

	/**
	 * 自動ロックをかける
	 * @param userId
	 * @param contractCode
	 * @return
	 */
	public static AtomTask autoLock(Require require, ContractCode contractCode, String userId) {
		
		return AtomTask.of(() -> {
			require.addLockoutData(new LockoutData(contractCode, userId, GeneralDateTime.now(), LockType.AUTO_LOCK));
		});
	}

	public static interface Require {
		void addLockoutData(LockoutData lockoutData);
	}
	

}
