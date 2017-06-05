package nts.uk.ctx.at.record.infra.repository.stamp.stampcard;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.card.StampCardItem;
import nts.uk.ctx.at.record.dom.stamp.card.StampCardRepository;


@Stateless
public class JpaStampCardRepository extends JpaRepository implements StampCardRepository{
	
	
	
//	private final String SELECT_BY_PERSON = "SELECT c FROM KwkdtStampCard c"
//			+ " WHERE c.kwkdtStampCardPK.companyId = :companyId" 
//			+ " AND c.kwkdtStampCardPK.PID = :personId";
	@Override
	public Optional<StampCardItem> findByPersonID(String PID) {
		// TODO Auto-generated method stub
		return null;
	}

}
