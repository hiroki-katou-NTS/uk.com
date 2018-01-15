package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputCommonData {
	
	ApplicationDto_New applicationDto;	
	String memo;
}
