/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.sql.PreparedStatement;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeComRepo;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComRegLaborTime;

/**
 * The Class JpaComRegularLaborTimeRepository.
 */
@Stateless
public class JpaComRegularLaborTimeRepository extends JpaRepository
		implements RegularLaborTimeComRepo {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository#create(nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime)
	 */
	@Override
	public void create(RegularLaborTimeCom setting) {
		KshstComRegLaborTime entity = new KshstComRegLaborTime();

		entity.setDailyTime(setting.getDailyTime().getDailyTime().v());
		entity.setWeeklyTime(setting.getWeeklyTime().getTime().v());
		entity.setCid(setting.getComId());
		
		commandProxy().insert(entity);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime)
	 */
	@Override
	public void update(RegularLaborTimeCom setting) {
		KshstComRegLaborTime entity = this.queryProxy().find(setting.getComId(), KshstComRegLaborTime.class).get();

		entity.setDailyTime(setting.getDailyTime().getDailyTime().v());
		entity.setWeeklyTime(setting.getWeeklyTime().getTime().v());
		
		commandProxy().update(entity);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		commandProxy().remove(KshstComRegLaborTime.class, companyId);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository#find(java.lang.String)
	 */
	@SneakyThrows
	@Override
	public Optional<RegularLaborTimeCom> find(String companyId) {
		String sqlJdbc = "SELECT * FROM KSHST_COM_REG_LABOR_TIME WHERE CID = ?";

		try (PreparedStatement stmt = this.connection().prepareStatement(sqlJdbc)) {

			stmt.setString(1, companyId);

			return new NtsResultSet(stmt.executeQuery())
					.getSingle(rec -> {
						return RegularLaborTimeCom.of(rec.getString("CID"),
								new WeeklyUnit(new WeeklyTime(rec.getInt("WEEKLY_TIME"))), 
								new DailyUnit(new TimeOfDay(rec.getInt("DAILY_TIME"))));
					});
		}
	}
}
