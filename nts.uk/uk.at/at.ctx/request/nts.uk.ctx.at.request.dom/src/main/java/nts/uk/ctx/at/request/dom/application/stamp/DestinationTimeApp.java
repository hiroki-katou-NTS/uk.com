package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;

/**
 * 
 * @author hoangnd
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
//時刻申請の反映先情報
public class DestinationTimeApp {
//	打刻分類
	private TimeStampAppEnum timeStampAppEnum;
//	打刻枠No
	private Integer engraveFrameNo;
//	開始終了区分
	private StartEndClassification startEndClassification;
//	応援勤務NO
	private Optional<WorkNo> supportWorkNo; 
}
