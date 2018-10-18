package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.paymana.KrcmtPayoutSubOfHDMana;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.paymana.KrcmtPayoutSubOfHDManaPK;

@Stateless
public class JpaPayoutSubofHDManaRepository extends JpaRepository implements PayoutSubofHDManaRepository {

	private static final String QUERY = "SELECT ps FROM KrcmtPayoutSubOfHDMana ps ";
	
	private static final String QUERY_BY_PAYOUTID = String.join(" ",QUERY, " WHERE ps.krcmtPayoutSubOfHDManaPK.payoutId =:payoutId");
	private static final String QUERY_BY_SUBID = String.join(" ",QUERY, " WHERE ps.krcmtPayoutSubOfHDManaPK.subOfHDID =:subOfHDID");
	private static final String QUERY_BY_LIST_PAYOUT_ID = String.join(" ",QUERY, " WHERE ps.krcmtPayoutSubOfHDManaPK.payoutId IN :listPayoutID");
	private static final String QUERY_BY_LIST_SUB_ID = String.join(" ",QUERY, " WHERE ps.krcmtPayoutSubOfHDManaPK.subOfHDID IN :listSubID");
	private static final String DELETE_BY_PAYOUTID = "DELETE FROM KrcmtPayoutSubOfHDMana ps WHERE ps.krcmtPayoutSubOfHDManaPK.payoutId =:payoutId";
	private static final String DELETE_BY_SUBID = "DELETE FROM KrcmtPayoutSubOfHDMana ps WHERE ps.krcmtPayoutSubOfHDManaPK.subOfHDID =:subOfHDID";
	
	@Override
	public void add(PayoutSubofHDManagement domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(PayoutSubofHDManagement domain) {
		KrcmtPayoutSubOfHDManaPK key = new KrcmtPayoutSubOfHDManaPK(domain.getPayoutId(), domain.getSubOfHDID());
		Optional<KrcmtPayoutSubOfHDMana> existed = this.queryProxy().find(key, KrcmtPayoutSubOfHDMana.class);
		if (existed.isPresent()){
			this.commandProxy().update(toEntity(domain));	
		}
	}

	@Override
	public void delete(String payoutId, String subOfHDID) {
		KrcmtPayoutSubOfHDManaPK key = new KrcmtPayoutSubOfHDManaPK(payoutId, subOfHDID);
		Optional<KrcmtPayoutSubOfHDMana> existed = this.queryProxy().find(key, KrcmtPayoutSubOfHDMana.class);
		if (existed.isPresent()){
			this.commandProxy().remove(KrcmtPayoutSubOfHDMana.class, key);	
		}
		
	}
	
	/** 
	 * Convert from enity to domain
	 * @param entity
	 * @return
	 */
	private PayoutSubofHDManagement toDomain(KrcmtPayoutSubOfHDMana entity){
		return new PayoutSubofHDManagement(entity.krcmtPayoutSubOfHDManaPK.payoutId, entity.krcmtPayoutSubOfHDManaPK.subOfHDID, entity.usedDays, entity.targetSelectionAtr);
	}
	
	/**
	 * Convert from domain to entity
	 * @param domain
	 * @return
	 */
	private KrcmtPayoutSubOfHDMana toEntity(PayoutSubofHDManagement domain){
		KrcmtPayoutSubOfHDManaPK key = new KrcmtPayoutSubOfHDManaPK(domain.getPayoutId(), domain.getSubOfHDID());
		return new KrcmtPayoutSubOfHDMana(key, domain.getUsedDays().v(), domain.getTargetSelectionAtr().value);
	}

	@Override
	public List<PayoutSubofHDManagement> getByPayoutId(String payoutId) {
		List<KrcmtPayoutSubOfHDMana> listpayoutSub = this.queryProxy().query(QUERY_BY_PAYOUTID,KrcmtPayoutSubOfHDMana.class)
				.setParameter("payoutId", payoutId).getList();
		return listpayoutSub.stream().map(item->toDomain(item)).collect(Collectors.toList());
	}

	@Override
	public List<PayoutSubofHDManagement> getBySubId(String subID) {
		List<KrcmtPayoutSubOfHDMana> listpayoutSub = this.queryProxy().query(QUERY_BY_SUBID,KrcmtPayoutSubOfHDMana.class)
				.setParameter("subOfHDID", subID).getList();
		return listpayoutSub.stream().map(item->toDomain(item)).collect(Collectors.toList());
	}

	@Override
	public void delete(String payoutId) {
		this.getEntityManager().createQuery(DELETE_BY_PAYOUTID).setParameter("payoutId", payoutId).executeUpdate();
	}

	@Override
	public void deleteBySubID(String subID) {
		this.getEntityManager().createQuery(DELETE_BY_SUBID).setParameter("subOfHDID", subID).executeUpdate();
	}

	@Override
	public List<PayoutSubofHDManagement> getByListPayoutID(List<String> listPayoutID) {
		List<KrcmtPayoutSubOfHDMana> listpayoutSub = new ArrayList<>();
		CollectionUtil.split(listPayoutID, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			listpayoutSub.addAll(this.queryProxy().query(QUERY_BY_LIST_PAYOUT_ID,KrcmtPayoutSubOfHDMana.class)
									.setParameter("listPayoutID", subList)
									.getList());
		});
		return listpayoutSub.stream().map(item->toDomain(item)).collect(Collectors.toList());
	}

	@Override
	public List<PayoutSubofHDManagement> getByListSubID(List<String> listSubID) {
		List<KrcmtPayoutSubOfHDMana> listpayoutSub = new ArrayList<>();
		CollectionUtil.split(listSubID, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			listpayoutSub.addAll(this.queryProxy().query(QUERY_BY_LIST_SUB_ID,KrcmtPayoutSubOfHDMana.class)
									 .setParameter("listSubID", subList)
									 .getList());
		});
		return listpayoutSub.stream().map(item->toDomain(item)).collect(Collectors.toList());
	}

}
