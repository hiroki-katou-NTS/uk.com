package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClosureForLogDto {
	private int closureId;
	
	private List<ClosureHistoryForLogDto> listClosureHistoryForLog;
	
	public static ClosureForLogDto fromDomain(Closure domain) {
		return new ClosureForLogDto(
				domain.getClosureId().value,
				domain.getClosureHistories().stream().map(c ->ClosureHistoryForLogDto.fromDomain(c) ).collect(Collectors.toList())
				);
		
	}
}
