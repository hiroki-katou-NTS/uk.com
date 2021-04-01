package nts.uk.cnv.dom.td.event.order;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;
import nts.uk.cnv.dom.td.event.EventIdProvider;
import nts.uk.cnv.dom.td.event.EventPolicy;

/**
 * 発注する
 * @author ai_muto
 *
 */
public class OrderService {
	public static OrderedResult order(Require require, String featureId, String eventName, String userName, List<String> alterIds) {


		val targetAlters = require.getByAlter(alterIds);
		val errorList = new EventPolicy(DevelopmentStatus.ORDERED)
				.check(require, targetAlters);

		// 発注できない
		if(errorList.size() > 0) {
			return new OrderedResult(Optional.empty(), errorList, Optional.empty());
		}

		val orderEvent = OrderEvent.create(require, eventName, userName, alterIds);

		// 発注できる
		return new OrderedResult(
			Optional.of(orderEvent.getEventId().getId()),
			errorList,
			Optional.of(
				AtomTask.of(() -> {
					require.regist(orderEvent);
				}
			)));
	}

	public interface Require extends EventIdProvider.ProvideOrderIdRequire,
									 EventPolicy.Require {
		List<AlterationSummary> getByAlter(List<String> alterIds);
		void regist(OrderEvent create);
	}
}
