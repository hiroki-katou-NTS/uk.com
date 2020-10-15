package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Data
public class InforWorkGoBackDirectOutput {
//	勤務種類初期選択
	private String workType;
//	就業時間帯初期選択
	private String workTime;
//	勤務種類リスト
	private List<WorkType> lstWorkType;
	 
}
