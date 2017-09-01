package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output;

import java.util.List;

import lombok.Value;

@Value
public class DecideAgencyExpiredOutput {
			List<String>  outputApprover; 
			boolean outputAlternateExpiration;
			
}
