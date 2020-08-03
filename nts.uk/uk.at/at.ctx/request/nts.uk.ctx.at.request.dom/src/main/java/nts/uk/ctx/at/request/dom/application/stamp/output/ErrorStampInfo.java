package nts.uk.ctx.at.request.dom.application.stamp.output;
/**
 * Refactor4
 * @author hoangnd
 *
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.stamp.StampAtrOther;
import nts.uk.ctx.at.request.dom.application.stamp.StartEndClassification;
@AllArgsConstructor
@NoArgsConstructor
@Data
//打刻エラー情報
public class ErrorStampInfo {
//	打刻分類
	private StampAtrOther timeStampAppEnum;
//	打刻枠No
	private Integer stampFrameNo;
//	開始終了区分
	private StartEndClassification startEndClassification;
}
