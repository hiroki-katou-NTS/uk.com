package nts.uk.cnv.dom.td.event.delivery;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.event.EventIdProvider;
import nts.uk.cnv.dom.td.event.EventPolicy;
import nts.uk.cnv.dom.td.event.EventType;

/**
 * 納品する
 * @author ai_muto
 *
 */
public class DeliveryService {
	public static DeliveredResult delivery(Require require, String featureId, String eventName, String userName, List<String> alterations) {

		val errorList = new EventPolicy(EventType.DELIVER)
				.checkError(require, alterations);
		
		// 納品できない
		if(errorList.size() > 0) {
			return new DeliveredResult(errorList, Optional.empty());
		}

		// 納品できる
		return new DeliveredResult(errorList,
			Optional.of(
				AtomTask.of(() -> {
					require.regist(DeliveryEvent.create(require, eventName, userName, alterations));
				}
			)));
	}

	public interface Require extends EventIdProvider.ProvideDeliveryIdRequire, 
									 EventPolicy.Require{
		void regist(DeliveryEvent create);
	}
}
