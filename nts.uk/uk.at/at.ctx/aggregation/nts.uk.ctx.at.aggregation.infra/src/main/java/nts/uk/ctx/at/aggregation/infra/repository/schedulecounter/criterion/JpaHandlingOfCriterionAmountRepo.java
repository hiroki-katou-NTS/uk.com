package nts.uk.ctx.at.aggregation.infra.repository.schedulecounter.criterion;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmountRepository;
import nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion.KagmtHandlingOfCriterionAmount;

/**
 * 
 * @author TU-TK
 *
 */
@Stateless
public class JpaHandlingOfCriterionAmountRepo extends JpaRepository implements HandlingOfCriterionAmountRepository {

	private static final String GET_BY_CID = "SELECT a FROM KagmtHandlingOfCriterionAmount a WHERE a.pk.companyId = :companyId ";

	@Override
	public void insert(String cid, HandlingOfCriterionAmount handling) {
		this.commandProxy().insertAll(KagmtHandlingOfCriterionAmount.toEntity(cid, handling));
	}

	@Override
	public void update(String cid, HandlingOfCriterionAmount handling) {
		List<KagmtHandlingOfCriterionAmount> dataOld = this.queryProxy()
				.query(GET_BY_CID, KagmtHandlingOfCriterionAmount.class).setParameter("companyId", cid).getList();
		List<KagmtHandlingOfCriterionAmount> dataNew = KagmtHandlingOfCriterionAmount.toEntity(cid, handling);
		for (KagmtHandlingOfCriterionAmount temp : dataOld) {
			dataNew.stream().forEach(c -> {
				if (c.pk.companyId == temp.pk.companyId && c.pk.frameNo == temp.pk.frameNo) {
					temp.color = c.color;
				}
			});
		}

		if (dataNew.size() > dataOld.size()) {
			val oldNos = dataOld.stream().map(c -> c.pk.frameNo).collect(Collectors.toList());
			val newEnt = dataNew.stream().filter(c -> !oldNos.contains(c.pk.frameNo)).collect(Collectors.toList());
			dataOld.addAll(newEnt);
		} else if (dataNew.size() < dataOld.size()) {
			val newNos = dataNew.stream().map(c -> c.pk.frameNo).collect(Collectors.toList());
			dataOld.removeIf(x -> !newNos.contains(x.pk.frameNo));
		}
	}

	@Override
	public boolean exist(String cid) {
		return this.get(cid).isPresent();
	}

	@Override
	public Optional<HandlingOfCriterionAmount> get(String cid) {
		List<KagmtHandlingOfCriterionAmount> result = this.queryProxy()
				.query(GET_BY_CID, KagmtHandlingOfCriterionAmount.class)
				.setParameter("companyId", cid).getList();
		if (result.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(KagmtHandlingOfCriterionAmount.toDomain(result));
	}

}
