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
 * 納品する
 * @author ai_muto
 *
 */
@Stateless
public class DeliveryService {
	public DeliveredResult delivery(Require require, String featureId, EventMetaData meta, List<String> alterations) {

		List<AlterationSummary> alterSummares = require.getAllUndeliveled(featureId);

		boolean allUndeliveled = alterations.stream()
				.allMatch(alt -> alterSummares.contains(alt));
		if(!allUndeliveled) {
			throw new BusinessException( new RawErrorMessage(
					"指定されたorutaは選択できません。納品済または別Featureのorutaの可能性があります"));
		}

		// この制御は発注に限らずある…？
		List<AlterationSummary> errorList = new ArrayList<>();
		alterSummares.forEach(alterSummary -> {
			errorList.addAll(require.getOlderUndeliveled(alterSummary.getAlterId()));
		});

		if(errorList.size() > 0) {
			return new DeliveredResult(errorList, Optional.empty());
		}

		return new DeliveredResult(errorList,
			Optional.of(
				AtomTask.of(() -> {
					require.regist(DeliveryEvent.create(require, meta, alterations));
				}
			)));
	}

	public interface Require extends EventIdProvider.ProvideDeliveryIdRequire{
		List<AlterationSummary> getAllUndeliveled(String featureId);
		List<AlterationSummary> getOlderUndeliveled(String alterId);
		void regist(DeliveryEvent create);
	}
}
