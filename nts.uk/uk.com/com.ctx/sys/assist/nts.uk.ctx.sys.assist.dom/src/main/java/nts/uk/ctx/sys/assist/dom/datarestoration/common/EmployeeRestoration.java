package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.security.crypt.commonkey.CommonKeyCrypt;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.datarestoration.Target;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSaving;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSavingRepository;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;

@Stateless
public class EmployeeRestoration {
	@Inject
	private PerformDataRecoveryRepository performDataRecoveryRepository;
	@Inject
	private ResultOfSavingRepository resultOfSavingRepository;
	private static final String TARGET_CSV = "対象社員";

	// アルゴリズム「対象社員の復元」を実行する
	public ServerPrepareMng restoreTargerEmployee(ServerPrepareMng serverPrepareMng,
			PerformDataRecovery performDataRecovery, List<TableList> tableList) {
		List<List<String>> targetEmployee = CsvFileUtil.getAllRecord(serverPrepareMng.getFileId().get(),TARGET_CSV);
		if (!targetEmployee.isEmpty()) {
			try {
				for (List<String> employeeInfo : targetEmployee.subList(1, targetEmployee.size())) {
					performDataRecoveryRepository.addTargetEmployee(
							new Target(serverPrepareMng.getDataRecoveryProcessId(), employeeInfo.get(0),
									employeeInfo.get(1), CommonKeyCrypt.decrypt(employeeInfo.get(2))));
				}
				int numOfPeopleRestore = 0;
				int numPeopleSave = targetEmployee.size() - 1;
				Optional<String> saveProcessId = Optional.empty();
				if (!tableList.isEmpty()) {
					Optional<ResultOfSaving> savingInfo = resultOfSavingRepository
							.getResultOfSavingById(tableList.get(0).getDataStorageProcessingId());
					if (savingInfo.isPresent()) {
						numOfPeopleRestore = savingInfo.get().getTargetNumberPeople();
						saveProcessId = Optional.ofNullable(savingInfo.get().getStoreProcessingId());
					}
					performDataRecovery.setSaveProcessId(saveProcessId);
				}
				performDataRecovery.setNumPeopleBeRestore(numOfPeopleRestore);
				performDataRecovery.setNumPeopleSave(numPeopleSave);
				serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.CHECK_COMPLETED);
				performDataRecoveryRepository.add(performDataRecovery);
			} catch (Exception e) {
				serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.EM_LIST_ABNORMALITY);
				return serverPrepareMng;
			}
		} else {
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.EM_LIST_ABNORMALITY);
		}
		return serverPrepareMng;
	}
}
