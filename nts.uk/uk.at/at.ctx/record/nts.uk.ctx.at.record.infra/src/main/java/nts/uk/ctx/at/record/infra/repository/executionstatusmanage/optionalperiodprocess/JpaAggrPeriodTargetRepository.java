package nts.uk.ctx.at.record.infra.repository.executionstatusmanage.optionalperiodprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetRepository;
import nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess.KrcdtAnpPeriodTarget;
import nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess.KrcmtAggrPeriodTargetPK;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class JpaAggrPeriodTargetRepository extends JpaRepository
implements AggrPeriodTargetRepository{

	private static final String FIND_ALL_TARGET;
	private static final String FIND_ALL_TARGET_BYLISTID;
	private static final String FIND_EXECUTION;
	
	static{
	StringBuilder builderString = new StringBuilder();
	builderString.append("SELECT e");
	builderString.append(" FROM KrcdtAnpPeriodTarget e");
	builderString.append(" WHERE e.krcmtAggrPeriodTargetPK.aggrId = :aggrId");
	FIND_ALL_TARGET = builderString.toString();
	
	builderString = new StringBuilder();
	builderString.append("SELECT e");
	builderString.append(" FROM KrcdtAnpPeriodTarget e");
	builderString.append(" WHERE e.krcmtAggrPeriodTargetPK.aggrId = :aggrId");
	FIND_EXECUTION = builderString.toString();


	builderString = new StringBuilder();
	builderString.append("SELECT e");
	builderString.append(" FROM KrcdtAnpPeriodTarget e");
	builderString.append(" WHERE e.krcmtAggrPeriodTargetPK.aggrId  IN  :aggrIds");
	FIND_ALL_TARGET_BYLISTID = builderString.toString();


	}
	
	/**
	 * 
	 */
	@Override
	public List<AggrPeriodTarget> findAll(String aggrId) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(FIND_ALL_TARGET, KrcdtAnpPeriodTarget.class)
				.setParameter("aggrId", aggrId)
				.getList(c -> converToDomainApt(c));
	}

	@Override
	public List<AggrPeriodTarget> findAll(List<String> aggrIds) {

		List<AggrPeriodTarget> targetList = new ArrayList<>();

		CollectionUtil.split(aggrIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			targetList.addAll(this.queryProxy().query(FIND_ALL_TARGET_BYLISTID, KrcdtAnpPeriodTarget.class)
					.setParameter("aggrIds", subList)
					.getList(this::converToDomainApt));
		});
		return targetList;
	}

	/**
	 * 
	 * @param krcmtAggrPeriodTarget
	 * @return
	 */
	private AggrPeriodTarget converToDomainApt(KrcdtAnpPeriodTarget krcmtAggrPeriodTarget) {
		AggrPeriodTarget aggrPeriodTarget = AggrPeriodTarget.createFromJavaType(krcmtAggrPeriodTarget.krcmtAggrPeriodTargetPK.aggrId, 
				krcmtAggrPeriodTarget.krcmtAggrPeriodTargetPK.employeeId, krcmtAggrPeriodTarget.state);
		return aggrPeriodTarget;
	}

	@Override
	public void addTarget(List<AggrPeriodTarget> target) {
		this.commandProxy().insertAll(target.stream().map(item -> convertToDbTypeApt(item)).collect(Collectors.toList()));

	}

	// Update 1 list
	@Override
	public void updateTarget(List<AggrPeriodTarget> target) {
		this.commandProxy().updateAll(
				target.stream()
						.map(c -> new KrcdtAnpPeriodTarget(new KrcmtAggrPeriodTargetPK(c.getAggrId(), c.getEmployeeId()), c.getState().value))
						.collect(Collectors.toList()));
	}
	
	private KrcdtAnpPeriodTarget convertToDbTypeApt(AggrPeriodTarget target) {
		KrcdtAnpPeriodTarget entity = new KrcdtAnpPeriodTarget();
		entity.krcmtAggrPeriodTargetPK = new KrcmtAggrPeriodTargetPK(target.getAggrId(), target.getEmployeeId());
		entity.state = target.getState().value;
		return entity;
	}

	@Override
	public Optional<AggrPeriodTarget> findByAggr(String anyPeriodAggrLogId) {
		return this.queryProxy().query(FIND_EXECUTION, KrcdtAnpPeriodTarget.class)
				.setParameter("anyPeriodAggrLogId", anyPeriodAggrLogId )
				.getSingle(c -> converToDomainApt(c));
	}
	
	// Update 1 item
	@Override
	public void updateExcution(AggrPeriodTarget target) {
		KrcmtAggrPeriodTargetPK primaryKey = new KrcmtAggrPeriodTargetPK(target.getAggrId(),
				target.getEmployeeId());
		KrcdtAnpPeriodTarget periodTarget = this.queryProxy().find(primaryKey, KrcdtAnpPeriodTarget.class).get();
		//status is done
		periodTarget.state = 1;

		this.commandProxy().update(periodTarget);
	}

}
