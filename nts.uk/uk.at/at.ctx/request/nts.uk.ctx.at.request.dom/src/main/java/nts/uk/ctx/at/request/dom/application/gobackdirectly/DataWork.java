package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

//勤務情報
@Data
@AllArgsConstructor
public class DataWork {
//	勤務種類コード
	private InforWorkType workType;
//	就業時間帯コード
	private Optional<InforWorkTime> workTime;
}
