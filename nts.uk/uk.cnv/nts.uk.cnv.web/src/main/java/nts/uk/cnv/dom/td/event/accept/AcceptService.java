package nts.uk.cnv.dom.td.event.accept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.summary.AlterationStatusPolicy;
import nts.uk.cnv.dom.td.event.EventIdProvider;
import nts.uk.cnv.dom.td.event.EventType;
import nts.uk.cnv.dom.td.schema.snapshot.CreateShapshot;

/**
 * 検収する
 * @author ai_muto
 *
 */
public class AcceptService {
	
	public static AcceptedResult accept(Require require, String deliveryEventId, String userName) {
		val alterationSummary = require.getByEvent(deliveryEventId);
		val ableAcceptAlterSummaries =  Collections.EMPTY_LIST;
//		val ableAcceptAlterSummaries = alterationSummary.stream().filter(alter -> alter.getState() == DevelopmentStatus.DELIVERED).collect(Collectors.toList());
		if(ableAcceptAlterSummaries.isEmpty())
			throw new BusinessException(new RawErrorMessage("検収できるものがありません。"));
		
		val alterationIds = alterationSummary.stream().map(alter -> alter.getAlterId()).collect(Collectors.toList());

		
		val errorList = new AlterationStatusPolicy(EventType.ACCEPT).checkError(require, alterationIds);
		if(errorList.size() > 0) {
			return new AcceptedResult(errorList,Optional.empty(),  Optional.empty());
		}
		
		val acceptEvent = AcceptEvent.create(require, null, userName, alterationIds);
		return new AcceptedResult(new ArrayList<>(),
			Optional.of(acceptEvent.getEventId().getId()),
			Optional.of(
				AtomTask.of(() -> {
					require.regist(acceptEvent);
					CreateShapshot.create(require, acceptEvent.getEventId().getId(), alterationIds);
				}
			)));
	}

	public interface Require extends AlterationStatusPolicy.Require,
														EventIdProvider.ProvideAcceptIdRequire,
														CreateShapshot.Require{
		List<AlterationSummary> getByEvent(String deliveryEventId);
		List<Alteration> getAlterationsByEvent(String deliveryEventId);
		void regist(AcceptEvent create);
	}
}
