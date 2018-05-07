package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunImport;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.ExtraResultMonthly;

@Getter
@Setter
@NoArgsConstructor
public class MonAlarmCheckConCommand {
	/**固定抽出条件*/
	private List<FixedExtraMonFunImport> listFixExtraMon = new ArrayList<>();
	/**任意抽出条件*/
	//List<ExtraResultMonthly> arbExtraCon = new ArrayList<>();

	public MonAlarmCheckConCommand(List<FixedExtraMonFunImport> listFixExtraMon) {
		super();
		this.listFixExtraMon = listFixExtraMon;
	}
	
}
