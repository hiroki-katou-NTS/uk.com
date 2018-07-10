package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.monthlycheckcondition;

import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.commons.lang3.ThreadUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.AgreementCheckCon36;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.AgreementCheckCon36Repository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.SpecHolidayCheckCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.SpecHolidayCheckConRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition.KrcmtSpecHolidayCheckCon;
@Stateless
public class JpaSpecHolidayCheckConRepository extends JpaRepository implements SpecHolidayCheckConRepository  {

	private static final String SELECT_SPEC_HOLIDAY_BY_ID = " SELECT c FROM KrcmtSpecHolidayCheckCon c"
			+ " WHERE c.errorAlarmCheckID = :errorAlarmCheckID ";

	@Override
	public Optional<SpecHolidayCheckCon> getSpecHolidayCheckConById(String errorAlarmCheckID) {
		Optional<SpecHolidayCheckCon> data = this.queryProxy().query(SELECT_SPEC_HOLIDAY_BY_ID,KrcmtSpecHolidayCheckCon.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public void addSpecHolidayCheckCon(SpecHolidayCheckCon specHolidayCheckCon) {
		this.commandProxy().insert(KrcmtSpecHolidayCheckCon.toEntity(specHolidayCheckCon));
		this.getEntityManager().flush();
	}

	@Override
	public void updateSpecHolidayCheckCon(SpecHolidayCheckCon specHolidayCheckCon) {
		KrcmtSpecHolidayCheckCon newEntity = KrcmtSpecHolidayCheckCon.toEntity(specHolidayCheckCon);
		KrcmtSpecHolidayCheckCon updateEntity = this.queryProxy().find(specHolidayCheckCon.getErrorAlarmCheckID(), KrcmtSpecHolidayCheckCon.class).get();
		
		updateEntity.compareOperator = newEntity.compareOperator;
		updateEntity.numberDayDiffHoliday1 = newEntity.numberDayDiffHoliday1;
		updateEntity.numberDayDiffHoliday2 = newEntity.numberDayDiffHoliday2;
		this.commandProxy().update(updateEntity);
		this.getEntityManager().flush();
	}

	@Override
	public void deleteSpecHolidayCheckCon(String errorAlarmCheckID) {
		KrcmtSpecHolidayCheckCon newEntity = this.queryProxy().find(errorAlarmCheckID, KrcmtSpecHolidayCheckCon.class).get();
		this.commandProxy().remove(newEntity);
		this.getEntityManager().flush();
	}
	
	


}
