package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AbsenceCheckRegisterOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;

@AllArgsConstructor
@NoArgsConstructor
public class AbsenceCheckRegisterDto {
	/**
	 * 確認メッセージリスト
	 */
	public List<ConfirmMsgOutput> confirmMsgLst;
	
	/**
	 * 休日の申請日<List>
	 */
	public List<String> holidayDateLst;
	
	/**
	 * 申請する勤務種類が休日か
	 */
	public boolean holidayFlg;
	
	public static AbsenceCheckRegisterDto fromDomain(AbsenceCheckRegisterOutput absenceCheckRegisterOutput) {
		AbsenceCheckRegisterDto result = new AbsenceCheckRegisterDto();
		result.confirmMsgLst = absenceCheckRegisterOutput.getConfirmMsgLst();
		result.holidayDateLst = absenceCheckRegisterOutput.getHolidayDateLst().stream().map(x -> x.toString("yyyy/MM/dd")).collect(Collectors.toList());
		result.holidayFlg = absenceCheckRegisterOutput.isHolidayFlg();
		return result;
	}
}
