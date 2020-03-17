package nts.uk.ctx.at.record.infra.repository.stamp.application;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.uk.ctx.at.record.dom.stamp.application.StamPromptAppRepository;
import nts.uk.ctx.at.record.dom.stamp.application.StamPromptApplication;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaStamPromptAppRepository implements StamPromptAppRepository{

	@Override
	public void insert(StamPromptApplication application) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(StamPromptApplication application) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<StamPromptApplication> getStampSet(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
