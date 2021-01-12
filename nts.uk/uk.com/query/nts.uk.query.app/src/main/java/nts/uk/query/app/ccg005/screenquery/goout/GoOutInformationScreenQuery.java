package nts.uk.query.app.ccg005.screenquery.goout;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformationRepository;

public class GoOutInformationScreenQuery {

	@Inject
	private GoOutEmployeeInformationRepository repo;

	public GoOutEmployeeInformationDto getGoOutInformation(String sid, GeneralDate date) {
		Optional<GoOutEmployeeInformation> domain = repo.getBySidAndDate(sid, date);
		GoOutEmployeeInformationDto dto = GoOutEmployeeInformationDto.builder().build();
		domain.ifPresent(goOut -> goOut.setMemento(dto));
		return dto;
	}
}
