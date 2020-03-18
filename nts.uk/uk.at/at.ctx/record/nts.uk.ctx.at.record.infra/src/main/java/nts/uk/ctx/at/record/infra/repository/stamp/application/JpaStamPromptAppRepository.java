package nts.uk.ctx.at.record.infra.repository.stamp.application;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.application.StamPromptAppRepository;
import nts.uk.ctx.at.record.dom.stamp.application.StamPromptApplication;
import nts.uk.ctx.at.record.dom.stamp.application.StampRecordDis;
import nts.uk.ctx.at.record.infra.entity.stamp.application.KrcmtPromptApplication;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaStamPromptAppRepository extends JpaRepository implements StamPromptAppRepository{
	
	private static final String SELECT_ALL_PAGE = "SELECT c FROM KrcmtPromptApplication c ";
	
	private static final String SELECT_BY_CID_PAGE = SELECT_ALL_PAGE + " WHERE c.pk.companyId = :companyId";

	@Override
	public void insert(StamPromptApplication application) {
		commandProxy().insert(KrcmtPromptApplication.toEntity(application));
		
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
	
	@Override
	public List<StampRecordDis> getAllStampSetPage(String companyId) {
		List<StampRecordDis> data = this.queryProxy().query(SELECT_BY_CID_PAGE, KrcmtPromptApplication.class)
				.setParameter("companyId", companyId).getList(c -> c.toDomainRecord());
		if (data.isEmpty())
			return Collections.emptyList();

		return data.stream().collect(Collectors.toList());
	}
}
