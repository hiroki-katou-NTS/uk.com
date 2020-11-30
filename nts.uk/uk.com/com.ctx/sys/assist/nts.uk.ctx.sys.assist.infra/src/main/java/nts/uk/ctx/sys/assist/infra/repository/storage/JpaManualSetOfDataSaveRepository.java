package nts.uk.ctx.sys.assist.infra.repository.storage;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.security.crypt.commonkey.CommonKeyCrypt;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveRepository;
import nts.uk.ctx.sys.assist.infra.entity.storage.SspmtManualSetOfDataSave;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class JpaManualSetOfDataSaveRepository extends JpaRepository implements ManualSetOfDataSaveRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspmtManualSetOfDataSave f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.cid =:cid AND  f.storeProcessingId =:storeProcessingId ";
	private static final String SELECT_BY_KEY_STRING_STORE = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.storeProcessingId =:storeProcessingId ";

	@Override
	public List<ManualSetOfDataSave> getAllManualSetOfDataSave() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspmtManualSetOfDataSave.class)
				.getList(item -> toDomain(item));
	}

	private ManualSetOfDataSave toDomain(SspmtManualSetOfDataSave entity) {
		return new ManualSetOfDataSave(entity.cid, entity.storeProcessingId, entity.passwordAvailability,
				entity.saveSetName, entity.referenceDate, entity.compressedPassword, entity.executionDateAndTime,
				entity.daySaveEndDate, entity.daySaveStartDate,
				entity.monthSaveEndDate, entity.monthSaveStartDate, entity.suppleExplanation, entity.endYear,
				entity.startYear, entity.presenceOfEmployee, entity.practitioner, 1);
	}

	private SspmtManualSetOfDataSave toEntity(ManualSetOfDataSave dom) {
		return new SspmtManualSetOfDataSave(dom.getCid(), dom.getStoreProcessingId(),
				dom.getPasswordAvailability().value, dom.getSaveSetName().v(), dom.getReferenceDate(),
				(dom.getCompressedPassword() != null && dom.getPasswordAvailability() == NotUseAtr.USE)
						? CommonKeyCrypt.encrypt(dom.getCompressedPassword().v())
						: null,
				dom.getExecutionDateAndTime(), dom.getDaySaveEndDate(), dom.getDaySaveStartDate(),
				dom.getMonthSaveEndDate(), dom.getMonthSaveStartDate(),
				dom.getSuppleExplanation(), dom.getEndYear().isPresent() ? dom.getEndYear().get().v() : null,
				dom.getStartYear().isPresent() ? dom.getStartYear().get().v() : null, dom.getPresenceOfEmployee().value,
				dom.getPractitioner());
	}

	@Override
	public Optional<ManualSetOfDataSave> getManualSetOfDataSaveById(String cid, String storeProcessingId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspmtManualSetOfDataSave.class).setParameter("cid", cid)
				.setParameter("storeProcessingId", storeProcessingId).getSingle(c -> toDomain(c));
	}

	@Override
	public void addManualSetting(ManualSetOfDataSave domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	@Override
	public Optional<ManualSetOfDataSave> getManualSetOfDataSaveById(String storeProcessingId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING_STORE, SspmtManualSetOfDataSave.class)
				.setParameter("storeProcessingId", storeProcessingId).getSingle(c -> toDomain(c));
	}

}
