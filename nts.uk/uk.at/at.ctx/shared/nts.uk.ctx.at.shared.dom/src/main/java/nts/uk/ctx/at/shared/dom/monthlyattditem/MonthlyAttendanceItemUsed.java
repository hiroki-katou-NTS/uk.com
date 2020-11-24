package nts.uk.ctx.at.shared.dom.monthlyattditem;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤怠項目.月次の勤怠項目.月次の勤怠項目が利用できる帳票
 * 
 * @author DungDV
 */
@Getter
public class MonthlyAttendanceItemUsed extends AggregateRoot {
	/**
	 * 	 会社ID
	 */
	private CompanyId companyId;

	/**
	 *	 利用できる帳票
	 */
	private List<FormCanUsedForTime> formCanUsedForTimes;

	/**
	 *	 勤怠項目ID
	 */
	private int attendanceItemId;
	
	private MonthlyAttendanceItemUsed() {}
	
	/**
	 * Creates the from getter memento.
	 *
	 * @param memento the getter memento
	 * @return the daily attendance item used
	 */
	public static MonthlyAttendanceItemUsed createFromMemento(MementoGetter memento) {
		MonthlyAttendanceItemUsed domain = new MonthlyAttendanceItemUsed();
		domain.getMemento(memento);
		return domain;
	}

	/**
	 * Gets the memento.
	 *
	 * @param memento the memento
	 * @return the memento
	 */
	public void getMemento(MementoGetter memento) {
		this.companyId = new CompanyId(memento.getCompanyId());
		this.attendanceItemId = memento.getAttendanceItemId();
		this.formCanUsedForTimes = memento.getFormCanUsedForTimes();
	}

	/**
	 * Sets the memento.
	 *
	 * @param memento the new memento
	 */
	public void setMemento(MementoSetter memento) {
		memento.setCompanyId(this.companyId.v());
		memento.setAttendanceItemId(this.attendanceItemId);
		memento.setFormCanUsedForTimes(this.formCanUsedForTimes);
	}

	public static interface MementoSetter {
		void setCompanyId(String companyId);
		void setAttendanceItemId(int itemDailyId);
		void setFormCanUsedForTimes(List<FormCanUsedForTime> form);
	}

	public static interface MementoGetter {
		String getCompanyId();
		int getAttendanceItemId();
		List<FormCanUsedForTime> getFormCanUsedForTimes();
	}
}