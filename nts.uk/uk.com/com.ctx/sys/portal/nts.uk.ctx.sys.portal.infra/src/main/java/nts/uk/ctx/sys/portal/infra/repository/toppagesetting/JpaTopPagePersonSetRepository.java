package nts.uk.ctx.sys.portal.infra.repository.toppagesetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSetRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPagePersonSet;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPagePersonSetPK;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaTopPagePersonSetRepository extends JpaRepository implements TopPagePersonSetRepository {

	private final String SELECT_TOPPAGE_PERSON_BYCODE = "SELECT c FROM CcgptTopPagePersonSet c"
			+ " WHERE c.ccgptTopPagePersonSetPK.companyId = :companyId "
			+ " AND c.ccgptTopPagePersonSetPK.employeeId = :employeeId";
	private TopPagePersonSet toDomain(CcgptTopPagePersonSet entity) {
		TopPagePersonSet domain = TopPagePersonSet.createFromJavaType(entity.ccgptTopPagePersonSetPK.companyId,
				entity.ccgptTopPagePersonSetPK.employeeId, entity.topMenuNo, entity.loginMenuNo, entity.loginSystem,
				entity.loginMenuCls);
		return domain;
	}

	@Override
	public Optional<TopPagePersonSet> findBySid(String companyId, String sId) {
		CcgptTopPagePersonSetPK ccgptTopPagePersonSetPK = new CcgptTopPagePersonSetPK(companyId, sId);
		return this.queryProxy().find(ccgptTopPagePersonSetPK, CcgptTopPagePersonSet.class).map(x -> toDomain(x));
	}

	@Override
	public TopPagePersonSet getbyCode(String companyId, String employeeId) {
		Optional<TopPagePersonSet> obj = this.queryProxy().query(SELECT_TOPPAGE_PERSON_BYCODE, CcgptTopPagePersonSet.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.getSingle(c->toDomain(c));
		if(!obj.isPresent()){
			return null;
		}
		return obj.get();
	}

}
