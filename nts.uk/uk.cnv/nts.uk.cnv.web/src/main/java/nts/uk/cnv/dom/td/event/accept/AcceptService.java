package nts.uk.cnv.dom.td.event.accept;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;
import nts.uk.cnv.dom.td.event.EventPolicy;
import nts.uk.cnv.dom.td.schema.snapshot.CreateShapshot;

/**
 * 検収する
 * @author ai_muto
 *
 */
public class AcceptService {
	
	public static AcceptedResult accept(Require require, String deliveryEventId, String userName) {
		val alterationSummary = require.getByEvent(deliveryEventId, DevelopmentProgress.deliveled());

		val errorList = new EventPolicy(DevelopmentStatus.ACCEPTED).check(require, alterationSummary);
		if(errorList.size() > 0) {
			return new AcceptedResult(errorList,Optional.empty(),  Optional.empty());
		}
		val alterationIds = alterationSummary.stream().map(alter -> alter.getAlterId()).collect(Collectors.toList());
		val acceptEvent = AcceptEvent.create(require, null, userName, alterationIds);
		
		return new AcceptedResult(new ArrayList<>(),
			Optional.of(acceptEvent.getEventId().getId()),
			Optional.of(
					AtomTask.of(() -> {
						require.regist(acceptEvent);
					}).then(
						CreateShapshot.create(require, acceptEvent.getEventId().getId(), alterationIds)
					)
			));
	}
	
	public interface Require extends AcceptEvent.Require,
									 CreateShapshot.Require, 
									 EventPolicy.Require{
		List<AlterationSummary> getByEvent(String deliveryEventId, DevelopmentProgress progress);
		void regist(AcceptEvent create);
	}
}
