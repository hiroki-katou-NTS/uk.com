package nts.uk.ctx.at.function.dom.annualworkschedule;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.AnnualWorkSheetPrintingForm;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.MonthsInTotalDisplay;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.TotalAverageDisplay;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScCode;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScName;
import nts.uk.ctx.at.function.dom.employmentfunction.commonform.SettingClassification;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.年間勤務表.年間勤務表の出力項目設定
 *
 * @author LienPTK
 *
 */
@Getter
public class SettingOutputItemOfAnnualWorkSchedule extends AggregateRoot {
	
	/** 項目設定ID. */
	@Setter
	private String layoutId;
	
	/** 会社ID. */
	private CompanyId cid;
	
	/** 社員ID. */
	private Optional<EmployeeId> sid;

	/** コード. */
	@Setter
	private OutItemsWoScCode cd;
	
	/** 名称. */
	@Setter
	private OutItemsWoScName name;
	
	/**  出力する項目一覧. */
	private List<ItemsOutputToBookTable> listItemsOutput;
	
	/** 印刷形式 */
	private AnnualWorkSheetPrintingForm printForm;
	
	/** 項目選択種類. */
	private SettingClassification settingType;
	
	/** 36協定時間を超過した月数を出力する. */
	private boolean outNumExceedTime36Agr;

	/** 複数月表示 */
	private boolean multiMonthDisplay;

	/** 合計表示の月数 */
	private Optional<MonthsInTotalDisplay> monthsInTotalDisplay;
	
	/** 合計平均表示 */
	private Optional<TotalAverageDisplay> totalAverageDisplay;

	private SettingOutputItemOfAnnualWorkSchedule() {}

	public static SettingOutputItemOfAnnualWorkSchedule createFromMemento(MementoGetter memento) {
		SettingOutputItemOfAnnualWorkSchedule domain = new SettingOutputItemOfAnnualWorkSchedule();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.layoutId = memento.getLayoutId();
		this.cid = new CompanyId(memento.getCid());
		this.sid = memento.getSid() != null ? Optional.of(new EmployeeId(memento.getSid())) : Optional.empty();
		this.cd = new OutItemsWoScCode(memento.getCd());
		this.name = new OutItemsWoScName(memento.getName());
		this.listItemsOutput = memento.getListItemsOutput();
		this.printForm = EnumAdaptor.valueOf(memento.getPrintForm(), AnnualWorkSheetPrintingForm.class);
		this.settingType = EnumAdaptor.valueOf(memento.getSettingType(), SettingClassification.class);
		this.outNumExceedTime36Agr = memento.isOutNumExceedTime36Agr();
		this.multiMonthDisplay = memento.isMultiMonthDisplay();
		this.monthsInTotalDisplay = memento.getMonthsInTotalDisplay() != null
								  ? Optional.of(EnumAdaptor.valueOf(memento.getMonthsInTotalDisplay(), MonthsInTotalDisplay.class))
								  : Optional.empty();
		this.totalAverageDisplay = memento.getTotalAverageDisplay() != null
								  ? Optional.of(EnumAdaptor.valueOf(memento.getTotalAverageDisplay(), TotalAverageDisplay.class))
								  : Optional.empty();
	}

	public void setMemento(MementoSetter memento) {
		memento.setLayoutId(this.layoutId);
		memento.setCid(AppContexts.user().companyId());
		memento.setCd(this.cd.v());
		memento.setName(this.name.v());
		memento.setListItemsOutput(this.listItemsOutput);
		memento.setSid(this.sid.map(EmployeeId::v).orElse(null));
		memento.setPrintForm(this.printForm.value);
		memento.setSettingType(this.settingType.value);
		memento.setOutNumExceedTime36Agr(this.outNumExceedTime36Agr);
		memento.setMultiMonthDisplay(this.multiMonthDisplay);
		memento.setMonthsInTotalDisplay(this.monthsInTotalDisplay.map(t -> t.value).orElse(null));
		memento.setTotalAverageDisplay(this.totalAverageDisplay.map(t -> t.value).orElse(null));
	}

	@Override
	public void validate() {
		super.validate();
		// 出力する項目一覧は1件以上設定しなければならない(List output item phải >=1 ) #Msg_880
		// 印刷形式 = "勤怠チェックリスト" の場合、並び順 ≧ 3 の「帳票に出力する項目」が設定されている必要があります。#Msg_880
		if (this.listItemsOutput == null || this.listItemsOutput.isEmpty()
				|| (this.printForm.equals(AnnualWorkSheetPrintingForm.TIME_CHECK_LIST) && this.listItemsOutput.size() < 3)) {
			throw new BusinessException("Msg_880");
		}
	}
	
	public static interface MementoSetter {
		void setCid(String cid);
		void setCd(String cd);
		void setName(String name);
		void setListItemsOutput(List<ItemsOutputToBookTable> listItemsOutput);
		void setSid(String sid);
		void setLayoutId(String layoutId);
		void setPrintForm(int printForm);
		void setSettingType(int settingType);
		void setOutNumExceedTime36Agr(boolean outNumExceedTime36Agr);
		void setMultiMonthDisplay(boolean multiMonthDisplay);
		void setMonthsInTotalDisplay(Integer monthsInTotalDisplay);
		void setTotalAverageDisplay(Integer totalAverageDisplay);
	}

	public static interface MementoGetter {
		String getCid();
		String getCd();
		String getName();
		List<ItemsOutputToBookTable> getListItemsOutput();
		String getSid();
		String getLayoutId();
		int getPrintForm();
		int getSettingType();
		boolean isOutNumExceedTime36Agr();
		boolean isMultiMonthDisplay();
		Integer getMonthsInTotalDisplay();
		Integer getTotalAverageDisplay();
	}
}
