package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KrcdtSyainDpErList;

@Stateless
public class JpaEmployeeDailyPerErrorRepository extends JpaRepository implements EmployeeDailyPerErrorRepository{

	@Override
	public void insert(EmployeeDailyPerError employeeDailyPerformanceError) {
		KrcdtSyainDpErList.toEntity(employeeDailyPerformanceError).forEach(f -> this.commandProxy().insert(f));
	}

}
