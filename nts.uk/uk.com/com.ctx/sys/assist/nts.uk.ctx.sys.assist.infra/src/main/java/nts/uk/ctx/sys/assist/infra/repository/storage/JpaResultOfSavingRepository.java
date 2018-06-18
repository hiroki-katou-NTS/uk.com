package nts.uk.ctx.sys.assist.infra.repository.storage;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSaving;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSavingRepository;
import nts.uk.ctx.sys.assist.dom.storage.SaveStatus;
import nts.uk.ctx.sys.assist.infra.entity.storage.SspmtResultOfSaving;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class JpaResultOfSavingRepository extends JpaRepository implements ResultOfSavingRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspmtResultOfSaving f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.storeProcessingId =:storeProcessingId ";

	@Override
	public List<ResultOfSaving> getAllResultOfSaving() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspmtResultOfSaving.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ResultOfSaving> getResultOfSavingById(String storeProcessingId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspmtResultOfSaving.class)
				.setParameter("storeProcessingId", storeProcessingId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(ResultOfSaving data) {
		this.commandProxy().insert(SspmtResultOfSaving.toEntity(data));

	}

	@Override
	public void update(String storeProcessingId, int targetNumberPeople, SaveStatus saveStatus, String fileId,
			NotUseAtr deletedFiles) {
		Optional<ResultOfSaving> resultOfSavingOpt = this.getResultOfSavingById(storeProcessingId);
		resultOfSavingOpt.ifPresent(data -> {
			data.setTargetNumberPeople(targetNumberPeople);
			data.setSaveStatus(saveStatus);
			data.setFileId(fileId);
			data.setDeletedFiles(deletedFiles);
			this.commandProxy().update(SspmtResultOfSaving.toEntity(data));
		});
	}

	@Override
	public void update(String storeProcessingId, int targetNumberPeople, SaveStatus saveStatus) {
		Optional<ResultOfSaving> resultOfSavingOpt = this.getResultOfSavingById(storeProcessingId);
		resultOfSavingOpt.ifPresent(data -> {
			data.setTargetNumberPeople(targetNumberPeople);
			data.setSaveStatus(saveStatus);
			this.commandProxy().update(SspmtResultOfSaving.toEntity(data));
		});
	}

	@Override
	public void update(ResultOfSaving data) {
		this.commandProxy().update(SspmtResultOfSaving.toEntity(data));
	}
}
