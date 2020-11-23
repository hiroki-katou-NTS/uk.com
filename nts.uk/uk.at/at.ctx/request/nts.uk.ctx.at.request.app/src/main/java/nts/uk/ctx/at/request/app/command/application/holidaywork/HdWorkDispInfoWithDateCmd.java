package nts.uk.ctx.at.request.app.command.application.holidaywork;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatus;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkDispInfoWithDateOutput_Old;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.DeductionTimeDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

public class HdWorkDispInfoWithDateCmd {
	
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
	
	public HdWorkDispInfoWithDateOutput_Old toDomain() {
		HdWorkDispInfoWithDateOutput_Old result = new HdWorkDispInfoWithDateOutput_Old();
		result.setSubHdManage(subHdManage);
		result.setAppHdWorkInstruction(appHdWorkInstruction);
		result.setWorkTypeCD(workTypeCD);
		result.setWorkTimeCD(workTimeCD);
		result.setStartTime(startTime);
		result.setEndTime(endTime);
		result.setAchievementOutputLst(achievementOutputLst);
		result.setActualStatus(actualStatus == null ? null : EnumAdaptor.valueOf(actualStatus, ActualStatus.class));
		result.setOvertimeStatus(overtimeStatus);
		result.setAgreementTimeStatusOfMonthly(agreementTimeStatusOfMonthly == null ? null : EnumAdaptor.valueOf(agreementTimeStatusOfMonthly, AgreementTimeStatusOfMonthly.class));
		result.setWorkTypeName(workTypeName);
		result.setWorkTimeName(workTimeName);
		result.setWorkTypeLst(CollectionUtil.isEmpty(workTypeLst) ? Optional.empty() : Optional.of(workTypeLst.stream().map(x -> x.toDomain()).collect(Collectors.toList())));
		result.setDeductionTimeLst(CollectionUtil.isEmpty(deductionTimeLst) ? Optional.empty() : 
			Optional.of(deductionTimeLst.stream().map(x -> new DeductionTime(x)).collect(Collectors.toList())));
		return result;
	}
	
}
