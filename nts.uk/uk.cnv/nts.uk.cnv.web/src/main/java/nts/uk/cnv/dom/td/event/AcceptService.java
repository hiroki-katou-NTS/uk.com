package nts.uk.cnv.dom.td.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;

/**
 * 検収する
 * @author ai_muto
 *
 */
@Stateless
public class AcceptService {
	public AcceptedResult accept(Require require, String deliveryEventId, String eventName, String userName) {
		val deliverySummares= require.getEvent(deliveryEventId, DevelopmentProgress.deliveled());
		if(deliverySummares.isEmpty()) throw new RuntimeException("検収できるものが1つも存在しません。");
		
		String featureId = deliverySummares.stream().findFirst().get().getFeatureId(); 
		val alterations = deliverySummares.stream().map(alter -> alter.getAlterId()).collect(Collectors.toList());
		
		List<AlterationSummary> alterSummares = require.getByFeature(featureId, DevelopmentProgress.accepted());
		boolean allUnaccepted = alterations.stream()
				.allMatch(alt -> alterSummares.stream().anyMatch( altSum -> altSum.getAlterId().equals(alt)));
		if(!allUnaccepted) {
			throw new BusinessException( new RawErrorMessage(
					"指定されたorutaは選択できません。検収済または別Featureのorutaの可能性があります"));
		}

		// この制御は発注に限らずある…？
		List<AlterationSummary> errorList = new ArrayList<>();
		alterSummares.forEach(alterSummary -> {
			errorList.addAll(require.getByTable(alterSummary.getTableId(), DevelopmentProgress.notAccepted()));
		});

		if(errorList.size() > 0) {
			return new AcceptedResult(errorList, Optional.empty());
		}

		return new AcceptedResult(errorList,
			Optional.of(
				AtomTask.of(() -> {
					val acceptEvent = AcceptEvent.create(require, eventName, userName, alterations);
					require.regist(acceptEvent);
					require.regist(new SchemaSnapshot("", 
							GeneralDateTime.now(), 
							acceptEvent.getEventId().toString()));
				}
			)));
	}

	public interface Require extends EventIdProvider.ProvideAcceptIdRequire{
		List<AlterationSummary> getEvent(String deliveryEventId, DevelopmentProgress devProgress);
		List<AlterationSummary> getByFeature(String featureId, DevelopmentProgress devProgress);
		List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress);
		void regist(AcceptEvent create);
		void regist(SchemaSnapshot snapShot);
	}
}
