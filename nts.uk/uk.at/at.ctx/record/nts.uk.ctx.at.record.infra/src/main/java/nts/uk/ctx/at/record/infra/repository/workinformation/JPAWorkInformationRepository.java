package nts.uk.ctx.at.record.infra.repository.workinformation;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformationRepository;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtDaiPerWorkInfo;

/**
 * 
 * @author lamdt
 *
 */
@Stateless
public class JPAWorkInformationRepository extends JpaRepository implements WorkInformationRepository {

	private static final String DEL_BY_KEY;

	private static final String FIND_BY_LIST_SID;

	private static final String DEL_BY_LIST_KEY;

	private static final String FIND_BY_ID = "SELECT a FROM KrcmtDaiPerWorkInfo"
			+ " WHERE a.krcmtDaiPerWorkInfoPK.employeeId = :employeeId " + " AND a.krcmtDaiPerWorkInfoPK.ymd = :ymd ";

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcmtDaiPerWorkInfo a ");
		builderString.append("WHERE WHERE a.krcmtDaiPerWorkInfoPK.employeeId = :employeeId ");
		builderString.append("AND a.krcmtDaiPerWorkInfoPK.ymd = :ymd ");
		DEL_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtDaiPerWorkInfo a ");
		builderString.append("WHERE a.krcmtDaiPerWorkInfoPK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcmtDaiPerWorkInfoPK.ymd IN :ymds ");
		FIND_BY_LIST_SID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcmtDaiPerWorkInfo a ");
		builderString.append("WHERE WHERE a.krcmtDaiPerWorkInfoPK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcmtDaiPerWorkInfoPK.ymd IN :ymds ");
		DEL_BY_LIST_KEY = builderString.toString();
	}

	@Override
	public Optional<WorkInfoOfDailyPerformance> find(String employeeId, GeneralDate ymd) {
		return this.queryProxy().query(FIND_BY_ID, KrcdtDaiPerWorkInfo.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).getSingle(c -> c.toDomain());
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(DEL_BY_KEY).setParameter("employeeId", employeeId).setParameter("ymd", ymd)
				.executeUpdate();
	}

	@Override
	public List<WorkInfoOfDailyPerformance> findByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		return this.queryProxy().query(FIND_BY_LIST_SID, KrcdtDaiPerWorkInfo.class)
				.setParameter("employeeIds", employeeIds).setParameter("ymds", ymds).getList(f -> f.toDomain());
	}

	@Override
	public void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		this.getEntityManager().createQuery(DEL_BY_LIST_KEY).setParameter("employeeIds", employeeIds)
				.setParameter("ymds", ymds).executeUpdate();
	}

}