package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkDispInfoWithDateOutput_Old;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.DeductionTimeDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

@NoArgsConstructor
public class HdWorkDispInfoWithDateDto_Old {
	
	/**
	 * 代休管理区分
	 */
	public boolean subHdManage;
	
	/**
	 * 休出申請指示
	 */
	public HolidayWorkInstruction appHdWorkInstruction;
	
	/**
	 * 初期選択勤務種類
	 */
	public String workTypeCD;
	
	/**
	 * 初期選択就業時間帯
	 */
	public String workTimeCD;
	
	/**
	 * 開始時刻
	 */
	public Integer startTime;
	
	/**
	 * 終了時刻
	 */
	public Integer endTime;
	
	/**
	 * 表示する実績内容
	 */
	public List<AchievementOutput> achievementOutputLst;
	
	/**
	 * 実績状態
	 */
	public Integer actualStatus;
	
	/**
	 * 勤怠時間の超過状態
	 */
	public String overtimeStatus;
	
	/**
	 * 月別実績の36協定時間状態
	 */
	public Integer agreementTimeStatusOfMonthly;
	
	/**
	 * 初期選択勤務種類名称
	 */
	public String workTypeName;
	
	/**
	 * 初期選択就業時間帯名称
	 */
	public String workTimeName;
	
	/**
	 * 勤務種類リスト
	 */
	public List<WorkTypeDto> workTypeLst;
	
	/**
	 * 休憩時間帯設定リスト
	 */
	public List<DeductionTimeDto> deductionTimeLst;
	
	public static HdWorkDispInfoWithDateDto_Old fromDomain(HdWorkDispInfoWithDateOutput_Old hdWorkDispInfoWithDateOutput) {
		HdWorkDispInfoWithDateDto_Old result = new HdWorkDispInfoWithDateDto_Old();
		result.subHdManage = hdWorkDispInfoWithDateOutput.isSubHdManage();
		result.appHdWorkInstruction = hdWorkDispInfoWithDateOutput.getAppHdWorkInstruction();
		result.workTypeCD = hdWorkDispInfoWithDateOutput.getWorkTypeCD();
		result.workTimeCD = hdWorkDispInfoWithDateOutput.getWorkTimeCD();
		result.startTime = hdWorkDispInfoWithDateOutput.getStartTime();
		result.endTime = hdWorkDispInfoWithDateOutput.getEndTime();
		result.achievementOutputLst = hdWorkDispInfoWithDateOutput.getAchievementOutputLst();
		result.actualStatus = hdWorkDispInfoWithDateOutput.getActualStatus() == null ? null : hdWorkDispInfoWithDateOutput.getActualStatus().value;
		result.overtimeStatus = hdWorkDispInfoWithDateOutput.getOvertimeStatus();
		result.agreementTimeStatusOfMonthly = hdWorkDispInfoWithDateOutput.getAgreementTimeStatusOfMonthly() == null ? null : hdWorkDispInfoWithDateOutput.getAgreementTimeStatusOfMonthly().value;
		result.workTypeName = hdWorkDispInfoWithDateOutput.getWorkTypeName();
		result.workTimeName = hdWorkDispInfoWithDateOutput.getWorkTimeName();
		result.workTypeLst = hdWorkDispInfoWithDateOutput.getWorkTypeLst().map(item -> item.stream().map(x -> WorkTypeDto.fromDomain(x)).collect(Collectors.toList()))
				.orElse(Collections.emptyList());
		if(hdWorkDispInfoWithDateOutput.getDeductionTimeLst() != null) {
			result.deductionTimeLst = hdWorkDispInfoWithDateOutput.getDeductionTimeLst()
					.map(item -> 
						item.stream().map(domain -> {
							DeductionTimeDto dto = new DeductionTimeDto();
							domain.saveToMemento(dto);
							return dto;
						}).collect(Collectors.toList()))
					.orElse(Collections.emptyList());
		}
		return result;
	}
	
}
