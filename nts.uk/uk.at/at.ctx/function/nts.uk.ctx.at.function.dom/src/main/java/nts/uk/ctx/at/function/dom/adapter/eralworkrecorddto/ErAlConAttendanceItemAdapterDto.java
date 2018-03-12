package nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErAlConAttendanceItemAdapterDto {
	private String atdItemConGroupId;

	// 条件間の演算子
	private int conditionOperator;

	// 複合エラーアラーム条件 ErAlAtdItemConditionPubExport
	private List<ErAlAtdItemConAdapterDto> lstErAlAtdItemCon;

	public ErAlConAttendanceItemAdapterDto(String atdItemConGroupId, int conditionOperator,
			List<ErAlAtdItemConAdapterDto> lstErAlAtdItemCon) {
		super();
		this.atdItemConGroupId = atdItemConGroupId;
		this.conditionOperator = conditionOperator;
		this.lstErAlAtdItemCon = lstErAlAtdItemCon;
	}
	
}
