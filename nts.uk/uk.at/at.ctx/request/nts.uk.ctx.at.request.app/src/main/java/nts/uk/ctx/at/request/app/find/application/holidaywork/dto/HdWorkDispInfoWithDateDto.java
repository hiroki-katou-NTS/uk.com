package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.overtime.ApplicationTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.BreakTimeZoneSettingDto;
import nts.uk.ctx.at.request.app.find.application.overtime.WorkHoursDto;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkDispInfoWithDateOutput;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HdWorkDispInfoWithDateDto {

	/**
	 * 代休管理区分
	 */
	private boolean subHdManage;
	
	/**
	 * 勤務時間
	 */
	private WorkHoursDto workHours;
	
	/**
	 * 休憩時間帯設定リスト
	 */
	private BreakTimeZoneSettingDto breakTimeZoneSettingList;
	
	/**
	 * 勤務種類リスト
	 */
	private List<WorkTypeDto> workTypeList;
	
	/**
	 * 初期選択勤務種類
	 */
	private String initWorkType;
	
	/**
	 * 初期選択勤務種類名称
	 */
	private String initWorkTypeName;
	
	/**
	 * 初期選択就業時間帯
	 */
	private String initWorkTime;
	
	/**
	 *初期選択就業時間帯名称
	 */
	private String initWorkTimeName;

	/**
	 * 勤怠時間の超過状態
	 */
	private OvertimeStatusDto overtimeStatus;
	
	/**
	 * 実績の申請時間
	 */
	private ApplicationTimeDto actualApplicationTime;
	
	/**
	 * 月別実績の36協定時間状態
	 */
	private Integer actualMonthlyAgreeTimeStatus;
	
	public static HdWorkDispInfoWithDateDto fromDomain(HdWorkDispInfoWithDateOutput domain) {
		if(domain == null) return null;
		List<WorkType> workTypeDomainList = domain.getWorkTypeList().orElse(new ArrayList<WorkType>());
		
		return new HdWorkDispInfoWithDateDto(domain.isSubHdManage(), WorkHoursDto.fromDomain(domain.getWorkHours()),
				BreakTimeZoneSettingDto.fromDomain(domain.getBreakTimeZoneSettingList().isPresent() ? domain.getBreakTimeZoneSettingList().get() : null), 
				workTypeDomainList.stream().map(workType -> WorkTypeDto.fromDomain(workType)).collect(Collectors.toList()), 
				domain.getInitWorkType().isPresent() ? domain.getInitWorkType().get().v() : "", 
				domain.getInitWorkTypeName().isPresent() ? domain.getInitWorkTypeName().get().v() : "", 
				domain.getInitWorkTime().isPresent() ? domain.getInitWorkTime().get().v() : "", 
				domain.getInitWorkTimeName().isPresent() ? domain.getInitWorkTimeName().get().v() : "", 
				OvertimeStatusDto.fromDomain(domain.getOvertimeStatus()), 
				ApplicationTimeDto.fromDomain(domain.getActualApplicationTime().orElse(null)), 
				domain.getActualMonthlyAgreeTimeStatus().isPresent() ? domain.getActualMonthlyAgreeTimeStatus().get().value : null);
	}
}
