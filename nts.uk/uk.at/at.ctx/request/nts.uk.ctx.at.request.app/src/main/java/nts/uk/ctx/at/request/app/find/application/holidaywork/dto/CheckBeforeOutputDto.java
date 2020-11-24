package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.AppOvertimeDetailDto;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckBeforeOutputDto {
	
	//	確認メッセージリスト
	private List<ConfirmMsgOutput> confirmMsgOutputs;
	
	//	時間外時間の詳細
	private AppOvertimeDetailDto appOverTimeDetail;
	
	public static CheckBeforeOutputDto fromDomain() {
		//CheckBeforeOutput domain
		//huytodo 
		return null;
	}
}
