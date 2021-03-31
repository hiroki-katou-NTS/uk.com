package nts.uk.cnv.dom.td.event.accept;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
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
		AtomTask alterSummaries = getAbleAcceptAlters(alterationSummary);
		
		val alterationIds = alterationSummary.stream().map(alter -> alter.getAlterId()).collect(Collectors.toList());
		
		val errorList = new EventPolicy(DevelopmentStatus.ACCEPTED).check(require, alterationSummary);
		if(errorList.size() > 0) {
			return new AcceptedResult(errorList,Optional.empty(),  Optional.empty());
		}
		
		val acceptEvent = AcceptEvent.create(require, null, userName, alterationIds);
		return new AcceptedResult(new ArrayList<>(),
			Optional.of(acceptEvent.getEventId().getId()),
			Optional.of(
				alterSummaries.then(() ->{
					AtomTask.of(() -> {
						require.regist(acceptEvent);
						CreateShapshot.create(require, acceptEvent.getEventId().getId(), alterationIds);
					});						
				})
			));
	}
	
	private static AtomTask getAbleAcceptAlters(List<AlterationSummary> alterSummaries) {
		List<AlterationSummary> ableAcceptAlterSummaries = new ArrayList<>(); 
		return AtomTask.of(() ->{
			ableAcceptAlterSummaries.addAll(alterSummaries.stream()
					.filter(alter -> alter.getState() == DevelopmentStatus.DELIVERED)
					.collect(Collectors.toList()));
			if(ableAcceptAlterSummaries.isEmpty())
				throw new BusinessException(new RawErrorMessage("検収できるものがありません。"));
		});
	}
	

	public interface Require extends AcceptEvent.Require,
									 CreateShapshot.Require, 
									 EventPolicy.Require{
		List<AlterationSummary> getByEvent(String deliveryEventId, DevelopmentProgress progress);
		void regist(AcceptEvent create);
	}
}
