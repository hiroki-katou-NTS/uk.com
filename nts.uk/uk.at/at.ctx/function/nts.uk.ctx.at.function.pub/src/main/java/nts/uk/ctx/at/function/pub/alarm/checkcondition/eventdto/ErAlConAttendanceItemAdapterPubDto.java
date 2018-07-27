package nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErAlConAttendanceItemAdapterPubDto {
	private String atdItemConGroupId;

	// 条件間の演算子
	private int conditionOperator;

	// 複合エラーアラーム条件 ErAlAtdItemConditionPubExport
	private List<ErAlAtdItemConAdapterPubDto> lstErAlAtdItemCon;

	public ErAlConAttendanceItemAdapterPubDto(String atdItemConGroupId, int conditionOperator,
			List<ErAlAtdItemConAdapterPubDto> lstErAlAtdItemCon) {
		super();
		this.atdItemConGroupId = atdItemConGroupId;
		this.conditionOperator = conditionOperator;
		this.lstErAlAtdItemCon = lstErAlAtdItemCon;
	}
	
}
