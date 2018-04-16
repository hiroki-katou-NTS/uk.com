package nts.uk.ctx.at.function.infra.repository.holidaysremaining;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.holidaysremaining.SpecialHoliday;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.SpecialHolidayRepository;
import nts.uk.ctx.at.function.infra.entity.holidaysremaining.KfnmtSpecialHoliday;
import nts.uk.ctx.at.function.infra.entity.holidaysremaining.KfnmtSpecialHolidayPk;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtLastExecDateTime;

@Stateless
public class JpaSpecialHolidayRepository extends JpaRepository implements SpecialHolidayRepository{

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KfnmtSpecialHoliday f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.specialHolidayPk.cid =:cid AND  f.specialHolidayPk.cd =:cd AND  f.specialHolidayPk.specialCd =:specialCd ";
	@Override
	public Optional<SpecialHoliday> get(String code, String holidayCode, String companyID) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, KfnmtSpecialHoliday.class)
				.setParameter("code", code)
				.setParameter("holidayCode", holidayCode)
				.setParameter("companyID", companyID).getSingle(c -> c.toDomain());
	}
	@Override
	public void insert(SpecialHoliday domain) {
		this.commandProxy().insert(KfnmtSpecialHoliday.toEntity(domain));
		
	}
	@Override
	public void update(SpecialHoliday domain) {
		KfnmtSpecialHoliday updateData = KfnmtSpecialHoliday.toEntity(domain);
		KfnmtSpecialHoliday oldData = this.queryProxy().find(updateData.kfnmtSpecialHolidayPk, KfnmtSpecialHoliday.class).get();
		this.commandProxy().update(oldData);
		
	}
	@Override
	public void remove(String code, String holidayCode, String companyID) {
		KfnmtSpecialHolidayPk kfnmtSpecialHolidayPk = new KfnmtSpecialHolidayPk(code, holidayCode,companyID);
		this.commandProxy().remove(KfnmtSpecialHoliday.class, kfnmtSpecialHolidayPk);
	}
}
