/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class FlowOTTimezone.
 */
// 流動残業時間帯
@Getter
@Setter
@NoArgsConstructor
public class FlowOTTimezone extends WorkTimeDomainObject implements Cloneable{

	/** The worktime no. */
	// 就業時間帯NO
	private Integer worktimeNo;

	/** The restrict time. */
	// 拘束時間として扱う
	private boolean restrictTime;

	/** The OT frame no. */
	// 残業枠NO
	private OvertimeWorkFrameNo oTFrameNo;

	/** The flow time setting. */
	// 流動時間設定
	private FlowTimeSetting flowTimeSetting;

	/** The in legal OT frame no. */
	// 法定内残業枠NO
	private OvertimeWorkFrameNo inLegalOTFrameNo;

	/** The settlement order. */
	// 精算順序
	private SettlementOrder settlementOrder;

	/**
	 * Instantiates a new flow OT timezone.
	 *
	 * @param memento
	 *            the memento
	 */
	public FlowOTTimezone(FlowOTTimezoneGetMemento memento) {
		this.worktimeNo = memento.getWorktimeNo();
		this.restrictTime = memento.getRestrictTime();
		this.oTFrameNo = memento.getOTFrameNo();
		this.flowTimeSetting = memento.getFlowTimeSetting();
		if (memento.getInLegalOTFrameNo() == null || memento.getInLegalOTFrameNo().v() == null) {
			this.inLegalOTFrameNo = OvertimeWorkFrameNo.getDefaultData();
		} else {
			this.inLegalOTFrameNo = memento.getInLegalOTFrameNo();
		}
		if (memento.getSettlementOrder() == null || memento.getSettlementOrder().v() == null) {
			this.settlementOrder = SettlementOrder.getDefaultData();
		} else {
			this.settlementOrder = memento.getSettlementOrder();
		}
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FlowOTTimezoneSetMemento memento) {
		memento.setWorktimeNo(this.worktimeNo);
		memento.setRestrictTime(this.restrictTime);
		memento.setOTFrameNo(this.oTFrameNo);
		memento.setFlowTimeSetting(this.flowTimeSetting);
		memento.setInLegalOTFrameNo(this.inLegalOTFrameNo);
		memento.setSettlementOrder(this.settlementOrder);
	}

	/**
	 * Correct default data.
	 */
	public void correctDefaultData() {
		this.settlementOrder = null;
		this.inLegalOTFrameNo = null;
	}
	
	@Override
	public FlowOTTimezone clone() {
		FlowOTTimezone cloned = new FlowOTTimezone();
		try {
			cloned.worktimeNo = this.worktimeNo;
			cloned.restrictTime = this.restrictTime ? true : false;
			cloned.oTFrameNo = new OvertimeWorkFrameNo(this.oTFrameNo.v());
			cloned.flowTimeSetting = this.flowTimeSetting.clone();
			cloned.inLegalOTFrameNo = new OvertimeWorkFrameNo(this.inLegalOTFrameNo.v());
			cloned.settlementOrder = new SettlementOrder(this.settlementOrder.v());
		}
		catch (Exception e){
			throw new RuntimeException("FlowOTTimezone clone error.");
		}
		return cloned;
	}
}
