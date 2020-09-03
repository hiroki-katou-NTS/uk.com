package nts.uk.ctx.at.function.dom.dailyattendanceitem;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤怠項目.日次の勤怠項目.日次の勤怠項目が利用できる帳票
 * 
 * @author LienPTK
 */
@Getter
public class DailyAttendanceItemUsed extends AggregateRoot {
	// 会社ID
	private CompanyId companyId;

	// 勤怠項目ID
	private ItemDailyId itemDailyId;

	// 利用できる帳票
	private List<FormCanUsedForTime> formCanUsedForTimes;

	private DailyAttendanceItemUsed() {}

	/**
	 * Creates the from getter memento.
	 *
	 * @param memento the getter memento
	 * @return the daily attendance item used
	 */
	public static DailyAttendanceItemUsed createFromMemento(MementoGetter memento) {
		DailyAttendanceItemUsed domain = new DailyAttendanceItemUsed();
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
		this.itemDailyId = new ItemDailyId(memento.getItemDailyId());
		this.formCanUsedForTimes = memento.getFormCanUsedForTimes();
	}

	/**
	 * Sets the memento.
	 *
	 * @param memento the new memento
	 */
	public void setMemento(MementoSetter memento) {
		memento.setCompanyId(this.companyId.v());
		memento.setItemDailyId(this.itemDailyId.v());
		memento.setFormCanUsedForTimes(this.formCanUsedForTimes);
	}

	public static interface MementoSetter {
		void setCompanyId(String companyId);
		void setItemDailyId(BigDecimal itemDailyId);
		void setFormCanUsedForTimes(List<FormCanUsedForTime> form);
	}

	public static interface MementoGetter {
		String getCompanyId();
		BigDecimal getItemDailyId();
		List<FormCanUsedForTime> getFormCanUsedForTimes();
	}

	
}
