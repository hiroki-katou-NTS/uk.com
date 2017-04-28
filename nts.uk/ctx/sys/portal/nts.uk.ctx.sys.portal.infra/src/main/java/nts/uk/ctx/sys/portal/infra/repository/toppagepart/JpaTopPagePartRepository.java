package nts.uk.ctx.sys.portal.infra.repository.toppagepart;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;

/**
 * @author LamDT
 */
@Stateless
public class JpaTopPagePartRepository extends JpaRepository implements TopPagePartRepository {

	@Override
	public Optional<TopPagePart> find(String topPagePartID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TopPagePart> findByLayout(String topPagePartID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String companyID, String topPagePartID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(TopPagePart topPagePart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(TopPagePart topPagePart) {
		// TODO Auto-generated method stub
		
	}

}
