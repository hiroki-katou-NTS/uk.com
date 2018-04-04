package nts.uk.ctx.at.record.dom.workrecord.temporarywork;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * The Class ManageWorkTemporary.
 *
 * @author hoangdd
 * 臨時勤務管理
 */
@Getter
public class ManageWorkTemporary extends AggregateRoot{

	/** The company ID. */
	// 会社ID
	private String companyID;
	
	/** The max usage. */
	// 最大使用回数
	private MaxUsage maxUsage;
	
	/** The time treat temporary same. */
	// 臨時打刻を同一と扱う時間
	private AttendanceTime timeTreatTemporarySame;
	
	/**
	 * Instantiates a new manage work temporary.
	 *
	 * @param memento the memento
	 */
	public ManageWorkTemporary(ManageWorkTemporaryGetMemento memento) {
		this.companyID = memento.getCompanyID();
		this.maxUsage = memento.getMaxUsage();
		this.timeTreatTemporarySame = memento.getTimeTreatTemporarySame();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ManageWorkTemporarySetMemento memento) {
		memento.setCompanyID(this.companyID);
		memento.setMaxUsage(this.maxUsage.v());
		memento.setTimeTreatTemporarySame(this.timeTreatTemporarySame.v());
	}
}

