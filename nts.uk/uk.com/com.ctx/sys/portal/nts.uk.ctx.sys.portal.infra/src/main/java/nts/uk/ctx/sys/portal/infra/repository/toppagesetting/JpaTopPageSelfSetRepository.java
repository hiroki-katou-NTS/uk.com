package nts.uk.ctx.sys.portal.infra.repository.toppagesetting;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSelfSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSelfSetRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPageSelfSet;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPageSelfSetPK;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaTopPageSelfSetRepository extends JpaRepository implements TopPageSelfSetRepository {
	private final String SELECT_TOPPAGE_SELFSET = "SELECT c FROM CcgptTopPageSelfSet c"
			+ " WHERE c.ccgptTopPageSelfSetPK.employeeId = :employeeId";
	private final String SELECT_TOPPAGE_SELFSET_BYCODE = "SELECT c FROM CcgptTopPageSelfSet c"
			+ " WHERE c.ccgptTopPageSelfSetPK.employeeId = :employeeId"
			+ " AND c.ccgptTopPageSelfSetPK.code = :code";
	private static TopPageSelfSet toDomain(CcgptTopPageSelfSet entity){
		val domain = TopPageSelfSet.createFromJavaType(
				entity.ccgptTopPageSelfSetPK.employeeId,
				entity.ccgptTopPageSelfSetPK.code,
				entity.ccgptTopPageSelfSetPK.division);
		return domain;
	}
	private static CcgptTopPageSelfSet toEntity(TopPageSelfSet domain){
		val entity = new CcgptTopPageSelfSet();
		entity.ccgptTopPageSelfSetPK = new CcgptTopPageSelfSetPK(domain.getEmployeeId(),
												domain.getCode(),
												domain.getDivision().value);
		return entity;
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
 	 * find top page self set by code
 	 * @param employeeId
 	 * @param code
 	 * @return
 	 */
	@Override
	public Optional<TopPageSelfSet> findTopPageSelfSetbyCode(String employeeId, String code) {
		return this.queryProxy().query(SELECT_TOPPAGE_SELFSET_BYCODE, CcgptTopPageSelfSet.class)
				.setParameter("employeeId", employeeId)
				.setParameter("code", code)
				.getSingle(c->toDomain(c));
	}
 	/**
 	 * update top page self set
 	 * @param topPageSelfSet
 	 */
	@Override
	public void updateTopPageSelfSet(TopPageSelfSet topPageSelfSet) {
		this.commandProxy().update(toEntity(topPageSelfSet));
	}
}
