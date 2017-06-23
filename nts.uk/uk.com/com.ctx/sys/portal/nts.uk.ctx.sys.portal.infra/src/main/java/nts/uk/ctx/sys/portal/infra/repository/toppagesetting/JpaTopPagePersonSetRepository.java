package nts.uk.ctx.sys.portal.infra.repository.toppagesetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSetRepository;

@Stateless
public class JpaTopPagePersonSetRepository extends JpaRepository implements TopPagePersonSetRepository {

	@Override
	public Optional<TopPagePersonSet> findBySid(String companyId, String sId) {
		// TODO Auto-generated method stub
		return null;
	}

}
