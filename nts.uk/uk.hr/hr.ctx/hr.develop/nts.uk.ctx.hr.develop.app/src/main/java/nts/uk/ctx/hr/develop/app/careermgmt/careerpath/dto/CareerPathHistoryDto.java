package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathHistory;

@AllArgsConstructor
@Getter
public class CareerPathHistoryDto {

	public String companyId;

	public List<DateHistoryItemDto> careerPathHistory;

	public static CareerPathHistoryDto fromDomain(CareerPathHistory domain) {
		return new CareerPathHistoryDto(domain.getCompanyId(), domain.getCareerPathHistory().stream().map(c -> new DateHistoryItemDto(c.identifier(), c.start(), c.end()))
				.collect(Collectors.toList()));
	}
}
