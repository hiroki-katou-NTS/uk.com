package nts.uk.ctx.sys.portal.infra.repository.toppagesetting;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSetRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPageJobSet;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPageJobSetPK;

@Stateless
public class JpaTopPageJobSetRepository extends JpaRepository implements TopPageJobSetRepository {

	private final String SEL = "SELECT a FROM CcgptTopPageJobSet a ";
	private final String SEL_BY_LIST_JOB_ID = SEL
			+ "WHERE a.ccgptTopPageJobSetPK.companyId = :companyId AND a.ccgptTopPageJobSetPK.jobId IN :jobId";

	public static TopPageJobSet toDomain(CcgptTopPageJobSet entity) {
		TopPageJobSet domain = TopPageJobSet.createFromJavaType(entity.ccgptTopPageJobSetPK.companyId, entity.topMenuCd,
				entity.loginMenuCd, entity.ccgptTopPageJobSetPK.jobId, entity.personPermissionSet, entity.system);
		return domain;
	}

	private CcgptTopPageJobSet toEntity(TopPageJobSet domain) {
		val entity = new CcgptTopPageJobSet();

		entity.ccgptTopPageJobSetPK = new CcgptTopPageJobSetPK();
		entity.ccgptTopPageJobSetPK.companyId = domain.getCompanyId();
		entity.ccgptTopPageJobSetPK.jobId = domain.getJobId();
		entity.loginMenuCd = domain.getLoginMenuCode().v();
		entity.topMenuCd = domain.getTopMenuCode().v();
		entity.personPermissionSet = domain.getPersonPermissionSet().value;
		entity.system = domain.getSystem().value;
		return entity;
	}

	@Override
	public List<TopPageJobSet> findByListJobId(String companyId, List<String> jobId) {
		return this.queryProxy().query(SEL_BY_LIST_JOB_ID, CcgptTopPageJobSet.class)
				.setParameter("companyId", companyId).setParameter("jobId", jobId).getList(x -> toDomain(x));
	}

	@Override
	public void add(TopPageJobSet topPageJobSet) {
		this.commandProxy().insert(toEntity(topPageJobSet));
	}

	@Override
	public void update(TopPageJobSet topPageJobSet) {
		this.commandProxy().update(toEntity(topPageJobSet));
	}

}
