package nts.uk.ctx.at.record.dom.workrecord.actualsituation.param;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalDayComplete {
	//承認が済んでいる
	private boolean approved;
	//未承認の日一覧
	private List<GeneralDate> date;
}
