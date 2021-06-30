package nts.uk.ctx.at.function.dom.annualworkschedule;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.ValueOuputFormat;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.FormOutputItemName;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.ItemOutTblBookCode;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.年間勤務表.年間勤務表の月次表示項目
 * 
 * @author LienPTK
 *
 */
@Getter
public class ItemsOutputToBookTable extends DomainObject {
	
	/** コード. */
	private ItemOutTblBookCode itemOutCd;

	/** 並び順. */
	private int sortBy;

	/** 見出し名称. */
	private FormOutputItemName headingName;
	
	/** 使用区分 */
	private boolean useClass;

	/** 値の出力形式 */
	private ValueOuputFormat valOutFormat;

	/** 出力対象項目 */
	private List<CalculationFormulaOfItem> listOperationSetting;
	
	@Override
	public void validate() {
		super.validate();
		if (this.listOperationSetting == null || this.listOperationSetting.isEmpty()) {
			// 出力対象項目が設定されていなければならない(output item phải set)
			// #Msg_881
			throw new BusinessException("Msg_881");
		}
		// 勤怠項目の件数＜＝50でなければならない
		if (this.sortBy > 2 && this.listOperationSetting.size() > 50) {
			// #Msg_882
			throw new BusinessException("Msg_882");
		}
	}
	
	private ItemsOutputToBookTable() {}

	public static ItemsOutputToBookTable createFromMemento(MementoGetter memento) {
		ItemsOutputToBookTable domain = new ItemsOutputToBookTable();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.itemOutCd = new ItemOutTblBookCode(!StringUtil.isNullOrEmpty(memento.getItemOutCd(), true)
				? memento.getItemOutCd()
				: StringUtil.padLeft(String.valueOf(memento.getSortBy()), 2, '0'));
		this.sortBy = memento.getSortBy();
		this.headingName = new FormOutputItemName(memento.getHeadingName());
		this.useClass = memento.isUseClass();
		this.valOutFormat = EnumAdaptor.valueOf(memento.getValOutFormat(), ValueOuputFormat.class);
		this.listOperationSetting = memento.getListOperationSetting();
	}

	public void setMemento(MementoSetter memento) {
		memento.setItemOutCd(this.itemOutCd.v());
		memento.setSortBy(this.sortBy);
		memento.setHeadingName(this.headingName.v());
		memento.setUseClass(this.useClass);
		memento.setValOutFormat(this.valOutFormat.value);
		memento.setListOperationSetting(this.listOperationSetting);
	}

	public static interface MementoSetter {
		void setItemOutCd(String itemOutCd);
		void setSortBy(int sortBy);
		void setHeadingName(String headingName);
		void setUseClass(boolean useClass);
		void setValOutFormat(int valOutFormat);
		void setListOperationSetting(List<CalculationFormulaOfItem> listOperationSetting);
	}

	public static interface MementoGetter {
		String getItemOutCd();
		int getSortBy();
		String getHeadingName();
		boolean isUseClass();
		int getValOutFormat();
		List<CalculationFormulaOfItem> getListOperationSetting();
	}

}
