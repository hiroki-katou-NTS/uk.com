package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import java.util.List;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

/**
 * @author thanhpv
 * 振休申請起動時の表示情報
 */
@Getter
@Setter
@NoArgsConstructor
public class DisplayInformationApplication {

	//勤務種類リスト
	private List<WorkTypeDto> workTypeList;
	//初期選択勤務種類
	private String workTypeCode;
	//初期選択就業時間帯
	private String workTimeCode;
	//開始時刻
	private Integer startTime;
	//終了時刻
	private Integer endTime;
	//開始時刻2
	private Integer startTime2;
	//終了時刻2
	private Integer endTime2;
}


