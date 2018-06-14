package nts.uk.ctx.sys.assist.infra.repository.datarestoration;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoverySelection;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoverySelectionRepository;

public class JpaDataRecoverySelectionRepository extends JpaRepository implements DataRecoverySelectionRepository {

	private final String SELECT_FILE_RECOVERY_SELECTION_SAVE = "SELECT r.saveSetCode, r.saveName, m.suppleExplanation, r.saveStartDatetime, r.saveForm, "
			+ "r.targetNumberPeople, r.saveFileName, r.fileId FROM SspmtResultOfSaving  r "
			+ "INNER JOIN SspmtManualSetOfDataSave m on r.storeProcessingId = m.storeProcessingId "
			+ "where r.cid = :companyId and r.saveStartDatetime >= :startDate and r.saveStartDatetime <= endDate and "
			+ "r.deletedFiles = 0 and r.systemType in (:systemType)";

	private final String SELECT_FILE_RECOVERY_SELECTION_DELETE = "SELECT r.delCode, r.delName, m.suppleExplanation, r.startDateTimeDel, r.delType, "
			+ "r.numberEmployees, r.fileName, r.fileId FROM SspdtResultDeletion  r "
			+ "INNER JOIN SspmtManualSetOfDataSave m on r.delCode = m.storeProcessingId "
			+ "where r.cid = :companyId and r.startDateTimeDel >= :startDate and r.startDateTimeDel <= endDate and "
			+ "r.isDeletedFilesFlg = 0 and r.systemType in (:systemType)";

	@Override
	public List<DataRecoverySelection> getDataRecoverySelection(String companyId, List<Integer> systemType,
			GeneralDateTime startDate, GeneralDateTime endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
