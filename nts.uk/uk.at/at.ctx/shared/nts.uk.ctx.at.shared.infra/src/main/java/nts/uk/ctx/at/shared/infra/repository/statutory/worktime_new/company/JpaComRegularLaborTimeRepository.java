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
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComTransLabTime;

/**
 * The Class JpaComRegularLaborTimeRepository.
 */
@Stateless
public class JpaComRegularLaborTimeRepository extends JpaRepository
		implements ComRegularLaborTimeRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository#create(nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime)
	 */
	@Override
	public void create(ComRegularLaborTime setting) {
		KshstComRegLaborTime entity = new KshstComRegLaborTime();
		setting.saveToMemento(new JpaComRegularLaborTimeSetMemento(entity));
		commandProxy().insert(entity);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime)
	 */
	@Override
	public void update(ComRegularLaborTime setting) {
		KshstComRegLaborTime entity = this.queryProxy().find(setting.getCompanyId().v(), KshstComRegLaborTime.class).get();
		setting.saveToMemento(new JpaComRegularLaborTimeSetMemento(entity));
		commandProxy().update(entity);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		commandProxy().remove(KshstComTransLabTime.class, companyId);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository#find(java.lang.String)
	 */
	@SneakyThrows
	@Override
	public Optional<ComRegularLaborTime> find(String companyId) {
		String sqlJdbc = "SELECT * FROM KSHST_COM_REG_LABOR_TIME WHERE CID = ?";

		try (PreparedStatement stmt = this.connection().prepareStatement(sqlJdbc)) {

			stmt.setString(1, companyId);

			Optional<KshstComRegLaborTime> result = new NtsResultSet(stmt.executeQuery())
					.getSingle(rec -> {
						KshstComRegLaborTime entity = new KshstComRegLaborTime();
						entity.setCid(rec.getString("CID"));
						entity.setWeeklyTime(rec.getInt("WEEKLY_TIME"));
						entity.setWeekStr(rec.getInt("WEEK_STR"));
						entity.setDailyTime(rec.getInt("DAILY_TIME"));
						return entity;
					});

			if (!result.isPresent()) {
				return null;
			}

			return Optional.of(
					new ComRegularLaborTime(new JpaComRegularLaborTimeGetMemento(result.get())));
		}
	}
	
}
