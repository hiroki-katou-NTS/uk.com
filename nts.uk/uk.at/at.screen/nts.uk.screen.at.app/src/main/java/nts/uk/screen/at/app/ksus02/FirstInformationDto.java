package nts.uk.screen.at.app.ksus02;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.screen.at.app.kdl045.query.WorkAvailabilityOfOneDayDto;

/**
 * 最初情報dto
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class FirstInformationDto {
	//勤務希望の指定方法 
	private int specifyWorkPre ;
	//シフト勤務単位
	private int shiftWorkUnit;
	//勤務希望の締切日
	private String deadlineForWork;
	//勤務希望の期間.start
	private String startWork;
	//勤務希望の期間.end
	private String endWork;
	//一日分の勤務希望の表示情報リスト
	private List<WorkAvailabilityOfOneDayDto> displayInfoListWorkOneDay;
	//シフト情報リスト
	private List<ShiftMasterAndWorkInfoScheTime> listShiftInfor;
	public FirstInformationDto(int specifyWorkPre, int shiftWorkUnit, String deadlineForWork, String startWork,
			String endWork, List<WorkAvailabilityOfOneDayDto> displayInfoListWorkOneDay,
			List<ShiftMasterAndWorkInfoScheTime> listShiftInfor) {
		super();
		this.specifyWorkPre = specifyWorkPre;
		this.shiftWorkUnit = shiftWorkUnit;
		this.deadlineForWork = deadlineForWork;
		this.startWork = startWork;
		this.endWork = endWork;
		this.displayInfoListWorkOneDay = displayInfoListWorkOneDay;
		this.listShiftInfor = listShiftInfor;
	}
	
}
