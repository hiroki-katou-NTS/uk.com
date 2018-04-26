package nts.uk.ctx.sys.assist.infra.repository.storage;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveRepository;
import nts.uk.ctx.sys.assist.infra.entity.storage.SspmtManualSetOfDataSave;

@Stateless
public class JpaManualSetOfDataSaveRepository extends JpaRepository implements ManualSetOfDataSaveRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspmtManualSetOfDataSave f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.manualSetOfDataSavePk.cid =:cid AND  f.manualSetOfDataSavePk.storeProcessingId =:storeProcessingId ";

	@Override
	public List<ManualSetOfDataSave> getAllManualSetOfDataSave() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspmtManualSetOfDataSave.class)
				.getList(item -> toDomain(item));
	}

	private ManualSetOfDataSave toDomain(SspmtManualSetOfDataSave entity) {
		return new ManualSetOfDataSave(entity.manualSetOfDataSavePk.cid, entity.manualSetOfDataSavePk.storeProcessingId,
				entity.systemType, entity.passwordAvailability, entity.saveSetName, entity.referenceDate,
				entity.compressedPassword, entity.executionDateAndTime, entity.daySaveEndDate, entity.daySaveStartDate,
				entity.monthSaveEndDate, entity.monthSaveStartDate, entity.suppleExplanation, entity.endYear,
				entity.startYear, entity.presenceOfEmployee, entity.identOfSurveyPre, entity.practitioner);
	}

	@Override
	public Optional<ManualSetOfDataSave> getManualSetOfDataSaveById(String cid, String storeProcessingId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspmtManualSetOfDataSave.class).setParameter("cid", cid)
				.setParameter("storeProcessingId", storeProcessingId).getSingle(c -> toDomain(c));
	}

}
