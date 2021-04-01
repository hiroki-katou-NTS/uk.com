package nts.uk.cnv.dom.td.event.delivery;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;
import nts.uk.cnv.dom.td.event.EventIdProvider;
import nts.uk.cnv.dom.td.event.EventPolicy;

/**
 * 納品する
 * @author ai_muto
 *
 */
public class DeliveryService {
	public static DeliveredResult delivery(Require require, String featureId, String eventName, String userName, List<String> alterIds) {

		val targetAlters = require.getByAlter(alterIds);
		val errorList = new EventPolicy(DevelopmentStatus.DELIVERED)
				.check(require, targetAlters);

		// 納品できない
		if(errorList.size() > 0) {
			return new DeliveredResult(Optional.empty(), errorList, Optional.empty());
		}

		val deliveryEvent = DeliveryEvent.create(require, eventName, userName, alterIds);

		// 納品できる
		return new DeliveredResult(
			Optional.of(deliveryEvent.getEventId().getId()),
			errorList,
			Optional.of(
				AtomTask.of(() -> {
					require.regist(deliveryEvent);
				}
			)));
	}

	public interface Require extends EventIdProvider.ProvideDeliveryIdRequire,
									 EventPolicy.Require{
		List<AlterationSummary> getByAlter(List<String> alterIds);
		void regist(DeliveryEvent create);
	}
}
