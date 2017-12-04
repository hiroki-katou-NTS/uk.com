package nts.uk.ctx.at.record.infra.repository.workrecord.worktime;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDaiLeavingWork;

@Stateless
public class JpaTimeLeavingOfDailyPerformanceRepository extends JpaRepository
		implements TimeLeavingOfDailyPerformanceRepository {

	private static final String REMOVE_BY_EMPLOYEE;

	private static final String DEL_BY_LIST_KEY;

	private static final String FIND_BY_LIST_SID;

	private static final String FIND_BY_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiLeavingWork a ");
		builderString.append("WHERE a.krcdtDaiLeavingWorkPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiLeavingWorkPK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiLeavingWork a ");
		builderString.append("WHERE WHERE a.krcdtDaiLeavingWorkPK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDaiLeavingWorkPK.ymd IN :ymds ");
		DEL_BY_LIST_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiLeavingWork a ");
		builderString.append("WHERE a.krcdtDaiLeavingWorkPK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDaiLeavingWorkPK.ymd IN :ymds ");
		FIND_BY_LIST_SID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiLeavingWork a ");
		builderString.append("WHERE a.krcdtDaiLeavingWorkPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiLeavingWorkPK.ymd = :ymd ");
		FIND_BY_KEY = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).executeUpdate();
	}

	@Override
	public void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		this.getEntityManager().createQuery(DEL_BY_LIST_KEY).setParameter("employeeIds", employeeIds)
				.setParameter("processingYmds", ymds).executeUpdate();
	}

	@Override
	public List<TimeLeavingOfDailyPerformance> findByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		return this.queryProxy().query(FIND_BY_LIST_SID, KrcdtDaiLeavingWork.class)
				.setParameter("employeeIds", employeeIds).setParameter("ymds", ymds).getList(f -> f.toDomain());
	}

	@Override
	public Optional<TimeLeavingOfDailyPerformance> findByKey(String employeeId, GeneralDate ymd) {
		return this.queryProxy().query(FIND_BY_KEY, KrcdtDaiLeavingWork.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).getSingle(f -> f.toDomain());
	}

}
