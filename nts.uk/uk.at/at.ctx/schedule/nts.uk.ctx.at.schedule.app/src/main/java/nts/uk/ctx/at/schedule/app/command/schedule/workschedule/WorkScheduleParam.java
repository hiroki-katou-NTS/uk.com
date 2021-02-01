package nts.uk.ctx.at.schedule.app.command.schedule.workschedule;
import java.util.List;

/**
 * 
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkScheduleParam {
	
	public String sid;
	public String ymd;
	public String workTypeCd;
	public String workTimeCd;
	public Integer startTime;
	public Integer endTime;
	public Integer startTime2;
	public Integer endTime2;
	//List<休憩時間帯>
	public List<TimeSpanForCalcDto> listBreakTime;
	public boolean directAtr;
	public boolean bounceAtr;
}
