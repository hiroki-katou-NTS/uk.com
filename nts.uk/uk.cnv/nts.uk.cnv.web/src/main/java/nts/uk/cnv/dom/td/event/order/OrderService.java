package nts.uk.cnv.dom.td.event.order;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.event.EventIdProvider;
import nts.uk.cnv.dom.td.event.EventPolicy;
import nts.uk.cnv.dom.td.event.EventType;

/**
 * 発注する
 * @author ai_muto
 *
 */
public class OrderService {
	public static OrderedResult order(Require require, String featureId, String eventName, String userName, List<String> alterations) {

		val errorList = new EventPolicy(EventType.ORDER)
				.checkError(require, alterations);

		// 発注できない
		if(errorList.size() > 0) {
			return new OrderedResult(errorList, Optional.empty());
		}

		// 発注できる
		return new OrderedResult(errorList,
			Optional.of(
				AtomTask.of(() -> {
					require.regist(OrderEvent.create(require, eventName, userName, alterations));
				}
			)));
	}

	public interface Require extends EventIdProvider.ProvideOrderIdRequire, 
									 EventPolicy.Require {
		void regist(OrderEvent create);
	}
}
