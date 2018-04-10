package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.Getter;

/**
 * 再作成対象者詳細区分
 * 
 * @author sonnh1
 *
 */
@Getter
public class RebuildTargetDetailsAtr {
	// 異動者を再作成
	private Boolean recreateConverter;
	// 休職休業者を再作成
	private Boolean recreateEmployeeOffWork;
	// 直行直帰者を再作成
	private Boolean recreateDirectBouncer;
	// 短時間勤務者を再作成
	private Boolean recreateShortTermEmployee;
	// 勤務種別変更者を再作成
	private Boolean recreateWorkTypeChange;
	// 手修正を保護
	private Boolean protectHandCorrection;

	/**
	 * To domain.
	 *
	 * @param memento
	 *            the memento
	 * @return the Rebuild Target Details Atr
	 */
	public RebuildTargetDetailsAtr(ScheduleCreateContentGetMemento memento) {
		this.recreateConverter = memento.getRecreateConverter();
		this.recreateEmployeeOffWork = memento.getRecreateEmployeeOffWork();
		this.recreateDirectBouncer = memento.getRecreateDirectBouncer();
		this.recreateShortTermEmployee = memento.getRecreateShortTermEmployee();
		this.recreateWorkTypeChange = memento.getRecreateWorkTypeChange();
		this.protectHandCorrection = memento.getProtectHandCorrection();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ScheduleCreateContentSetMemento memento) {
		memento.setRecreateConverter(this.recreateConverter);
		memento.setRecreateEmployeeOffWork(recreateEmployeeOffWork);
		memento.setRecreateDirectBouncer(this.recreateDirectBouncer);
		memento.setRecreateShortTermEmployee(this.recreateShortTermEmployee);
		memento.setRecreateWorkTypeChange(this.recreateWorkTypeChange);
		memento.setProtectHandCorrection(this.protectHandCorrection);
	}
}
