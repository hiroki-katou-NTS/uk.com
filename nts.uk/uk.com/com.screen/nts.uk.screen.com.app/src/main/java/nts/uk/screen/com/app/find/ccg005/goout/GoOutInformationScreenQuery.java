package nts.uk.screen.com.app.find.ccg005.goout;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformationRepository;

//外出・戻り情報を取得 //TODO link
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
