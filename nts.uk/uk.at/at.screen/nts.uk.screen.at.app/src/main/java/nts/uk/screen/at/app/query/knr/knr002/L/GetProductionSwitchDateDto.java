package nts.uk.screen.at.app.query.knr.knr002.L;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetProductionSwitchDateDto {

	private String empInfoTerminalCode;
	
	private String switchDateTime;
	
	public static GetProductionSwitchDateDto toDto(TimeRecordReqSetting domain) {
		
		String switchDateTime = domain.getTimeSwitchUKMode().isPresent() ? domain.getTimeSwitchUKMode().get().toString() : "";
		
		return new GetProductionSwitchDateDto(domain.getTerminalCode().v(), switchDateTime);
	}
}
