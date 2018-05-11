package nts.uk.ctx.at.record.infra.repository.stamp.stampcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;

@Stateless
public class JpaStampCardRepository implements StampCardRepository{

	@Override
	public List<StampCard> getListStampCard(String sid) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public Optional<StampCard> getByStampCardId(String stampCardId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void add(StampCard domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(StampCard domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String stampCardId) {
		// TODO Auto-generated method stub
		
	}

	

}
