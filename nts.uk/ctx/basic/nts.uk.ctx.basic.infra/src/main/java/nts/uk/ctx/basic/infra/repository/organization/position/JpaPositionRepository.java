package nts.uk.ctx.basic.infra.repository.organization.position;




import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.organization.position.HistoryId;
import nts.uk.ctx.basic.dom.organization.position.JobCode;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;
import nts.uk.ctx.basic.dom.organization.position.JobTitle;
import nts.uk.ctx.basic.dom.organization.position.JobTitleRef;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobHist;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitle;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitlePK;


@Stateless
public class JpaPositionRepository extends JpaRepository implements PositionRepository{

	private final String SELECT_FROM_JOBTITLE ="SELECT c FROM CmnmtJobTitle c";
	private final String SELECT_FROM_JOBHIST ="SELECT c FROM CmnmtJobHist c";
	private final String SELECT_ALL_POSITION = SELECT_FROM_JOBTITLE + " WHERE c.cmnmtJobTitlePK.companyCode = :companyCode"
												+ " AND c.cmnmtJobTitlePK.historyId = :historyId";
	private final String SELECT_ALL_HISTORY = SELECT_FROM_JOBHIST +" WHERE c.cmnmtJobHistPK.companyCode = :companyCode";
	private final String SELECT_DETAIL = SELECT_ALL_POSITION + " AND c.cmnmtJobTitlePK.jobCode = :jobCode"
										+ " AND c.cmnmtJobTitlePK.historyId = :historyId";
	
	private static JobTitle toDomain(CmnmtJobTitle entity) {
		val domain = JobTitle.createSimpleFromJavaType(
				entity.cmnmtJobTitlePK.companyCode,
				entity.cmnmtJobTitlePK.historyId,
				entity.cmnmtJobTitlePK.jobCode, 
				entity.jobName,
				entity.presenceCheckScopeSet,
				entity.jobOutCode,
				entity.memo);
		return domain;
	}
	
	private static JobHistory toDomain2(CmnmtJobHist entity) {
		val domain = JobHistory.createSimpleFromJavaType(
				entity.startDate,
				entity.endDate,
				entity.cmnmtJobHistPK.companyCode,
				entity.cmnmtJobHistPK.historyId
				);
		return domain;
	}


	private static CmnmtJobTitle toEntity(JobTitle domain) {

		val entity = new CmnmtJobTitle();
		entity.cmnmtJobTitlePK = new CmnmtJobTitlePK();	
		entity.jobName = domain.getJobName().v();
		entity.presenceCheckScopeSet = domain.getPresenceCheckScopeSet().value;
		entity.jobOutCode = domain.getJobOutCode().v();
		entity.memo = domain.getMemo().v();

		return entity;

	}

	@Override
	public void add(JobTitle position) {
		this.commandProxy().insert(toEntity(position));
	}

	@Override
	public void update(JobTitle position) {
		this.commandProxy().update(toEntity(position));
	}

	//delete position is selected
	@Override
	public void delete(String companyCode, JobCode jobCode,String historyId) {
		CmnmtJobTitlePK cmnmtClassPK = new CmnmtJobTitlePK(companyCode, jobCode.toString(),historyId);
		this.commandProxy().remove(CmnmtJobTitle.class, cmnmtClassPK);
	}

	//find all position by historyId 
	@Override
	public List<JobTitle> findAllPosition(String companyCode, String historyId) {
	
 		return this.queryProxy().query(SELECT_ALL_POSITION, CmnmtJobTitle.class)
				.setParameter("companyCode", companyCode)
				.setParameter("historyId", historyId)
				.getList(c -> toDomain(c));
	}
	
	@Override
	public List<JobHistory> getAllHistory(String companyCode) {
	
		return this.queryProxy().query(SELECT_ALL_HISTORY, CmnmtJobHist.class)
				.setParameter("companyCode", companyCode)
				.getList(c -> toDomain2(c));
	}
	
	@Override
	public List<JobTitleRef> findAllJobTitleRef(String companyCode, String historyId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteHist(String companyCode, HistoryId historyId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addHistory(JobHistory history) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateHistory(JobHistory history) {
		// TODO Auto-generated method stub
		
	}



//	@Override
//	public Optional<Position> find(String companyCode, String jobCode, String historyId) {
//		return this.queryProxy().query(SELECT_DETAIL, CmnmtJobTitle.class)
//				.setParameter("companyCode", companyCode)
//				.setParameter("jobCode", jobCode)
//				.setParameter("historyId", historyId)
//				.getSingle().map(e -> {
//					return Optional.of(toDomain(e));
//				}).orElse(Optional.empty());
//}


}

