package nts.uk.ctx.at.request.app.find.application.requestofearch;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEachCommon;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public class RequestOfEachCommonDto {
	List<RequestAppDetailSettingDto> requestOfEachCommonDtos;

	public static RequestOfEachCommonDto convertToDto(RequestOfEachCommon domain) {
		return new RequestOfEachCommonDto(
				domain.getRequestAppDetailSettings().stream()
				.map(x -> RequestAppDetailSettingDto.convertToDto(x))
				.collect(Collectors.toList()));
	}
}
