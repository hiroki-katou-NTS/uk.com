package nts.uk.ctx.at.function.infra.repository.alarm.checkcondition;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AlarmCheckSubConAgr;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.IAlarmCheckSubConAgrRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.annualholiday.KfnmtAlCheckSubConAg;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.annualholiday.KfnmtAlCheckSubConAgPK;

@Stateless
public class JpaAlarmCheckSubConAgrRepository extends JpaRepository implements IAlarmCheckSubConAgrRepository{

	@Override
	public Optional<AlarmCheckSubConAgr> findByKey(String companyId, int category, String code) {
		return this.queryProxy().find(new KfnmtAlCheckSubConAgPK(companyId, category, code), KfnmtAlCheckSubConAg.class).map(x -> x.toDomain());
	}

	@Override
	public void insert(String companyId, int category, String code, AlarmCheckSubConAgr alarmCheckSubConAgr) {
		this.commandProxy().insert(KfnmtAlCheckSubConAg.toEntity(companyId, code, category, alarmCheckSubConAgr));		
	}

	@Override
	public void update(String companyId, int category, String code, AlarmCheckSubConAgr alarmCheckSubConAgr) {
		this.commandProxy().update(KfnmtAlCheckSubConAg.toEntity(companyId, code, category, alarmCheckSubConAgr));
	}

	@Override
	public void delete(String companyId, int category, String code) {
		this.commandProxy().remove(KfnmtAlCheckSubConAg.class, new KfnmtAlCheckSubConAgPK(companyId, category, code));
	}

}
