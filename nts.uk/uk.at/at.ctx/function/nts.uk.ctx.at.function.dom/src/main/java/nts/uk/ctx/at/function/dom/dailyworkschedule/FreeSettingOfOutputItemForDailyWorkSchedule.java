package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.日別勤務表.日別勤務表の出力項目自由設定
 * @author LienPTK
 */
@Getter
public class FreeSettingOfOutputItemForDailyWorkSchedule extends AggregateRoot {
	/**
	 *	項目選択種類
	 */
	private ItemSelectionType selection;

	/**
	 *	会社ID
	 */
	private CompanyId companyId;

	/**
	 * 	社員ID
	 */
	private EmployeeId employeeId;

	/**
	 *	出力項目
	 */
	private List<OutputItemDailyWorkSchedule> outputItemDailyWorkSchedules;
	

	private FreeSettingOfOutputItemForDailyWorkSchedule() {}

	public static FreeSettingOfOutputItemForDailyWorkSchedule createFromMemento(MementoGetter memento) {
		FreeSettingOfOutputItemForDailyWorkSchedule domain = new FreeSettingOfOutputItemForDailyWorkSchedule();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.companyId = new CompanyId(memento.getCompanyId());
		this.employeeId = new EmployeeId(memento.getEmployeeId());
		this.selection = ItemSelectionType.valueOf(memento.getSelection());
		this.outputItemDailyWorkSchedules = memento.getOutputItemDailyWorkSchedules();
	}

	public void setMemento(MementoSetter memento) {
		memento.setCompanyId(this.companyId.v());
		memento.setEmployeeId(this.employeeId.v());
		if (this.selection != null) {
			memento.setSelection(this.selection.value);
		}
		memento.setOutputItemDailyWorkSchedules(this.outputItemDailyWorkSchedules);
	}

	public static interface MementoSetter {
		void setCompanyId(String companyId);
		void setEmployeeId(String employeeId);
		void setSelection(int itemSelection);
		void setOutputItemDailyWorkSchedules(List<OutputItemDailyWorkSchedule> outputItem);
	}

	public static interface MementoGetter {
		String getCompanyId();
		String getEmployeeId();
		int getSelection();
		List<OutputItemDailyWorkSchedule> getOutputItemDailyWorkSchedules();
	}

}
