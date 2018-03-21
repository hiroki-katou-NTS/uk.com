package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.recruitmentapp;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentWorkingHour;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.recruitmentapp.KrqdtRecruitmentApp;

@Stateless
public class JpaRecruitmentAppRepository extends JpaRepository implements RecruitmentAppRepository {

	@Override
	public void insert(RecruitmentApp recApp) {
		this.commandProxy().insert(toEntity(recApp));

	}

	private KrqdtRecruitmentApp toEntity(RecruitmentApp recApp) {
		KrqdtRecruitmentApp entity = new KrqdtRecruitmentApp();
		entity.setAppID(recApp.getAppID());
		entity.setWorkTypeCD(recApp.getWorkTypeCD());
		entity.setWorkLocationCD(recApp.getWorkLocationCD().v());
		entity.setWorkTimeCD(recApp.getWorkTimeCD().v());

		RecruitmentWorkingHour wkTime1 = recApp.getWorkTime1();
		if (wkTime1 != null) {
			entity.setStartWorkTime1(wkTime1.getStartTime().v());
			entity.setStartUseAtr1(wkTime1.getStartUseAtr().value);
			entity.setEndWorkTime1(wkTime1.getEndTime().v());
			entity.setEndUseAtr1(wkTime1.getEndUseAtr().value);
		}
		RecruitmentWorkingHour wkTime2 = recApp.getWorkTime2();
		if (wkTime2 != null) {
			entity.setStartWorkTime2(wkTime2.getStartTime().v());
			entity.setStartUseAtr2(wkTime2.getStartUseAtr().value);
			entity.setEndWorkTime2(wkTime2.getEndTime().v());
			entity.setEndUseAtr2(wkTime2.getEndUseAtr().value);
		}

		return entity;

	}

}
