package nts.uk.ctx.exio.infra.repository.exo.authset;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.dom.exo.authset.ExOutCtgAuthSet;
import nts.uk.ctx.exio.dom.exo.authset.ExOutCtgAuthSetRepository;
import nts.uk.ctx.exio.infra.entity.exo.authset.OiomtExOutCtgAuthSet;
import nts.uk.shr.infra.permit.data.JpaAvailablityPermissionRepositoryBase;

@Stateless
public class JpaExOutCtgAuthSetRepository
		extends JpaAvailablityPermissionRepositoryBase<ExOutCtgAuthSet, OiomtExOutCtgAuthSet>
		implements ExOutCtgAuthSetRepository {

	@Override
	protected Class<OiomtExOutCtgAuthSet> getEntityClass() {
		return OiomtExOutCtgAuthSet.class;
	}

	@Override
	protected OiomtExOutCtgAuthSet createEmptyEntity() {
		return new OiomtExOutCtgAuthSet();
	}

}
