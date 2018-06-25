package nts.uk.ctx.at.record.infra.repository.executionstatusmanage.optionalperiodprocess;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetRepository;
import nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess.KrcmtAggrPeriodTarget;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class JpaAggrPeriodTargetRepository extends JpaRepository
implements AggrPeriodTargetRepository{

	private static final String FIND_ALL;	
	static{
	StringBuilder builderString = new StringBuilder();
	builderString.append("SELECT e");
	builderString.append(" FROM KrcmtAggrPeriodTarget e");
	builderString.append(" WHERE e.krcmtAggrPeriodTargetPK.anyPeriodAggrLogId = :anyPeriodAggrLogId");
	FIND_ALL = builderString.toString();
	}
	
	/**
	 * 
	 */
	@Override
	public List<AggrPeriodTarget> findAll(String anyPeriodAggrLogId) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(FIND_ALL, KrcmtAggrPeriodTarget.class)
				.setParameter("anyPeriodAggrLogId", anyPeriodAggrLogId)
				.getList(c -> converToDomainApt(c));
	}

	/**
	 * 
	 * @param krcmtAggrPeriodTarget
	 * @return
	 */
	private AggrPeriodTarget converToDomainApt(KrcmtAggrPeriodTarget krcmtAggrPeriodTarget) {
		AggrPeriodTarget aggrPeriodTarget = AggrPeriodTarget.createFromJavaType(krcmtAggrPeriodTarget.krcmtAggrPeriodTargetPK.anyPeriodAggrLogId, 
				krcmtAggrPeriodTarget.krcmtAggrPeriodTargetPK.memberId, krcmtAggrPeriodTarget.state);
		return aggrPeriodTarget;
	}

}
