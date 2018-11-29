package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableContent;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableContentRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaWageTableContentRepository extends JpaRepository implements WageTableContentRepository {

	@Override
	public List<WageTableContent> getAllWageTableContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WageTableContent> getWageTableContentById(String historyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(WageTableContent domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(WageTableContent domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String historyId) {
		// TODO Auto-generated method stub
		
	}

}
