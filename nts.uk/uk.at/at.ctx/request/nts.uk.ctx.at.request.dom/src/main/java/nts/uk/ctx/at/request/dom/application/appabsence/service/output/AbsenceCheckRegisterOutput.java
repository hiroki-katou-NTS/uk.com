package nts.uk.ctx.at.request.dom.application.appabsence.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbsenceCheckRegisterOutput {
	
	/**
	 * 確認メッセージリスト
	 */
	private List<ConfirmMsgOutput> confirmMsgLst;
	
	/**
	 * 休日の申請日<List>
	 */
	private List<GeneralDate> holidayDateLst;
	
	/**
	 * 申請する勤務種類が休日か
	 */
	private boolean holidayFlg;
	
}
