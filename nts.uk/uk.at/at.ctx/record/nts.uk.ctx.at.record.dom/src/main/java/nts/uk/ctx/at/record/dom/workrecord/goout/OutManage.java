/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.goout;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;

/**
 * @author Hoangdd
 * 外出管理
 */
@Getter
public class OutManage extends AggregateRoot{
	/** The company ID. */
	// 会社ID
	private String companyID;
	
	/** The max usage. */
	// 最大使用回数
	private MaxGoOut maxUsage;
	
	/** The init value reason go out. */
	// 外出理由の初期値
	private GoingOutReason initValueReasonGoOut;
	
	
	/**
	 * Instantiates a new out manage.
	 *
	 * @param memento the memento
	 */
	public OutManage(OutManageGetMemento memento) {
		this.companyID = memento.getCompanyID();
		this.maxUsage = memento.getMaxUsage();
		this.initValueReasonGoOut = memento.getInitValueReasonGoOut();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OutManageSetMemento memento) {
		memento.setCompanyID(this.companyID);
		memento.setMaxUsage(this.maxUsage.v());
		memento.setInitValueReasonGoOut(this.initValueReasonGoOut.value);
	}
}

