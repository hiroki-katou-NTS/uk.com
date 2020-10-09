package nts.uk.ctx.sys.assist.infra.repository.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.gul.security.crypt.commonkey.CommonKeyCrypt;
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
	private static final String SELECT_WITH_NULL_LIST_EMPLOYEE = SELECT_ALL_QUERY_STRING
				+ " WHERE f.cid =:cid "
				+ " AND f.saveStartDatetime >=:startDateOperator "
				+ " AND f.saveStartDatetime <=:endDateOperator ";
	
	private static final String SELECT_WITH_NOT_NULL_LIST_EMPLOYEE = SELECT_ALL_QUERY_STRING
				+ " WHERE f.cid =:cid "
				+ " AND f.saveStartDatetime =:startDateOperator "
				+ " AND f.saveStartDatetime =:endDateOperator "
				+ " AND f.practitioner IN :practitioner ";
	
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
		SspmtResultOfSaving entity = SspmtResultOfSaving.toEntity(data);
		String password = data.getCompressedPassword().map(i -> i.v()).orElse("");
		entity.compressedPassword = StringUtils.isNotEmpty(password) ? CommonKeyCrypt.encrypt(password) : null;
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(String storeProcessingId, int targetNumberPeople, SaveStatus saveStatus, String fileId,
			NotUseAtr deletedFiles, String compressedFileName) {
		Optional<SspmtResultOfSaving> resultOfSavingOpt = this.queryProxy().find(storeProcessingId, SspmtResultOfSaving.class);
		resultOfSavingOpt.ifPresent(data -> {
			data.targetNumberPeople = targetNumberPeople;
			data.saveStatus = saveStatus.value;
			data.fileId = fileId;
			data.deletedFiles = deletedFiles.value;
			data.saveFileName = compressedFileName;
			this.commandProxy().update(data);
		});
	}

	@Override
	public void update(String storeProcessingId, Optional<Integer> targetNumberPeople, Optional<SaveStatus> saveStatus) {
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

	@Override
	public void update(String storeProcessingId, long fileSize) {
		Optional<SspmtResultOfSaving> resultOfSavingOpt = this.queryProxy().find(storeProcessingId, SspmtResultOfSaving.class);
		resultOfSavingOpt.ifPresent(data -> {
			data.fileSize = fileSize;
			this.commandProxy().update(data);
		});
		
	}

	@Override
	public List<ResultOfSaving> getResultOfSaving(
			String cid,
			GeneralDateTime startDateOperator, 
			GeneralDateTime endDateOperator, 
			List<String> listOperatorEmployeeId) {
		
		List<ResultOfSaving> resultOfSavings = new ArrayList<ResultOfSaving>();
		
		if (!CollectionUtil.isEmpty(listOperatorEmployeeId)) {
			resultOfSavings.addAll(
					this.queryProxy().query(SELECT_WITH_NOT_NULL_LIST_EMPLOYEE, SspmtResultOfSaving.class)
					.setParameter("cid", cid)
					.setParameter("startDateOperator", startDateOperator)
					.setParameter("endDateOperator", endDateOperator)
					.setParameter("practitioner", listOperatorEmployeeId)
					.getList(item -> item.toDomain()));
		} else {
			resultOfSavings.addAll(
					this.queryProxy().query(SELECT_WITH_NULL_LIST_EMPLOYEE, SspmtResultOfSaving.class)
					.setParameter("cid", cid)
					.setParameter("startDateOperator", startDateOperator)
					.setParameter("endDateOperator", endDateOperator)
					.getList(item -> item.toDomain()));
		}
		return resultOfSavings;
	}
}
