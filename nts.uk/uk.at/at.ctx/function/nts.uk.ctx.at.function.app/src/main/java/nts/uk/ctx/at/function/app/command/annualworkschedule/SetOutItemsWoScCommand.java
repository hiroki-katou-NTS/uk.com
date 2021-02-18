package nts.uk.ctx.at.function.app.command.annualworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemsOutputToBookTable;
import nts.uk.ctx.at.function.dom.annualworkschedule.SettingOutputItemOfAnnualWorkSchedule;

@Data
public class SetOutItemsWoScCommand implements SettingOutputItemOfAnnualWorkSchedule.MementoGetter {

	/** 項目設定ID. */
	private String layoutId;
	
	/** 会社ID. */
	private String cid;
	
	/** 社員ID. */
	private String sid;

	/** コード. */
	private String cd;
	
	/** 名称. */
	private String name;
	
	/**  出力する項目一覧. */
	private List<ItemsOutputToBookTableCommand> listItemsOutput;
	
	/** 印刷形式 */
	private int printForm;
	
	/** 項目選択種類. */
	private int settingType;
	
	/** 36協定時間を超過した月数を出力する. */
	private boolean outNumExceedTime36Agr;

	/** 複数月表示 */
	private boolean multiMonthDisplay;

	/** 合計表示の月数 */
	private Integer monthsInTotalDisplay;
	
	/** 合計平均表示 */
	private Integer totalAverageDisplay;
	
	@Getter
	private boolean newMode;

	public List<ItemsOutputToBookTable> getListItemsOutput() {
		return this.listItemsOutput.stream()
				.map(t -> ItemsOutputToBookTable.createFromMemento(t))
				.collect(Collectors.toList());
	}
}
