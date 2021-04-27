package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 申請中の勤務情報
public class WorkInfo {
	// 勤務種類
	private String workType;
	// 就業時間帯
	private String workTime;
}
