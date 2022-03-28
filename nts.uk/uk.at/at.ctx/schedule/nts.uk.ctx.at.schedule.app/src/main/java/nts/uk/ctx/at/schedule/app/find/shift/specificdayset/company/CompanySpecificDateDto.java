package nts.uk.ctx.at.schedule.app.find.shift.specificdayset.company;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;

@Value
public class CompanySpecificDateDto {

	public String companyId;
	public GeneralDate specificDate;
	public List<Integer> specificDateItemNo;

	public static CompanySpecificDateDto fromDomain(CompanySpecificDateItem domain) {
		List<Integer> specificDateItemNo = domain.getOneDaySpecificItem()
				.getSpecificDayItems()
				.stream()
				.map(item -> item.v())
				.collect(Collectors.toList());
		return new CompanySpecificDateDto(
				domain.getCompanyId(),
				domain.getSpecificDate(),
				specificDateItemNo);
	}
}
