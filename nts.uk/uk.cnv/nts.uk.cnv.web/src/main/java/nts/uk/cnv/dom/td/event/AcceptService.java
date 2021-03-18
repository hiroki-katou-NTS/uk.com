package nts.uk.cnv.dom.td.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;

/**
 * 検収する
 * @author ai_muto
 *
 */
@Stateless
public class AcceptService {
	public AcceptedResult accept(Require require, String featureId, EventMetaData meta, List<String> alterations) {

		List<AlterationSummary> alterSummares = require.getByFeature(featureId, DevelopmentProgress.accepted());

		boolean allUnaccepted = alterations.stream()
				.allMatch(alt -> alterSummares.contains(alt));
		if(!allUnaccepted) {
			throw new BusinessException( new RawErrorMessage(
					"指定されたorutaは選択できません。検収済または別Featureのorutaの可能性があります"));
		}

		// この制御は発注に限らずある…？
		List<AlterationSummary> errorList = new ArrayList<>();
		alterSummares.forEach(alterSummary -> {
			errorList.addAll(require.getByTable(alterSummary.getTableIdentity().getTableId(), DevelopmentProgress.notAccepted()));
		});

		if(errorList.size() > 0) {
			return new AcceptedResult(errorList, Optional.empty());
		}

		return new AcceptedResult(errorList,
			Optional.of(
				AtomTask.of(() -> {
					require.regist(AcceptEvent.create(require, meta, alterations));
				}
			)));
	}

	public interface Require extends EventIdProvider.ProvideAcceptIdRequire{
		List<AlterationSummary> getByFeature(String featureId, DevelopmentProgress devProgress);
		List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress);
		void regist(AcceptEvent create);
	}
}
