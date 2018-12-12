package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import java.util.*;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.StringUtil;
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
		String wageTableQualificationSQL =
				" SELECT" +
				"     wcp.id," +
				"     wgs.pk.qualificationGroupCode," +
				"     qgs.qualificationGroupName," +
				"     wgs.pk.elegibleQualificationCode," +
				"     qi.qualificationName," +
				"     wcp.paymentAmount," +
				"     wgs.paymentMethod" +
				" FROM" +
				"     QpbmtWageTableComboPayment wcp" +
				"     LEFT JOIN QpbmtWageTableQualifyGroupSet wgs ON wcp.historyId = wgs.pk.historyId AND wcp.masterCode1 = wgs.pk.elegibleQualificationCode" +
				"     LEFT JOIN QpbmtQualificationInformation qi ON wgs.pk.elegibleQualificationCode = qi.pk.qualificationCode" +
				"     LEFT JOIN QpbmtQualificationGroupSetting qgs ON qgs.pk.cid = qi.pk.cid AND wgs.pk.elegibleQualificationCode = qgs.pk.eligibleQualificationCode AND wgs.pk.qualificationGroupCode = qgs.pk.qualificationGroupCode " +
				" WHERE" +
				"     qi.pk.cid =:cid AND" +
				"     wgs.pk.historyId =:historyId" +
				" ORDER BY" +
				"     CASE WHEN wgs.pk.qualificationGroupCode = '  ' THEN 2 ELSE 1 END, wgs.pk.qualificationGroupCode, wgs.pk.elegibleQualificationCode";
		List<Object[]> data = this.queryProxy()
				.query(wageTableQualificationSQL, Object[].class)
				.setParameter("cid", AppContexts.user().companyId())
				.setParameter("historyId", historyId)
				.getList();

		Map<String, List<Object[]>> result = data.stream().collect(Collectors.groupingBy(x -> String.valueOf(x[1])))
				.entrySet().stream().sorted((o1, o2) -> {
					if (StringUtil.isNullOrEmpty(o1.getKey(), true) || StringUtil.isNullOrEmpty(o2.getKey(), true)) return -1;
					if (Integer.valueOf(o1.getKey()).compareTo(Integer.valueOf(o2.getKey())) > 0) return 1;
					return 0;
				})
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		return getWageTableQualificationFromDB(result);
	}

	@Override
	public List<WageTableQualification> getDefaultWageTableQualification() {
		String wageTableQualificationSQL
				= " SELECT" +
				"   null," +
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

		Map<String, List<Object[]>> result = data.stream().collect(Collectors.groupingBy(x -> String.valueOf(x[1])));

		return getWageTableQualificationFromDB(result);
	}

	private List<WageTableQualification> getWageTableQualificationFromDB(Map<String, List<Object[]>> result){
		List<WageTableQualification> wageTableQualification = new ArrayList<>();
		result.forEach((k, v) -> {
			String qualificationGroupCode = (String) v.get(0)[1];
			String qualificationGroupName = (String) v.get(0)[2];
			int paymentMethod             = (int) v.get(0)[6];
			wageTableQualification.add(new WageTableQualification(
					qualificationGroupCode,
					StringUtil.isNullOrEmpty(qualificationGroupCode, true) ? NONE_GROUP : qualificationGroupName,
					paymentMethod,
					v.stream().map(x -> new WageTableQualificationInfo(
							(String) x[0],
							(String) x[3],
							(String) x[4],
							(Long) x[5]
					)).collect(Collectors.toList())
			));
		});
		return wageTableQualification;
	}
}