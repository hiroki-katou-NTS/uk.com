package nts.uk.ctx.at.record.infra.repository.stamp.stampcard;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.stamp.card.StampCardItem;
import nts.uk.ctx.at.record.dom.stamp.card.StampCardtemRepository;
import nts.uk.ctx.at.record.infra.entity.stamp.stampcard.KwkdtStampCard;

@Stateless
public class JpaStampCardItemRepository extends JpaRepository implements StampCardtemRepository {


	private static final String SELECT_BY_SID = "SELECT c FROM KwkdtStampCard c"
			+ " WHERE c.sid = :employeeID";
	
	private static final String SELECT_BY_LIST_PERSON = "SELECT c FROM KwkdtStampCard c"
			+ " WHERE c.sid IN :lstEmployeeId";

	private static StampCardItem toDomain(KwkdtStampCard entity) {
		StampCardItem domain = StampCardItem.createFromJavaType(
				entity.sid, 
				entity.cardNo);
		return domain;
	}

	@Override
	public List<StampCardItem> findByListEmployeeID(List<String> lstEmployeeId) {
		List<StampCardItem> resultList = new ArrayList<>();
		CollectionUtil.split(lstEmployeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SELECT_BY_LIST_PERSON, KwkdtStampCard.class)
				.setParameter("lstEmployeeId", subList)
				.getList(c -> toDomain(c)));
		});
		return resultList;
	}
	
	@Override
	public List<StampCardItem> findByEmployeeID(String employeeID) {
		return this.queryProxy().query(SELECT_BY_SID, KwkdtStampCard.class)
				.setParameter("employeeID", employeeID)
				.getList(c -> toDomain(c));
	}

}
