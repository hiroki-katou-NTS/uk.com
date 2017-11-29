package nts.uk.ctx.pereg.app.find.employment.history;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * @author sonnlb
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EmploymentHistoryDto extends PeregDomainDto {

	private List<DateHistoryItemDto> historyItems;

	public EmploymentHistoryDto(List<DateHistoryItemDto> historyItems) {
		this.historyItems = historyItems;
	}

	public static EmploymentHistoryDto createFromDomain(EmploymentHistory domain) {

		List<DateHistoryItemDto> historyItems = domain.getHistoryItems().stream()
				.map(x -> DateHistoryItemDto.createFromDomain(x)).collect(Collectors.toList());

		return new EmploymentHistoryDto(historyItems);

	}

}
