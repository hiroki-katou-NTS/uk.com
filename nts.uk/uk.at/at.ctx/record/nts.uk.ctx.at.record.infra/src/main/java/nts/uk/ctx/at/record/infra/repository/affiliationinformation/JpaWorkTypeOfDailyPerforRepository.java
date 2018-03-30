package nts.uk.ctx.at.record.infra.repository.affiliationinformation;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.WorkTypeOfDailyPerforRepository;
import nts.uk.ctx.at.record.infra.entity.affiliationinformation.KrcdtDaiWorkType;

@Stateless
public class JpaWorkTypeOfDailyPerforRepository extends JpaRepository implements WorkTypeOfDailyPerforRepository {

	private static final String FIND_BY_KEY;

	private static final String REMOVE_BY_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiWorkType a ");
		builderString.append("WHERE a.krcdtDaiWorkTypePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiWorkTypePK.ymd = :ymd ");
		REMOVE_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiWorkType a ");
		builderString.append("WHERE a.krcdtDaiWorkTypePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiWorkTypePK.ymd = :ymd ");
		FIND_BY_KEY = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate processingDate) {
		this.getEntityManager().createQuery(REMOVE_BY_KEY).setParameter("employeeId", employeeId)
				.setParameter("ymd", processingDate).executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	public void add(WorkTypeOfDailyPerformance workTypeOfDailyPerformance) {
		this.commandProxy().insert(KrcdtDaiWorkType.toEntity(workTypeOfDailyPerformance));
		this.getEntityManager().flush();
	}

	@Override
	public void update(WorkTypeOfDailyPerformance workTypeOfDailyPerformance) {
		Optional<KrcdtDaiWorkType> data = this.queryProxy().query(FIND_BY_KEY, KrcdtDaiWorkType.class)
				.setParameter("employeeId", workTypeOfDailyPerformance.getEmployeeId())
				.setParameter("ymd", workTypeOfDailyPerformance.getDate()).getSingle();
		if (data.isPresent()) {
			data.get().krcdtDaiWorkTypePK.employeeId = workTypeOfDailyPerformance.getEmployeeId();
			data.get().krcdtDaiWorkTypePK.ymd = workTypeOfDailyPerformance.getDate();
			data.get().workTypeCode = workTypeOfDailyPerformance.getWorkTypeCode().v();
			
			this.commandProxy().update(data.get());
		}
	}

	@Override
	public Optional<WorkTypeOfDailyPerformance> findByKey(String employeeId, GeneralDate processingDate) {
		Optional<WorkTypeOfDailyPerformance> data = this.queryProxy().query(FIND_BY_KEY, KrcdtDaiWorkType.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", processingDate).getSingle(f -> f.toDomain());
		if (data.isPresent()) {
			return data;
		} else {
			return Optional.empty();
		}
	}

}
