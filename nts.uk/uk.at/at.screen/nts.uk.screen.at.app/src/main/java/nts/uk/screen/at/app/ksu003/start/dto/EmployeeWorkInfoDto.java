package nts.uk.screen.at.app.ksu003.start.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.TimeZonesDto;

/**
 * 対象社員の社員勤務情報dto - 社員勤務情報dto
 * @author phongtq
 *
 */
@Value
public class EmployeeWorkInfoDto {
	//応援か : SupportAtr
	private int isCheering;
	//確定済みか 0:false or 1:true
	private int isConfirmed;
	//直帰区分 0:false or 1:true
	private int bounceAtr;
	//直行区分 0:false or 1:true
	private int directAtr;
	//勤務予定が必要か 0:false or 1:true
	private int isNeedWorkSchedule;
	//社員ID
	private String employeeId;
	//年月日
	private String date;
	//応援時間帯
	private TimeZonesDto timeZonesDto;
	//応援先の職場名称
	private String wkpNameSupport;
	//Map<時間休暇種類, 時間休暇>
	private List<TimeVacationAndTypeDto> listTimeVacationAndType;
	//シフトコード
	private String shiftCode;
	//シフト名称
	private String shiftName;
	//List<育児介護短時間帯>
	private List<TimeZoneDto> shortTime;
	public EmployeeWorkInfoDto(int isCheering, int isConfirmed, int bounceAtr, int directAtr,
			int isNeedWorkSchedule, String employeeId, String date, TimeZonesDto timeZonesDto,
			String wkpNameSupport, List<TimeVacationAndTypeDto> listTimeVacationAndType, String shiftCode,
			String shiftName, List<TimeZoneDto> shortTime) {
		super();
		this.isCheering = isCheering;
		this.isConfirmed = isConfirmed;
		this.bounceAtr = bounceAtr;
		this.directAtr = directAtr;
		this.isNeedWorkSchedule = isNeedWorkSchedule;
		this.employeeId = employeeId;
		this.date = date;
		this.timeZonesDto = timeZonesDto;
		this.wkpNameSupport = wkpNameSupport;
		this.listTimeVacationAndType = listTimeVacationAndType;
		this.shiftCode = shiftCode;
		this.shiftName = shiftName;
		this.shortTime = shortTime;
	}
}
