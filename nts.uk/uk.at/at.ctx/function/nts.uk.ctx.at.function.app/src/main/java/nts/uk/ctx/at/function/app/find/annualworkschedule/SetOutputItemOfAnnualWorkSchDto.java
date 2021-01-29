package nts.uk.ctx.at.function.app.find.annualworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemsOutputToBookTable;
import nts.uk.ctx.at.function.dom.annualworkschedule.SettingOutputItemOfAnnualWorkSchedule;

@Data
@NoArgsConstructor
public class SetOutputItemOfAnnualWorkSchDto implements SettingOutputItemOfAnnualWorkSchedule.MementoSetter {

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
	private List<ItemsOutputToBookTableDto> listItemsOutput;
	
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

	@Override
	public void setListItemsOutput(List<ItemsOutputToBookTable> listItemsOutput) {
		this.listItemsOutput = listItemsOutput.stream().map(t -> {
			ItemsOutputToBookTableDto dto = new ItemsOutputToBookTableDto();
			t.setMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * Convert domain to dto 
	 *
	 * @param domain the domain
	 * @return the sets the output item of annual work sch dto
	 */
	public static SetOutputItemOfAnnualWorkSchDto toDto(SettingOutputItemOfAnnualWorkSchedule domain) {
		SetOutputItemOfAnnualWorkSchDto dto = new SetOutputItemOfAnnualWorkSchDto();
		domain.setMemento(dto);
		return dto;
	}
}
