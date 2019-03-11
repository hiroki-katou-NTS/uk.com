package nts.uk.ctx.at.function.infra.repository.alarm.checkcondition;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AlarmCheckConAgr;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.IAlarmCheckConAgrRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.annualholiday.KfnmtAlCheckConAg;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.annualholiday.KfnmtAlCheckConAgPK;

@Stateless
public class JpaAlarmCheckConAgrRepository extends JpaRepository implements IAlarmCheckConAgrRepository{

	@Override
	public Optional<AlarmCheckConAgr> findByKey(String companyId, int category, String code) {
		return this.queryProxy().find(new KfnmtAlCheckConAgPK(companyId, category, code), KfnmtAlCheckConAg.class).map(x -> x.toDomain());
	}

	@Override
	public void insert(String companyId, int category, String code, AlarmCheckConAgr alarmCheckConAgr) {
		this.commandProxy().insert(KfnmtAlCheckConAg.toEntity(companyId, code, category, alarmCheckConAgr));		
	}

	@Override
	public void update(String companyId, int category, String code, AlarmCheckConAgr alarmCheckConAgr) {
		this.commandProxy().update(KfnmtAlCheckConAg.toEntity(companyId, code, category, alarmCheckConAgr));
	}

	@Override
	public void delete(String companyId, int category, String code) {
		this.commandProxy().remove(KfnmtAlCheckConAg.class, new KfnmtAlCheckConAgPK(companyId, category, code));
	}

}
