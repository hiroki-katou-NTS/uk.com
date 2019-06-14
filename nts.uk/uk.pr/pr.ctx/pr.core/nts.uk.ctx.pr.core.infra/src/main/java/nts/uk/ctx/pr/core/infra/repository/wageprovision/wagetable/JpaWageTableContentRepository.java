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
	public Optional<WageTableContent> getWageTableContentById(String historyId, String companyId,
			String wageTableCode) {
		String queryWagePayment = "SELECT p FROM QpbmtWageTableComboPayment p WHERE p.pk.historyId = :historyId AND p.pk.companyId = :companyId AND p.pk.wageTableCode = :wageTableCode";
		String queryWageQualificationGroup = "SELECT g FROM QpbmtWageTableQualifyGroupSet g WHERE  g.pk.historyId = :historyId AND g.pk.companyId = :companyId AND g.pk.wageTableCode = :wageTableCode";
		List<ElementsCombinationPaymentAmount> listPayment = this.queryProxy()
				.query(queryWagePayment, QpbmtWageTableComboPayment.class).setParameter("historyId", historyId)
				.setParameter("companyId", companyId).setParameter("wageTableCode", wageTableCode)
				.getList(i -> i.toDomain());
		List<QpbmtWageTableQualifyGroupSet> listGroupCodeEntity = this.queryProxy()
				.query(queryWageQualificationGroup, QpbmtWageTableQualifyGroupSet.class)
				.setParameter("companyId", companyId).setParameter("wageTableCode", wageTableCode)
				.setParameter("historyId", historyId).getList();
		if (listPayment.isEmpty() && listGroupCodeEntity.isEmpty())
			return Optional.empty();
		List<QualificationGroupSettingContent> listGroupCode = listGroupCodeEntity.stream()
				.map(i -> QpbmtWageTableQualifyGroupSet.toDomain(i)).collect(Collectors.toList());
		return Optional.of(new WageTableContent(historyId, listPayment,
				listGroupCode.isEmpty() ? Optional.empty() : Optional.of(listGroupCode)));
	}

	@Override
	public void addOrUpdate(WageTableContent domain, String companyId, String wageTableCode) {
		this.remove(domain.getHistoryID(), companyId, wageTableCode);
		List<QpbmtWageTableComboPayment> listPaymentEntity = domain.getPayments().stream()
				.map(i -> new QpbmtWageTableComboPayment(i, domain.getHistoryID(), companyId, wageTableCode))
				.collect(Collectors.toList());
		List<QpbmtWageTableQualifyGroupSet> listGroupSetEntity = new ArrayList<>();
		domain.getQualificationGroupSettings().ifPresent(listSetting -> {
			listSetting.forEach(i -> {
				listGroupSetEntity.add(
						QpbmtWageTableQualifyGroupSet.fromDomain(i, companyId, wageTableCode, domain.getHistoryID()));
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
	public void remove(String historyId, String companyId, String wageTableCode) {
		String queryDeleteWagePayment = "DELETE FROM QpbmtWageTableComboPayment p WHERE  p.pk.historyId = :historyId AND p.pk.companyId = :companyId AND p.pk.wageTableCode = :wageTableCode";
		this.getEntityManager().createQuery(queryDeleteWagePayment).setParameter("historyId", historyId)
				.setParameter("companyId", companyId).setParameter("wageTableCode", wageTableCode).executeUpdate();
		String queryDeleteWageGroupSet = "DELETE FROM QpbmtWageTableQualifyGroupSet g WHERE  g.pk.historyId = :historyId AND g.pk.companyId = :companyId AND g.pk.wageTableCode = :wageTableCode";
		this.getEntityManager().createQuery(queryDeleteWageGroupSet).setParameter("historyId", historyId)
				.setParameter("companyId", companyId).setParameter("wageTableCode", wageTableCode).executeUpdate();
		String queryDeleteWageGroupSetEligibleCode = "DELETE FROM QpbmtWageTableGroupEligibleCode g WHERE  g.pk.historyId = :historyId AND g.pk.companyId = :companyId AND g.pk.wageTableCode = :wageTableCode";
		this.getEntityManager().createQuery(queryDeleteWageGroupSetEligibleCode).setParameter("historyId", historyId)
				.setParameter("companyId", companyId).setParameter("wageTableCode", wageTableCode).executeUpdate();
	}

	@Override
	public List<WageTableQualification> getWageTableQualification(String historyId) {
		String wageTableQualificationSQL =
				" SELECT" +
				"		wcp.pk.id," +
				"		wgs.pk.qualificationGroupCode," +
				"		wgs.qualificationGroupName," +
				"		wgsec.pk.eligibleQualificationCode," +
				"		qi.qualificationName," +
				"		wcp.paymentAmount," +
				"		wgs.paymentMethod" +
				"	FROM" +
				"		QpbmtWageTableComboPayment wcp" +
				"	LEFT JOIN QpbmtWageTableGroupEligibleCode wgsec ON wcp.pk.companyId = wgsec.pk.companyId" +
				"	AND wcp.pk.wageTableCode = wgsec.pk.wageTableCode" +
				"	AND wcp.pk.historyId = wgsec.pk.historyId" +
				"	AND wcp.masterCode1 = wgsec.pk.eligibleQualificationCode" +
				"	LEFT JOIN QpbmtWageTableQualifyGroupSet wgs ON wgs.pk.companyId = wgsec.pk.companyId" +
				"	AND wgs.pk.wageTableCode = wgsec.pk.wageTableCode" +
				"	AND wgs.pk.qualificationGroupCode = wgsec.pk.qualificationGroupCode" +
				"	AND wgs.pk.historyId = wgsec.pk.historyId" +
				"	LEFT JOIN QpbmtQualificationInformation qi ON qi.pk.cid = wgsec.pk.companyId" +
				"	AND qi.pk.qualificationCode = wgsec.pk.eligibleQualificationCode" +
				" WHERE" +
				"     wcp.pk.companyId = :cid AND" +
				"     wcp.pk.historyId = :historyId" +
				" ORDER BY" +
				"     CASE WHEN wgs.pk.qualificationGroupCode = '  ' THEN 2 ELSE 1 END, wgs.pk.qualificationGroupCode, wgsec.pk.eligibleQualificationCode";
		List<Object[]> data = this.queryProxy()
				.query(wageTableQualificationSQL, Object[].class)
				.setParameter("cid", AppContexts.user().companyId())
				.setParameter("historyId", historyId)
				.getList();

		Map<String, List<Object[]>> result = sortByQualificationGroupCode(data);

		return getWageTableQualificationFromDB(result);
	}

	@Override
	public List<WageTableQualification> getDefaultWageTableQualification() {
		String wageTableQualificationSQL
				= "SELECT" +
				"		NULL," +
				"		qgs.pk.qualificationGroupCode," +
				"		qgs.qualificationGroupName," +
				"		qi.pk.qualificationCode," +
				"		qi.qualificationName," +
				"		NULL," +
				"		qgs.paymentMethod" +
				"	FROM" +
				"		QpbmtQualificationInformation qi" +
				"	LEFT JOIN QpbmtEligibleQualificationCode eqc ON qi.pk.cid = eqc.pk.companyId" +
				"	AND qi.pk.qualificationCode = eqc.pk.eligibleQualificationCode" +
				"	LEFT JOIN QpbmtQualificationGroupSetting qgs ON eqc.pk.companyId = qgs.pk.cid" +
				"	AND eqc.pk.qualificationGroupCode = qgs.pk.qualificationGroupCode" +
				" WHERE qi.pk.cid =:cid ORDER BY CASE WHEN qgs.pk.qualificationGroupCode IS NULL THEN 2 ELSE 1 END, qgs.pk.qualificationGroupCode, qi.pk.qualificationCode";
		List<Object[]> data = this.queryProxy()
				.query(wageTableQualificationSQL, Object[].class)
				.setParameter("cid", AppContexts.user().companyId())
				.getList();

		Map<String, List<Object[]>> result = sortByQualificationGroupCode(data);

		return getWageTableQualificationFromDB(result);
	}

	private LinkedHashMap<String, List<Object[]>> sortByQualificationGroupCode(List<Object[]> data) {
		return data.stream().collect(Collectors.groupingBy(x -> String.valueOf(x[1])))
				.entrySet().stream().sorted((o1, o2) -> {
					if(StringUtil.isNullOrEmpty(o1.getKey(), true)) return 1;
					if(StringUtil.isNullOrEmpty(o2.getKey(), true)) return -1;
                    return Integer.compare(o1.getKey().compareTo(o2.getKey()), 0);
                })
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
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

	@Override
	public List<ElementsCombinationPaymentAmount> getWageTableContentByThirdDimension(String historyId,
			String companyId, String wageTableCode, String thirdMasterCode, Integer thirdFrameNumber) {
		String queryWagePayment = "SELECT p FROM QpbmtWageTableComboPayment p WHERE p.pk.historyId = :historyId AND p.pk.companyId = :companyId AND p.pk.wageTableCode = :wageTableCode"
				+ " AND (p.masterCode3 = :masterCode3 OR p.frameNumber3 = :frameNumber3)";
		List<ElementsCombinationPaymentAmount> listPayment = this.queryProxy()
				.query(queryWagePayment, QpbmtWageTableComboPayment.class).setParameter("historyId", historyId)
				.setParameter("companyId", companyId).setParameter("wageTableCode", wageTableCode)
				.setParameter("masterCode3", thirdMasterCode).setParameter("frameNumber3", thirdFrameNumber)
				.getList(i -> i.toDomain());
		return listPayment;
	}

	@Override
	public void updateListPayment(String historyId, String companyId, String wageTableCode,
			List<ElementsCombinationPaymentAmount> payments) {
		String queryWagePayment = "SELECT p FROM QpbmtWageTableComboPayment p WHERE p.pk.historyId = :historyId AND p.pk.companyId = :companyId AND p.pk.wageTableCode = :wageTableCode";
		List<ElementsCombinationPaymentAmount> listPayment = this.queryProxy()
				.query(queryWagePayment, QpbmtWageTableComboPayment.class).setParameter("historyId", historyId)
				.setParameter("companyId", companyId).setParameter("wageTableCode", wageTableCode)
				.getList(i -> i.toDomain());
		for (ElementsCombinationPaymentAmount newPayment : payments) {
			for (ElementsCombinationPaymentAmount oldPayment : listPayment) {
				if (!isElementItemsEqual(newPayment.getElementAttribute().getFirstElementItem(),
						oldPayment.getElementAttribute().getFirstElementItem()))
					continue; // neu first element khac nhau thi bo qua luon
				if (newPayment.getElementAttribute().getSecondElementItem().isPresent()
						&& oldPayment.getElementAttribute().getSecondElementItem().isPresent()) {
					if (!isElementItemsEqual(newPayment.getElementAttribute().getSecondElementItem().get(),
							oldPayment.getElementAttribute().getSecondElementItem().get()))
						continue; // neu second element khac nhau thi bo qua luon
					if (newPayment.getElementAttribute().getThirdElementItem().isPresent()
							&& oldPayment.getElementAttribute().getThirdElementItem().isPresent()) {
						if (isElementItemsEqual(newPayment.getElementAttribute().getThirdElementItem().get(),
								oldPayment.getElementAttribute().getThirdElementItem().get())) {
							newPayment.setId(oldPayment.getId());
							listPayment.remove(oldPayment);
							break;
						}
					} else {
						newPayment.setId(oldPayment.getId());
						listPayment.remove(oldPayment);
						break;
					}
				} else {
					newPayment.setId(oldPayment.getId());
					listPayment.remove(oldPayment);
					break;
				}
			}
		}
		List<QpbmtWageTableComboPayment> listPaymentEntity = payments.stream()
				.map(i -> new QpbmtWageTableComboPayment(i, historyId, companyId, wageTableCode))
				.collect(Collectors.toList());
		this.commandProxy().updateAll(listPaymentEntity);
	}
	
	private boolean isElementItemsEqual(ElementItem item1, ElementItem item2) {
		if (item1.getMasterElementItem().isPresent() && item2.getMasterElementItem().isPresent()) {
			return item1.getMasterElementItem().get().getMasterCode()
					.equals(item2.getMasterElementItem().get().getMasterCode());
		}
		if (item1.getNumericElementItem().isPresent() && item2.getNumericElementItem().isPresent()) {
			if (item1.getNumericElementItem().get().getFrameLowerLimit()
					.compareTo(item2.getNumericElementItem().get().getFrameLowerLimit()) == 0
					&& item1.getNumericElementItem().get().getFrameUpperLimit()
							.compareTo(item2.getNumericElementItem().get().getFrameUpperLimit()) == 0
					&& item1.getNumericElementItem().get().getFrameNumber()
							.compareTo(item2.getNumericElementItem().get().getFrameNumber()) == 0) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

}