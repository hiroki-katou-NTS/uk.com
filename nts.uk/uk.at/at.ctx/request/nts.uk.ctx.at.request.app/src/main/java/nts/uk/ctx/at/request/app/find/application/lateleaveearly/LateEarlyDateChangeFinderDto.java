package nts.uk.ctx.at.request.app.find.application.lateleaveearly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoWithDateDto;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateEarlyDateChangeOutput;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class LateEarlyDateChangeFinderDto {

	private AppDispInfoWithDateDto appDispInfoWithDateOutput;
	
	private String errorInfo;
	
	public static LateEarlyDateChangeFinderDto fromDomain(LateEarlyDateChangeOutput dto) {
		
		return new LateEarlyDateChangeFinderDto(AppDispInfoWithDateDto.fromDomain(dto.getAppDispInfoWithDateOutput()), 
				dto.getErrorInfo().isPresent() ? dto.getErrorInfo().get() : null);
	}
}
