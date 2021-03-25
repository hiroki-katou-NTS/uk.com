package nts.uk.cnv.dom.td.event.accept;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.event.EventIdProvider;
import nts.uk.cnv.dom.td.event.EventIdProvider.ProvideAcceptIdRequire;
import nts.uk.cnv.dom.td.schema.snapshot.CreateShapShot;
import nts.uk.cnv.dom.td.schema.snapshot.CreateShapShot;

/**
 * 検収する
 * @author ai_muto
 *
 */
@Stateless
public class AcceptService {
	
	public AcceptedResult accept(Require require, String deliveryEventId, String userName) {
		
		val eventName = require.getEventName(deliveryEventId).orElseThrow(() -> new BusinessException("検収対象がありません。")); 

		val deliveryAlteations= require.getAlterationsByEvent(deliveryEventId);
		
		val alterationIds = deliveryAlteations.stream().map(alter -> alter.getAlterId()).collect(Collectors.toList());
		
		List<AlterationSummary> errorList = getErrorList(require, deliveryAlteations, alterationIds);
		if(errorList.size() > 0) {
			return new AcceptedResult(errorList, Optional.empty());
		}
		val acceptEvent = AcceptEvent.create(require, eventName, userName, alterationIds);
		return new AcceptedResult(errorList,
			Optional.of(
				AtomTask.of(() -> {
					require.regist(acceptEvent);
					CreateShapShot.create(require, acceptEvent.getEventId().getId(), deliveryAlteations);
				}
			)));
	}

	private List<AlterationSummary> getErrorList(Require require,List<Alteration> deliveryAlteations,  List<String> alterationIds) {
		String featureId = deliveryAlteations.stream().findFirst().get().getFeatureId(); 
		List<AlterationSummary> alterSummares = require.getByFeature(featureId, DevelopmentProgress.accepted());
		boolean allUnaccepted = alterationIds.stream()
				.allMatch(alt -> alterSummares.stream().anyMatch( altSum -> altSum.getAlterId().equals(alt)));
		if(!allUnaccepted) {
			throw new BusinessException( new RawErrorMessage(
					"指定されたorutaは選択できません。検収済または別Featureのorutaの可能性があります"));
		}

		// この制御は発注に限らずある…？
		List<AlterationSummary> errorList = new ArrayList<>();
		alterSummares.forEach(alterSummary -> {
			errorList.addAll(require.getByTable(alterSummary.getTableId(), DevelopmentProgress.notDeliveled()));
		});
		return errorList;
	}

	public interface Require extends EventIdProvider.ProvideAcceptIdRequire,
															CreateShapShot.Require{
		Optional<String> getEventName(String deliveryEventId);
		List<Alteration> getAlterationsByEvent(String deliveryEventId);
		List<AlterationSummary> getByFeature(String featureId, DevelopmentProgress devProgress);
		List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress);
		void regist(AcceptEvent create);
	}
}
