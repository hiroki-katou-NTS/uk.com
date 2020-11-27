package nts.uk.ctx.at.request.dom.application.appabsence.service;

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
public class AbsenceCheckOutput {
	/*
	 * 確認メッセージリスト
	 */
	private List<ConfirmMsgOutput> lstConfirm;
	/*
	 * 休日の申請日<List>
	 */
	private List<GeneralDate> lstAppDate;
}
