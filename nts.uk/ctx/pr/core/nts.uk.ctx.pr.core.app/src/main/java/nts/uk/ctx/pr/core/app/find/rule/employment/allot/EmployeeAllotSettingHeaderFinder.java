package nts.uk.ctx.pr.core.app.find.rule.employment.allot;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.EmployeeAllotSettingHeaderRespository;

@Stateless
public class EmployeeAllotSettingHeaderFinder {
	@Inject
	private EmployeeAllotSettingHeaderRespository EmployeeAllotRepo;
	
	
	public List<EmployeeAllotSettingHeaderDto> getEmployeeAllotSettingHeader(String companyCode) {
		return this.EmployeeAllotRepo.findAll(companyCode).stream().map(m -> EmployeeAllotSettingHeaderDto.fromDomain(m))
				.collect(Collectors.toList());
	}
}