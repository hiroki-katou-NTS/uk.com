package nts.uk.ctx.at.request.app.find.application.requestofearch;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCommon;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class RequestOfEachCommonDto {
	@Setter
	@Getter
	List<RequestAppDetailSettingDto> requestOfEachCommonDtos;

	public static RequestOfEachCommonDto convertToDto(RequestOfEachCommon domain) {
		if(domain==null) return null;
		return new RequestOfEachCommonDto(
				domain.getRequestAppDetailSettings().stream().map(x -> RequestAppDetailSettingDto.convertToDto(x)).collect(Collectors.toList()));
	}
}
