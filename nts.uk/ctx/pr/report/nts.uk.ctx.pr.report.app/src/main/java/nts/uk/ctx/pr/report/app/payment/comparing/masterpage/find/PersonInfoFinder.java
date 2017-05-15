package nts.uk.ctx.pr.report.app.payment.comparing.masterpage.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.dom.payment.comparing.masterpage.PersonInfoRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PersonInfoFinder {

	@Inject
	private PersonInfoRepository personInfoRepository;

	public List<PersonInfoDto> getPersonInfo() {
		return this.personInfoRepository.getPersonInfo(AppContexts.user().companyCode()).stream()
				.map(p -> new PersonInfoDto(p.getEmployeeCode().v(), p.getEmployeeName().v()))
				.collect(Collectors.toList());
	};
}
