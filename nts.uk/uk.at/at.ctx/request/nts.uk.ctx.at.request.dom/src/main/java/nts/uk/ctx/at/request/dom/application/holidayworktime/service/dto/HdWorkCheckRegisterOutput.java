package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;

@NoArgsConstructor
@Getter
@Setter
public class HdWorkCheckRegisterOutput {
	
	/**
	 * 確認メッセージリスト
	 */
	private List<ConfirmMsgOutput> confirmMsgLst;
	
	private Optional<AppOvertimeDetail> appOvertimeDetailOtp;
}
