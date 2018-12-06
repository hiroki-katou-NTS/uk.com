package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import java.util.*;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.*;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTableComboPayment;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTableQualifyGroupSet;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaWageTableContentRepository extends JpaRepository implements WageTableContentRepository {
	private static final String NONE_GROUP = "グループなし";

	@Override
	public List<WageTableContent> getAllWageTableContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WageTableContent> getWageTableContentById(String historyId) {
		String queryWagePayment = "SELECT p FROM QpbmtWageTableComboPayment p WHERE  p.historyId = :historyId";
		String queryWageQualificationGroup = "SELECT g FROM QpbmtWageTableQualifyGroupSet g WHERE  g.pk.historyId = :historyId";
		List<ElementsCombinationPaymentAmount> listPayment = this.queryProxy()
				.query(queryWagePayment, QpbmtWageTableComboPayment.class).setParameter("historyId", historyId)
				.getList(i -> i.toDomain());
		List<QpbmtWageTableQualifyGroupSet> listGroupCodeEntity = this.queryProxy()
				.query(queryWageQualificationGroup, QpbmtWageTableQualifyGroupSet.class)
				.setParameter("historyId", historyId).getList();
		if (listPayment.isEmpty() && listGroupCodeEntity.isEmpty())
			return Optional.empty();
		Map<String, List<QpbmtWageTableQualifyGroupSet>> mapGroupCodeEntity = listGroupCodeEntity.stream()
				.collect(Collectors.groupingBy(i -> i.pk.qualificationGroupCode));
		List<QualificationGroupSettingContent> listGroupCode = new ArrayList<>();
		mapGroupCodeEntity.entrySet().forEach(i -> {
			listGroupCode.add(QpbmtWageTableQualifyGroupSet.toDomain(i.getValue()));
		});
		return Optional.of(new WageTableContent(historyId, listPayment,
				listGroupCode.isEmpty() ? Optional.empty() : Optional.of(listGroupCode)));
	}

	@Override
	public void addOrUpdate(WageTableContent domain) {
		this.remove(domain.getHistoryID());
		List<QpbmtWageTableComboPayment> listPaymentEntity = domain.getPayments().stream()
				.map(i -> new QpbmtWageTableComboPayment(i, domain.getHistoryID())).collect(Collectors.toList());
		List<QpbmtWageTableQualifyGroupSet> listGroupSetEntity = new ArrayList<>();
		domain.getQualificationGroupSettings().ifPresent(listSetting -> {
			listSetting.forEach(i -> {
				listGroupSetEntity.addAll(QpbmtWageTableQualifyGroupSet.fromDomain(i, domain.getHistoryID()));
			});
		});
		if (!listPaymentEntity.isEmpty()) {
			this.commandProxy().insertAll(listPaymentEntity);
		}
		if (!listGroupSetEntity.isEmpty()) {
			this.commandProxy().insertAll(listGroupSetEntity);
		}
	}

	@Override
	public void remove(String historyId) {
		String queryDeleteWagePayment = "DELETE FROM QpbmtWageTableComboPayment p WHERE  p.historyId = :historyId";
		this.getEntityManager().createQuery(queryDeleteWagePayment).setParameter("historyId", historyId)
				.executeUpdate();
		String queryDeleteWageGroupSet = "DELETE FROM QpbmtWageTableQualifyGroupSet g WHERE  g.pk.historyId = :historyId";
		this.getEntityManager().createQuery(queryDeleteWageGroupSet).setParameter("historyId", historyId)
				.executeUpdate();
	}

	@Override
	public List<WageTableQualification> getWageTableQualification(String historyId) {
		String wageTableQualificationSQL
				= " SELECT" +
				"   wgs.pk.qualificationGroupCode," +
				"   qgs.qualificationGroupName," +
				"   wgs.pk.elegibleQualificationCode," +
				"   qi.qualificationName," +
				"   wcp.paymentAmount," +
				"   wgs.paymentMethod" +
				" FROM" +
				"   QpbmtWageTableComboPayment wcp" +
				"   LEFT JOIN QpbmtWageTableQualifyGroupSet wgs ON wcp.historyId = wgs.pk.historyId AND wcp.masterCode1 = wgs.pk.elegibleQualificationCode" +
				"   LEFT JOIN QpbmtQualificationGroupSetting qgs ON wgs.pk.qualificationGroupCode = qgs.pk.qualificationGroupCode AND wgs.pk.elegibleQualificationCode = qgs.pk.eligibleQualificationCode" +
				"   INNER JOIN QpbmtQualificationInformation qi ON qgs.pk.eligibleQualificationCode = qi.pk.qualificationCode" +
				" WHERE wcp.historyId =:historyId AND qgs.pk.cid =:cid ORDER BY wgs.pk.qualificationGroupCode, wgs.pk.elegibleQualificationCode";
		List<Object[]> data = this.queryProxy()
				.query(wageTableQualificationSQL, Object[].class)
				.setParameter("historyId", historyId)
				.setParameter("cid", AppContexts.user().companyId())
				.getList();

		Map<String, List<Object[]>> result = data.stream().collect(Collectors.groupingBy(x -> String.valueOf(x[0])));

		return getWageTableQualificationFromDB(result);
	}

	@Override
	public List<WageTableQualification> getDefaultWageTableQualification() {
		String wageTableQualificationSQL
				= " SELECT" +
				"   qgs.pk.qualificationGroupCode," +
				"   qgs.qualificationGroupName," +
				"   qi.pk.qualificationCode," +
				"   qi.qualificationName," +
				"   null," +
				"   qgs.paymentMethod" +
				" FROM" +
				"   QpbmtQualificationInformation qi" +
				"   LEFT JOIN QpbmtQualificationGroupSetting qgs ON qi.pk.cid = qgs.pk.cid AND qi.pk.qualificationCode = qgs.pk.eligibleQualificationCode" +
				" WHERE qi.pk.cid =:cid ORDER BY CASE WHEN qgs.pk.qualificationGroupCode IS NULL THEN 2 ELSE 1 END, qgs.pk.qualificationGroupCode, qi.pk.qualificationCode";
		List<Object[]> data = this.queryProxy()
				.query(wageTableQualificationSQL, Object[].class)
				.setParameter("cid", AppContexts.user().companyId())
				.getList();

		Map<String, List<Object[]>> result = data.stream().collect(Collectors.groupingBy(x -> String.valueOf(x[0])));

		return getWageTableQualificationFromDB(result);
	}

	private List<WageTableQualification> getWageTableQualificationFromDB(Map<String, List<Object[]>> result){
		List<WageTableQualification> wageTableQualification = new ArrayList<>();
		result.forEach((k, v) -> {
			String qualificationGroupCode = (String) v.get(0)[0];
			String qualificationGroupName = (String) v.get(0)[1];
			int paymentMethod             = (int) v.get(0)[5];
			wageTableQualification.add(new WageTableQualification(
					qualificationGroupCode,
					Objects.isNull(qualificationGroupCode) ? NONE_GROUP : qualificationGroupName,
					paymentMethod,
					v.stream().map(x -> new WageTableQualificationInfo(
							x[2].toString(),
							x[3].toString(),
							(Long) x[4]
					)).collect(Collectors.toList())
			));
		});
		return wageTableQualification;
	}
}