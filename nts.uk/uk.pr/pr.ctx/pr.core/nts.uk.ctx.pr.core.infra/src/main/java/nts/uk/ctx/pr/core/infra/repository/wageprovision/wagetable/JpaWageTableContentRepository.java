package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementsCombinationPaymentAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSettingContent;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableContent;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableContentRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTableComboPayment;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTableQualifyGroupSet;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaWageTableContentRepository extends JpaRepository implements WageTableContentRepository {

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

}
