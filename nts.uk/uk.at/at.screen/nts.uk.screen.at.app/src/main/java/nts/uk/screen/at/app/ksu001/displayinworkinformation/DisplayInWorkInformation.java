/**
 * 
 */
package nts.uk.screen.at.app.ksu001.displayinworkinformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.JudgeHdSystemOneDayService;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.WorkScheduleShiftDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv ScreenQuery 勤務情報で表示する
 */
@Stateless
public class DisplayInWorkInformation {

	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private JudgeHdSystemOneDayService judgeHdSystemOneDayService;

	public DisplayInWorkInfoResult getData(DisplayInWorkInfoParam param) {

		DisplayInWorkInfoResult result = new DisplayInWorkInfoResult();

		String cid = AppContexts.user().companyId();
		List<WorkTypeInfomation> listWorkTypeInfo = new ArrayList<>();

		// <<Public>> 廃止されていない勤務種類をすべて取得する
		List<WorkType> listWorkType = basicScheduleService.getAllWorkTypeNotAbolished(cid);
		List<WorkTypeDto> listWorkTypeDto = listWorkType.stream().map(mapper -> {
			return new WorkTypeDto(mapper);
		}).collect(Collectors.toList());

		for (int i = 0; i < listWorkTypeDto.size(); i++) {

			// <<Public>> 廃止されていない勤務種類をすべて取得する
			WorkTypeDto workTypeDto = listWorkTypeDto.get(i);
			// 就業時間帯の必須チェック
			SetupType workTimeSetting = basicScheduleService
					.checkNeededOfWorkTimeSetting(listWorkTypeDto.get(i).getWorkTypeCode());

			// 1日半日出勤・1日休日系の判定 - (Thực hiện thuật toán [Kiểm tra hệ thống đi làm
			// nửa ngày・ nghỉ cả ngày ])
			AttendanceHolidayAttr attHdAtr = judgeHdSystemOneDayService
					.judgeHdOnDayWorkPer(listWorkTypeDto.get(i).getWorkTypeCode());

			WorkTypeInfomation workTypeInfomation = new WorkTypeInfomation(workTypeDto, workTimeSetting.value,
					attHdAtr.value);
			listWorkTypeInfo.add(workTypeInfomation);
		}
		result.setListWorkTypeInfo(listWorkTypeInfo);

		// fix tam data đoạn này
		List<WorkScheduleWorkInforDto> listWorkScheduleWorkInfor = new ArrayList<>();
		List<String> sids = Arrays.asList("fc4304be-8121-4bad-913f-3e48f4e2a752",
				"338c26ac-9b80-4bab-aa11-485f3c624186", "89ea1474-d7d8-4694-9e9b-416ea1d6381c",
				"ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570", "8f9edce4-e135-4a1e-8dca-ad96abe405d6",
				"9787c06b-3c71-4508-8e06-c70ad41f042a", "62785783-4213-4a05-942b-c32a5ffc1d63",
				"4859993b-8065-4789-90d6-735e3b65626b", "aeaa869d-fe62-4eb2-ac03-2dde53322cb5",
				"70c48cfa-7e8d-4577-b4f6-7b715c091f24", "c141daf2-70a4-4f4b-a488-847f4686e848");

		for (String sid : sids) {
			for (int i = 1; i < 32; i++) {
				WorkScheduleWorkInforDto w1 = new WorkScheduleWorkInforDto(sid, GeneralDate.ymd(2020, 7, i), false,
						false, false, false, 1, "001 ", "出勤 ", new EditStateOfDailyAttdDto(1, 0), "001 ", "テスト固定 ",
						new EditStateOfDailyAttdDto(1, 0), 480, new EditStateOfDailyAttdDto(1, 0), 1020,
						new EditStateOfDailyAttdDto(1, 0), 1);
				listWorkScheduleWorkInfor.add(w1);
			}
		}

		result.setListWorkScheduleWorkInfor(listWorkScheduleWorkInfor);
		return result;
	}

