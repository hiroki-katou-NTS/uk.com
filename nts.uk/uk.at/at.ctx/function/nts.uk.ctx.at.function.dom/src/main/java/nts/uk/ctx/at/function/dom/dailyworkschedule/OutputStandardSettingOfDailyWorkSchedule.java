package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.List;

import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.日別勤務表.日別勤務表の出力項目定型設定
 * @author LienPTK
 */
public class OutputStandardSettingOfDailyWorkSchedule extends AggregateRoot {

	/**
	 *	項目選択種類
	 */
	private ItemSelectionType selection;

	/**
	 *	会社ID
	 */
	private CompanyId companyId;

	/**
	 *	出力項目
	 */
	private List<OutputItemDailyWorkSchedule> outputItems;

	private OutputStandardSettingOfDailyWorkSchedule() {}

	public static OutputStandardSettingOfDailyWorkSchedule createFromMemento(MementoGetter memento) {
		OutputStandardSettingOfDailyWorkSchedule domain = new OutputStandardSettingOfDailyWorkSchedule();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.companyId = new CompanyId(memento.getCid());
		this.selection = ItemSelectionType.valueOf(memento.getSelectionSetting());
		this.outputItems = memento.getOutputItemDailyWorkSchedule();
	}

	public void setMemento(MementoSetter memento) {
		memento.setCid(this.companyId.v());
		if (this.selection != null) {
			memento.setSelectionSetting(this.selection.value);
		}
		memento.setOutputItemDailyWorkSchedule(this.outputItems);
	}

	public static interface MementoSetter {
		void setCid(String companyId);
		void setSelectionSetting(int itemSelection);
		void setOutputItemDailyWorkSchedule(List<OutputItemDailyWorkSchedule> outputItem);
	}

	public static interface MementoGetter {
		String getCid();
		int getSelectionSetting();
		List<OutputItemDailyWorkSchedule> getOutputItemDailyWorkSchedule();
	}

}
