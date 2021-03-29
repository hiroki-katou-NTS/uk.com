package nts.uk.screen.com.app.find.ccg005.goout;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformationRepository;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.E：外出入力.メニュー別OCD.外出・戻り情報を取得
 */
@Stateless
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
