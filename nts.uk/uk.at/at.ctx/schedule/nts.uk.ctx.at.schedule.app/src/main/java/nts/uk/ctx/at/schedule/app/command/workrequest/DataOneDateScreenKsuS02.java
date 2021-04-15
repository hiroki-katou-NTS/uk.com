package nts.uk.ctx.at.schedule.app.command.workrequest;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.workrule.workinghours.TimeSpanForCalcSharedDto;

@Getter
@Setter
@NoArgsConstructor
public class DataOneDateScreenKsuS02 {
	//作る(年月日, 勤務希望のメモ, 勤務希望の指定方法, List<シフトマスタコード>, List<時間帯>)
	//年月日
	private String date ;
	
	//勤務希望のメモ
	private String memo;
	
	//勤務希望の指定方法
	private int assignmentMethod;
	
	//List<シフトマスタコード>
	private List<String> nameList;
	
	//List<時間帯>
	private List<TimeSpanForCalcSharedDto> timeZoneList;

	public DataOneDateScreenKsuS02(String date, String memo, int assignmentMethod, List<String> nameList,
			List<TimeSpanForCalcSharedDto> timeZoneList) {
		super();
		this.date = date;
		this.memo = memo;
		this.assignmentMethod = assignmentMethod;
		this.nameList = nameList;
		this.timeZoneList = timeZoneList;
	}
}
