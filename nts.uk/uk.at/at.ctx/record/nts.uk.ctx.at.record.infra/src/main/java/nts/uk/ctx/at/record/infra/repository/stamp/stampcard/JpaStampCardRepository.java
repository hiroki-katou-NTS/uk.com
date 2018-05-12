package nts.uk.ctx.at.record.infra.repository.stamp.stampcard;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;

@Stateless
public class JpaStampCardRepository implements StampCardRepository {

	@Override
	public List<StampCard> getListStampCard(String sid) {
		return Arrays.asList(
				StampCard.createFromJavaType(IdentifierUtil.randomUniqueId(), sid, "a0000001", GeneralDate.today()),
				StampCard.createFromJavaType(IdentifierUtil.randomUniqueId(), sid, "a0000002", GeneralDate.today()));
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
