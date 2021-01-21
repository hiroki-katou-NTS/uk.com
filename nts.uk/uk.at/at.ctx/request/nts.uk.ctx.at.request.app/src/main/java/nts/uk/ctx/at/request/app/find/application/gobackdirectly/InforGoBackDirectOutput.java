package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Data
public class InforGoBackDirectOutput {
//	勤務種類初期選択
	private InforWorkType workType;
//	就業時間帯初期選択
	private InforWorkTime workTime;
//	勤務種類リスト
	private List<WorkType> lstWorkType;
	 
}
