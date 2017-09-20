package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author hieult
 *
 */
@Value
public class ScreenLateOrLeaveEarlyDto {

	private LateOrLeaveEarlyDto lateOrLeaveEarlyDto;
	
	/** 定型理由 typicalReason :DB reasonTemp */ 
	private List<ApplicationReasonDto> ListApplicationReasonDto;

}