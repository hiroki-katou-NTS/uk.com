package nts.uk.ctx.at.request.app.find.application.workchange.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.workchange.output.WorkChangeCheckRegOutput;

@AllArgsConstructor
@NoArgsConstructor
public class WorkChangeCheckRegisterDto {
	/**
	 * 確認メッセージリスト
	 */
	public List<ConfirmMsgOutput> confirmMsgLst;
	
	/**
	 * 休日の申請日<List>
	 */
	public List<String> holidayDateLst;
	
	public static WorkChangeCheckRegisterDto fromDomain(WorkChangeCheckRegOutput workChangeCheckRegOutput) {
		WorkChangeCheckRegisterDto result = new WorkChangeCheckRegisterDto();
		result.confirmMsgLst = workChangeCheckRegOutput.getConfirmMsgLst();
		result.holidayDateLst = CollectionUtil.isEmpty(workChangeCheckRegOutput.getHolidayDateLst()) ? null : workChangeCheckRegOutput.getHolidayDateLst().stream().map(x -> x.toString("yyyy/MM/dd")).collect(Collectors.toList());
		return result;
	}
}
