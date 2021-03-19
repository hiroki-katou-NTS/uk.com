package nts.uk.cnv.dom.td.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;

/**
 * 発注する
 * @author ai_muto
 *
 */
@Stateless
public class OrderService {
	public OrderedResult order(Require require, String featureId, EventMetaData meta, List<String> alterations) {

		List<AlterationSummary> alterSummares = require.getByFeature(featureId, DevelopmentProgress.ordered());

		boolean allUndeliveled = alterations.stream()
				.allMatch(altId -> alterSummares.stream().anyMatch(altSum -> altSum.getAlterId().equals(altId)));
		if(!allUndeliveled) {
			throw new BusinessException( new RawErrorMessage(
					"指定されたorutaは選択できません。発注済または別Featureのorutaの可能性があります"));
		}

		List<AlterationSummary> errorList = new ArrayList<>();
		alterSummares.forEach(alterSummary -> {
			List<AlterationSummary> olderOtherSum =
				require.getByTable(alterSummary.getTableId(), DevelopmentProgress.notOrdered()).stream()
				.filter(sum -> !alterations.contains(sum.getAlterId()))
				.collect(Collectors.toList());
			errorList.addAll(olderOtherSum);
		});

		if(errorList.size() > 0) {
			return new OrderedResult(errorList, Optional.empty());
		}

		return new OrderedResult(errorList,
			Optional.of(
				AtomTask.of(() -> {
					require.regist(OrderEvent.create(require, meta, alterations));
				}
			)));
	}

	public interface Require extends EventIdProvider.ProvideOrderIdRequire{
		List<AlterationSummary> getByFeature(String feature, DevelopmentProgress devProgress);
		List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress);
		void regist(OrderEvent create);
	}
}
