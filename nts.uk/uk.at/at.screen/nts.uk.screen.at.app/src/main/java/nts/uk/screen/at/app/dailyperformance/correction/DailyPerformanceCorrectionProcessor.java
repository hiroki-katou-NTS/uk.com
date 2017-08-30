/**
 * 1:57:38 PM Aug 21, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.task.AsyncTask;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class DailyPerformanceCorrectionProcessor {

	@Inject
	private DailyPerformanceScreenRepo repo;

	public DailyPerformanceCorrectionDto startScreen(DateRange dateRange, GeneralDate baseDate)
			throws InterruptedException {
		String sId = AppContexts.user().employeeId();
		ExecutorService es = Executors.newCachedThreadPool();
		DailyPerformanceCorrectionDto screenDto = new DailyPerformanceCorrectionDto();
		AsyncTask getHolidaySettingTask = AsyncTask.builder().withContexts().keepsTrack(true).build(() -> {
			/*
			 * アルゴリズム「休暇の管理状況をチェックする」を実行する (Tiến hành xử lý
			 * "Kiểm tra tình trạng quản lý ngày nghỉ")
			 */
			screenDto.setYearHolidaySettingDto(this.repo.getYearHolidaySetting());
			screenDto.setSubstVacationDto(this.repo.getSubstVacationDto());
			screenDto.setCompensLeaveComDto(this.repo.getCompensLeaveComDto());
			screenDto.setCom60HVacationDto(this.repo.getCom60HVacationDto());
		});
		AsyncTask getDisplayItemsAndErrorTask = AsyncTask.builder().withContexts().keepsTrack(true).build(() -> {
			// Get closure of login user
			ClosureDto clo = this.repo.getClosure(sId, baseDate);
			// システム日付を基準に1ヵ月前の期間を設定する
			screenDto.setDateRange(dateRange);
			// アルゴリズム「対象者を抽出する」を実行する (Tiến hành xử lý "Chiết suất đối tượng")
			if (clo != null) {
				screenDto.setLstEmployee(this.getListEmployee(sId, screenDto.getDateRange(), clo.getClosureId()));
			} else {
				screenDto.setLstEmployee(new ArrayList<>());
			}
			AsyncTask getDisplayItemsTask = AsyncTask.builder().withContexts().keepsTrack(true).build(() -> {
				// アルゴリズム「表示項目を制御する」を実行する (Tiến hành xử lý "Điều khiển item hiển
				// thị")
				if (screenDto.getLstEmployee().size() > 0) {
					screenDto.setLstControlDisplayItem(this.getControlDisplayItems(screenDto.getLstEmployee().stream()
							.map(e -> e.getEmployeeId()).collect(Collectors.toList()), screenDto.getDateRange()));
				}
			});
			es.execute(getDisplayItemsTask);
			AsyncTask getErrorsTask = AsyncTask.builder().withContexts().keepsTrack(true).build(() -> {
				/*
				 * アルゴリズム「期間に対応する実績エラーを取得する」を実行する (Tiến hành xử lý Lấy về lỗi
				 * thành tích tương ứng trong khoảng thời gian")
				 */
				if (screenDto.getLstEmployee().size() > 0) {
					screenDto.setLstError(this.getDPErrorList(screenDto.getLstEmployee().stream()
							.map(e -> e.getEmployeeId()).collect(Collectors.toList()), screenDto.getDateRange()));
					if (screenDto.getLstError().size() > 0) {
						// 対応するドメインモデル「勤務実績のエラーアラーム」をすべて取得する (Lấy về domain
						// model "Lỗi và alarm của thành tích công việc")
						screenDto.setLstErrorSetting(this.repo.getErrorSetting(screenDto.getLstError().stream()
								.map(e -> e.getErrorCode()).collect(Collectors.toList())));
					}
				}
			});
			es.execute(getErrorsTask);
			es.shutdown();
		});
		es.execute(getHolidaySettingTask);
		es.execute(getDisplayItemsAndErrorTask);
		
		// wait all tasks are done
		es.awaitTermination(1, TimeUnit.MINUTES);
		return screenDto;
	}

	private List<DailyPerformanceEmployeeDto> getListEmployee(String sId, DateRange dateRange, Integer closureId) {
		List<String> lstJobTitle = this.repo.getListJobTitle(dateRange);
		List<String> lstEmployment = this.repo.getListEmployment(closureId);
		List<String> lstWorkplace = this.repo.getListWorkplace(sId, dateRange);
		List<String> lstClassification = this.repo.getListClassification();
		return this.repo.getListEmployee(lstJobTitle, lstEmployment, lstWorkplace, lstClassification);
	}

	private DPControlDisplayItem getControlDisplayItems(List<String> lstEmployee, DateRange dateRange) {
		DPControlDisplayItem result = new DPControlDisplayItem();
		List<String> lstBusinessTypeCode = this.repo.getListBusinessType(lstEmployee, dateRange);
		List<FormatDPCorrectionDto> lstFormat = new ArrayList<>();
		if (lstBusinessTypeCode.size() > 0) {
			lstFormat = this.repo.getListFormatDPCorrection(lstBusinessTypeCode);
			result.setLstFormat(lstFormat);
		}
		List<DPBusinessTypeControl> lstDPBusinessTypeControl = new ArrayList<>();
		if (lstFormat.size() > 0) {
			lstDPBusinessTypeControl = this.repo.getListBusinessTypeControl(lstBusinessTypeCode,
					lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList()));
		}
		if (lstDPBusinessTypeControl.size() > 0) {
			result.setAttendanceItem(this.repo.getListAttendanceItem(
					lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList())));
			result.setAttendanceItemControl(this.repo.getListAttendanceItemControl(
					lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList())));
		}
		return result;
	}

	private List<DPErrorDto> getDPErrorList(List<String> lstEmployee, DateRange dateRange) {
		return this.repo.getListDPError(dateRange, lstEmployee);
	}

}
