package nts.uk.ctx.sys.portal.infra.repository.toppagesetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import lombok.val;
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
@Transactional
public class JpaTopPagePersonSetRepository extends JpaRepository implements TopPagePersonSetRepository {
	private final String SEL = "SELECT c FROM CcgptTopPagePersonSet c ";
	private final String SELECT_TOPPAGE_PERSON_BYCODE = SEL + "WHERE c.ccgptTopPagePersonSetPK.companyId = :companyId "
			+ " AND c.ccgptTopPagePersonSetPK.employeeId = :employeeId";
	private final String SELECT_BY_LIST_SID = SEL + "WHERE c.ccgptTopPagePersonSetPK.companyId = :companyId "
			+ " AND c.ccgptTopPagePersonSetPK.employeeId IN :employeeId";

	private TopPagePersonSet toDomain(CcgptTopPagePersonSet entity) {
		TopPagePersonSet domain = TopPagePersonSet.createFromJavaType(entity.ccgptTopPagePersonSetPK.companyId,
				entity.ccgptTopPagePersonSetPK.employeeId, entity.topMenuCode, entity.loginMenuCode, entity.loginSystem,
				entity.loginMenuCls);
		return domain;
	}

	private CcgptTopPagePersonSet toEntity(TopPagePersonSet domain) {
		val entity = new CcgptTopPagePersonSet();

		entity.ccgptTopPagePersonSetPK = new CcgptTopPagePersonSetPK();
		entity.ccgptTopPagePersonSetPK.companyId = domain.getCompanyId();
		entity.ccgptTopPagePersonSetPK.employeeId = domain.getEmployeeId();
		entity.loginMenuCode = domain.getLoginMenuCode().v();
		entity.topMenuCode = domain.getTopMenuCode().v();
		entity.loginSystem = domain.getLoginSystem().value;
		entity.loginMenuCls = domain.getMenuClassification().value;
		return entity;
	}

	@Override
	public List<TopPagePersonSet> findByListSid(String companyId, List<String> sId) {
		return this.queryProxy().query(SELECT_BY_LIST_SID, CcgptTopPagePersonSet.class)
				.setParameter("companyId", companyId).setParameter("employeeId", sId).getList(x -> toDomain(x));
	}

	@Override
	public Optional<TopPagePersonSet> getbyCode(String companyId, String employeeId) {
		Optional<TopPagePersonSet> obj = this.queryProxy()
				.query(SELECT_TOPPAGE_PERSON_BYCODE, CcgptTopPagePersonSet.class).setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId).getSingle(c -> toDomain(c));
		return obj;
	}

	@Override
	public void update(TopPagePersonSet topPagePersonSet) {
		this.commandProxy().update(toEntity(topPagePersonSet));
	}

	@Override
	public void remove(String companyId, String Sid) {
		CcgptTopPagePersonSetPK pk = new CcgptTopPagePersonSetPK(companyId, Sid);
		this.commandProxy().remove(CcgptTopPagePersonSet.class, pk);
		;
	}

	@Override
	public void add(TopPagePersonSet topPagePersonSet) {
		this.commandProxy().insert(toEntity(topPagePersonSet));

	}

}
