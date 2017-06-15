package nts.uk.ctx.sys.portal.infra.repository.toppagesetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSetRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPageJobSet;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPageJobSetPK;

@Stateless
public class JpaTopPageJobSetRepository extends JpaRepository implements TopPageJobSetRepository {

//	private final String SEL = "SELECT a FROM CcgptTopPageJobSet a";

	public static TopPageJobSet toDomain(CcgptTopPageJobSet entity) {
		TopPageJobSet domain = TopPageJobSet.createFromJavaType(entity.ccgptTopPageJobSetPK.companyId, entity.topMenuCd,
				entity.loginMenuCd, entity.ccgptTopPageJobSetPK.jobId, entity.personPermissionSet, entity.system);
		return domain;
	}

	@Override
	public Optional<TopPageJobSet> findByJobId(String companyId, String jobId) {
		CcgptTopPageJobSetPK ccgptTopPageJobSetPK = new CcgptTopPageJobSetPK(companyId, jobId);
		return this.queryProxy().find(ccgptTopPageJobSetPK, CcgptTopPageJobSet.class).map(x -> toDomain(x));
	}

	@Override
	public void add(TopPageJobSet topPageJobSet) {
		this.commandProxy().insert(topPageJobSet);
	}

	@Override
	public void update(TopPageJobSet topPageJobSet) {
		this.commandProxy().update(topPageJobSet);
	}

}
