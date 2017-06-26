package nts.uk.ctx.sys.portal.infra.repository.toppagesetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.toppagesetting.JobPosition;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSelfSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSelfSetRepository;
import nts.uk.ctx.sys.portal.infra.entity.layout.CcgmtLayout;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPageSelfSet;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPageSelfSetPK;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CjpmtJobPosition;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaTopPageSelfSetRepository extends JpaRepository implements TopPageSelfSetRepository {
	private final String SELECT_TOPPAGE_SELFSET = "SELECT c FROM CcgptTopPageSelfSet c"
			+ " WHERE c.ccgptTopPageSelfSetPK.employeeId = :employeeId";
	private final String SELECT_SINGLE = "SELECT c FROM CcgmtLayout c WHERE c.ccgmtLayoutPK.layoutID = :layoutID AND c.pgType = :pgType";
	private final String SELECT_JOB_POSITION = "SELECT c FROM CjpmtJobPosition c"
			+ " WHERE c.employeeId = :employeeId" + " AND c.startDate <= :date" + " AND c.endDate >= :date";
	
	private static TopPageSelfSet toDomain(CcgptTopPageSelfSet entity){
		val domain = TopPageSelfSet.createFromJavaType(
				entity.ccgptTopPageSelfSetPK.employeeId,
				entity.code);
		return domain;
	}
	private static CcgptTopPageSelfSet toEntity(TopPageSelfSet domain){
		val entity = new CcgptTopPageSelfSet();
		entity.ccgptTopPageSelfSetPK = new CcgptTopPageSelfSetPK(domain.getEmployeeId());
		entity.code = domain.getCode();
		return entity;
	}
	/**
	 * Convert entity to domain
	 * 
	 * @param entity CcgmtLayout
	 * @return Layout instance
	 */
	private Layout toDomainLayout(CcgmtLayout entity) {
		return Layout.createFromJavaType(entity.ccgmtLayoutPK.companyID, entity.ccgmtLayoutPK.layoutID, entity.pgType);
	}
	private JobPosition toDomainJobPosition(CjpmtJobPosition entity){
		return JobPosition.createSimpleFromJavaType(entity.getCjpmtJobPositionPK().id,
				entity.getEmployeeId(), 
				entity.getJobId(),
				entity.getStartDate(),
				entity.getEndDate());
	}
 	/**
 	 * get top page self set
 	 * @param employeeId
 	 * @return
 	 */
	@Override
	public Optional<TopPageSelfSet> getTopPageSelfSet(String employeeId) {
		return this.queryProxy().query(SELECT_TOPPAGE_SELFSET, CcgptTopPageSelfSet.class)
						.setParameter("employeeId", employeeId)
						.getSingle(c->toDomain(c));
	}
	/**
	 * Add the Top Page Self Setting.
	 * @param topPageSelfSet the TopPageSelfSet
	 */
	@Override
	public void addTopPageSelfSet(TopPageSelfSet topPageSelfSet) {
		this.commandProxy().insert(toEntity(topPageSelfSet));
	}
 	/**
 	 * update top page self set
 	 * @param topPageSelfSet
 	 */
	@Override
	public void updateTopPageSelfSet(TopPageSelfSet topPageSelfSet) {
		this.commandProxy().update(toEntity(topPageSelfSet));
	}
	
	@Override
	public Optional<Layout> find(String layoutID, int pgType) {
		return this.queryProxy().query(SELECT_SINGLE, CcgmtLayout.class)
				.setParameter("layoutID", layoutID)
				.setParameter("pgType", pgType)
				.getSingle(c -> toDomainLayout(c));
	}
	@Override
	public Optional<JobPosition> getJobPosition(String employeeId, GeneralDate date) {
		List<JobPosition> lst = this.queryProxy().query(SELECT_JOB_POSITION, CjpmtJobPosition.class)
				.setParameter("employeeId", employeeId)
				.setParameter("date", date)
				.getList(c->toDomainJobPosition(c));
		if(lst.isEmpty()){
			return Optional.empty();
		}else{
			return Optional.of(lst.get(0));
		}
	}
}
