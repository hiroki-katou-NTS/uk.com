package nts.uk.ctx.at.record.infra.repository.workinformation;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformationRepository;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcmtDaiPerWorkInfo;

/**
 * 
 * @author lamdt
 *
 */
@Stateless
public class JPAWorkInformationRepository extends JpaRepository implements WorkInformationRepository {

	private static final String FIND_BY_ID = "SELECT a FROM KrcmtDaiPerWorkInfo"
			+ " WHERE a.krcmtDaiPerWorkInfoPK.employeeId = :employeeId";

	@Override
	public Optional<WorkInfoOfDailyPerformance> find(String employeeId) {
		return this.queryProxy().query(FIND_BY_ID, KrcmtDaiPerWorkInfo.class)
				.setParameter("employeeId", employeeId).getSingle(c -> c.toDomain());
	}

}