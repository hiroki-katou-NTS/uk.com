package nts.uk.cnv.dom.td.event.accept;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.summary.AlterationStatusPolicy;
import nts.uk.cnv.dom.td.event.EventIdProvider;
import nts.uk.cnv.dom.td.event.EventType;
import nts.uk.cnv.dom.td.schema.snapshot.CreateShapShot;

/**
 * 検収する
 * @author ai_muto
 *
 */
public class AcceptService {
	
	public static AcceptedResult accept(Require require, String deliveryEventId, String userName) {
		
		val eventName = require.getEventName(deliveryEventId)
				.orElseThrow(() -> new BusinessException("検収対象がありません。")); 
		val deliveryAlteations= require.getAlterationsByEvent(deliveryEventId);
		val alterationIds = deliveryAlteations.stream().map(alter -> alter.getAlterId()).collect(Collectors.toList());
		
		val errorList = new AlterationStatusPolicy(EventType.ACCEPT).checkError(require, alterationIds);
		if(errorList.size() > 0) {
			return new AcceptedResult(errorList,Optional.empty(),  Optional.empty());
		}
		
		val acceptEvent = AcceptEvent.create(require, eventName, userName, alterationIds);
		return new AcceptedResult(new ArrayList<>(),
			Optional.of(acceptEvent.getEventId().getId()),
			Optional.of(
				AtomTask.of(() -> {
					require.regist(acceptEvent);
					CreateShapShot.create(require, acceptEvent.getEventId().getId(), deliveryAlteations);
				}
			)));
	}

	public interface Require extends AlterationStatusPolicy.Require,
														EventIdProvider.ProvideAcceptIdRequire,
														CreateShapShot.Require{
		Optional<String> getEventName(String deliveryEventId);
		List<Alteration> getAlterationsByEvent(String deliveryEventId);
		void regist(AcceptEvent create);
	}
}
