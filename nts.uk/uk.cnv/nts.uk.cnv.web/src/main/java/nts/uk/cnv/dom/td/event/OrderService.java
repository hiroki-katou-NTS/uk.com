package nts.uk.cnv.dom.td.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;

/**
 * 発注する
 * @author ai_muto
 *
 */
@Stateless
public class OrderService {
	public OrderedResult order(Require require, String featureId, EventMetaData meta, List<String> alterations) {

		List<AlterationSummary> alterSummares = require.getAllUndeliveled(featureId);

		boolean allUndeliveled = alterations.stream()
				.allMatch(alt -> alterSummares.contains(alt));
		if(!allUndeliveled) {
			throw new BusinessException( new RawErrorMessage(
					"指定されたorutaは選択できません。発注済または別Featureのorutaの可能性があります"));
		}

		List<AlterationSummary> errorList = new ArrayList<>();
		alterSummares.forEach(alterSummary -> {
			errorList.addAll(require.getOlderUndeliveled(alterSummary.getAlterId()));
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
		List<AlterationSummary> getAllUndeliveled(String feature);
		List<AlterationSummary> getOlderUndeliveled(String alterId);
		void regist(OrderEvent create);
	}
}
