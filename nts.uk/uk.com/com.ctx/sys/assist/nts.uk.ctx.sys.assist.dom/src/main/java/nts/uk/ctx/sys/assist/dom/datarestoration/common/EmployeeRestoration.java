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
	public List<Object> restoreTargerEmployee(ServerPrepareMng serverPrepareMng, PerformDataRecovery performDataRecovery, List<TableList> tableList){
		InputStream inputStream = CsvFileUtil.createInputStreamFromFile(serverPrepareMng.getFileId().get(), TARGET_CSV);
		if(Objects.isNull(inputStream)){
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.EM_LIST_ABNORMALITY);
		} else {
			List<List<String>> targetEmployee = CsvFileUtil.getAllRecord(inputStream);
			if (targetEmployee.size() > 0) {
				for(List<String> employeeInfo : targetEmployee.subList(1, targetEmployee.size())){
					performDataRecoveryRepository.addTargetEmployee(new Target(serverPrepareMng.getDataRecoveryProcessId(), employeeInfo.get(0), employeeInfo.get(1), CommonKeyCrypt.decrypt(employeeInfo.get(2))));
				}
				int numOfPeopleRestore = 0;
				int numPeopleSave = targetEmployee.size();
				if (!tableList.isEmpty()){
					Optional<ResultOfSaving> savingInfo = resultOfSavingRepository.getResultOfSavingById(tableList.get(0).getDataStorageProcessingId());
					numOfPeopleRestore = savingInfo.isPresent() ? savingInfo.get().getTargetNumberPeople() : 0;
					performDataRecovery.setSaveProcessId(Optional.of(savingInfo.isPresent() ? savingInfo.get().getStoreProcessingId() : Optional.empty()));
				}
				performDataRecovery.setNumPeopleBeRestore(numOfPeopleRestore);
				performDataRecovery.setNumPeopleSave(numPeopleSave);
				serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.CHECK_COMPLETED);
				performDataRecoveryRepository.add(performDataRecovery);
			}
		}
		return Arrays.asList(serverPrepareMng, performDataRecovery, tableList);
	}
}
