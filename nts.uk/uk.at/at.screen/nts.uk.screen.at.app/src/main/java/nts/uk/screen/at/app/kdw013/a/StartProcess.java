package nts.uk.screen.at.app.kdw013.a;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.jobmanagement.displayformat.ManHrInputDisplayFormat;
import nts.uk.screen.at.app.kdw013.query.AttendanceItemMasterInformationDto;
import nts.uk.screen.at.app.kdw013.query.GetFavoriteTask;
import nts.uk.screen.at.app.kdw013.query.GetFavoriteTaskDto;
import nts.uk.screen.at.app.kdw013.query.GetWorkDataMasterInformation;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.初期起動処理
 * 
 * @author tutt
 *
 */
@Stateless
public class StartProcess {
	
	@Inject
	private StartManHourInputScreenQuery manHourQuery;

	@Inject
	private GetWorkDataMasterInformation getWorkDataMasterInformation;

	@Inject
	private GetRefWorkplaceAndEmployee GetRefWorkplaceAndEmployee;

	@Inject
	private GetFavoriteTask getFavoriteTask;

	public StartProcessDto startProcess() {
		
		StartProcessDto result = new StartProcessDto();

		// 1. 工数入力を起動する
		StartManHourInput manHourInput = this.manHourQuery.startManHourInput();

		result.setManHourInput(manHourInput);

		// 2. call($勤怠項目リスト)
		AttendanceItemMasterInformationDto itemMasterInfo = this.getWorkDataMasterInformation
				.getAttendanceItemMasterInformation(collectItemList(manHourInput.getManHrInputDisplayFormat()));

		result.setItemMasterInfo(itemMasterInfo);

		// 3. 画面モード = 確認モード <call>(システム日付)
		GetRefWorkplaceAndEmployeeDto refWork = this.GetRefWorkplaceAndEmployee.get(GeneralDate.today());

		result.setRefWork(refWork);

		// 4.画面モード = 入力モード

		GetFavoriteTaskDto favTask = this.getFavoriteTask.getFavTask();

		result.setFavTask(favTask);

		// 5. get (bước này làm dưới client)

		return result;
	}
	
	private List<Integer> collectItemList(Optional<ManHrInputDisplayFormat> ManHrOpt) {

		return ManHrOpt.map(manHr -> manHr.getRecordColumnDisplayItems().stream().map(di -> di.getAttendanceItemId())
				.collect(Collectors.toList())).orElse(Collections.EMPTY_LIST);
	}

}