	public DisplayInWorkInfoResult getDataNextMonth(DisplayInWorkInfoParam param) {

		DisplayInWorkInfoResult result = new DisplayInWorkInfoResult();

		String cid = AppContexts.user().companyId();
		List<WorkTypeInfomation> listWorkTypeInfo = new ArrayList<>();

		// <<Public>> 廃止されていない勤務種類をすべて取得する
		List<WorkType> listWorkType = basicScheduleService.getAllWorkTypeNotAbolished(cid);
		List<WorkTypeDto> listWorkTypeDto = listWorkType.stream().map(mapper -> {
			return new WorkTypeDto(mapper);
		}).collect(Collectors.toList());

		for (int i = 0; i < listWorkTypeDto.size(); i++) {

			// <<Public>> 廃止されていない勤務種類をすべて取得する
			WorkTypeDto workTypeDto = listWorkTypeDto.get(i);
			// 就業時間帯の必須チェック
			SetupType workTimeSetting = basicScheduleService
					.checkNeededOfWorkTimeSetting(listWorkTypeDto.get(i).getWorkTypeCode());

			// 1日半日出勤・1日休日系の判定 - (Thực hiện thuật toán [Kiểm tra hệ thống đi làm
			// nửa ngày・ nghỉ cả ngày ])
			AttendanceHolidayAttr attHdAtr = judgeHdSystemOneDayService
					.judgeHdOnDayWorkPer(listWorkTypeDto.get(i).getWorkTypeCode());

			WorkTypeInfomation workTypeInfomation = new WorkTypeInfomation(workTypeDto, workTimeSetting.value,
					attHdAtr.value);
			listWorkTypeInfo.add(workTypeInfomation);
		}
		result.setListWorkTypeInfo(listWorkTypeInfo);

		// fix tam data đoạn này
		List<WorkScheduleWorkInforDto> listWorkScheduleWorkInfor = new ArrayList<>();
		List<String> sids = Arrays.asList("fc4304be-8121-4bad-913f-3e48f4e2a752",
				"338c26ac-9b80-4bab-aa11-485f3c624186", "89ea1474-d7d8-4694-9e9b-416ea1d6381c",
				"ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570", "8f9edce4-e135-4a1e-8dca-ad96abe405d6",
				"9787c06b-3c71-4508-8e06-c70ad41f042a", "62785783-4213-4a05-942b-c32a5ffc1d63",
				"4859993b-8065-4789-90d6-735e3b65626b", "aeaa869d-fe62-4eb2-ac03-2dde53322cb5",
				"70c48cfa-7e8d-4577-b4f6-7b715c091f24", "c141daf2-70a4-4f4b-a488-847f4686e848");

		for (String sid : sids) {
			for (int i = 1; i < 32; i++) {
				WorkScheduleWorkInforDto w1 = new WorkScheduleWorkInforDto(sid, GeneralDate.ymd(2020, 8, i), false,
						false, false, false, 1, "001 ", "N出勤 ", new EditStateOfDailyAttdDto(1, 0), "001 ", "Nテスト固定 ",
						new EditStateOfDailyAttdDto(1, 0), 480, new EditStateOfDailyAttdDto(1, 0), 1020,
						new EditStateOfDailyAttdDto(1, 0), 1);
				listWorkScheduleWorkInfor.add(w1);
			}
		}

		result.setListWorkScheduleWorkInfor(listWorkScheduleWorkInfor);
		return result;
	}

	public DisplayInWorkInfoResult getDataPreMonth(DisplayInWorkInfoParam param) {

		DisplayInWorkInfoResult result = new DisplayInWorkInfoResult();

		String cid = AppContexts.user().companyId();
		List<WorkTypeInfomation> listWorkTypeInfo = new ArrayList<>();

		// <<Public>> 廃止されていない勤務種類をすべて取得する
		List<WorkType> listWorkType = basicScheduleService.getAllWorkTypeNotAbolished(cid);
		List<WorkTypeDto> listWorkTypeDto = listWorkType.stream().map(mapper -> {
			return new WorkTypeDto(mapper);
		}).collect(Collectors.toList());

		for (int i = 0; i < listWorkTypeDto.size(); i++) {

			// <<Public>> 廃止されていない勤務種類をすべて取得する
			WorkTypeDto workTypeDto = listWorkTypeDto.get(i);
			// 就業時間帯の必須チェック
			SetupType workTimeSetting = basicScheduleService
					.checkNeededOfWorkTimeSetting(listWorkTypeDto.get(i).getWorkTypeCode());

			// 1日半日出勤・1日休日系の判定 - (Thực hiện thuật toán [Kiểm tra hệ thống đi làm
			// nửa ngày・ nghỉ cả ngày ])
			AttendanceHolidayAttr attHdAtr = judgeHdSystemOneDayService
					.judgeHdOnDayWorkPer(listWorkTypeDto.get(i).getWorkTypeCode());

			WorkTypeInfomation workTypeInfomation = new WorkTypeInfomation(workTypeDto, workTimeSetting.value,
					attHdAtr.value);
			listWorkTypeInfo.add(workTypeInfomation);
		}
		result.setListWorkTypeInfo(listWorkTypeInfo);

		// fix tam data đoạn này
		List<WorkScheduleWorkInforDto> listWorkScheduleWorkInfor = new ArrayList<>();
		List<String> sids = Arrays.asList("fc4304be-8121-4bad-913f-3e48f4e2a752",
				"338c26ac-9b80-4bab-aa11-485f3c624186", "89ea1474-d7d8-4694-9e9b-416ea1d6381c",
				"ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570", "8f9edce4-e135-4a1e-8dca-ad96abe405d6",
				"9787c06b-3c71-4508-8e06-c70ad41f042a", "62785783-4213-4a05-942b-c32a5ffc1d63",
				"4859993b-8065-4789-90d6-735e3b65626b", "aeaa869d-fe62-4eb2-ac03-2dde53322cb5",
				"70c48cfa-7e8d-4577-b4f6-7b715c091f24", "c141daf2-70a4-4f4b-a488-847f4686e848");

		for (String sid : sids) {
			for (int i = 1; i < 31; i++) {
				WorkScheduleWorkInforDto w1 = new WorkScheduleWorkInforDto(sid, GeneralDate.ymd(2020, 6, i), false,
						false, false, false, 1, "001 ", "P出勤 ", new EditStateOfDailyAttdDto(1, 0), "001 ", "Pテスト固定 ",
						new EditStateOfDailyAttdDto(1, 0), 480, new EditStateOfDailyAttdDto(1, 0), 1020,
						new EditStateOfDailyAttdDto(1, 0), 1);
				listWorkScheduleWorkInfor.add(w1);
			}
		}

		result.setListWorkScheduleWorkInfor(listWorkScheduleWorkInfor);
		return result;
	}

}
