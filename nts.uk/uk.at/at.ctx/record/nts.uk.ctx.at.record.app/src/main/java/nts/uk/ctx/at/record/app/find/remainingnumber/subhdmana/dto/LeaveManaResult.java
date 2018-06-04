package nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.dto;

import java.util.List;

import lombok.Data;

@Data
public class LeaveManaResult {
	
	private List<LeaveManaDto> listLeaveMana;
	private String errorCode;
	
}
