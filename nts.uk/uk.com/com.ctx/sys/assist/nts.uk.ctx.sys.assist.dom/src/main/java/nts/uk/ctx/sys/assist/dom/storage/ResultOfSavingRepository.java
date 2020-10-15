package nts.uk.ctx.sys.assist.dom.storage;

import java.util.List;
import java.util.Optional;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * データ保存の保存結果
 */
public interface ResultOfSavingRepository {

	List<ResultOfSaving> getAllResultOfSaving();
	
	List<ResultOfSaving> getResultOfSaving(
		 String cid,
		 GeneralDateTime startDateOperator,
		 GeneralDateTime endDateOperator,
		 List<String>  listOperatorEmployeeId
	);

	Optional<ResultOfSaving> getResultOfSavingById(String storeProcessingId);

	void add(ResultOfSaving data);

	void update(String storeProcessingId, int targetNumberPeople, SaveStatus saveStatus, String fileId,
			NotUseAtr deletedFiles, String compressedFileName);

	void update(String storeProcessingId, Optional<Integer> targetNumberPeople, Optional<SaveStatus> saveStatus);
	
	void update(ResultOfSaving data);
	
	void update(String storeProcessingId, long fileSize);
}
