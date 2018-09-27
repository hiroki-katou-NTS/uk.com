package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author sonnh1
 *
 */
@Data
@AllArgsConstructor
public class BasicScheduleFromSql {
	private String sId;
	private GeneralDate date;
	private String workTypeCode;
	private String workTimeCode;
	private int confirmAtr;
	
	private Integer timezoneCnt;
	private Integer bounceAtr;
	private Integer timezoneStart;
	private Integer timezoneEnd;
	
	private Integer breakTime;
	private Integer workingTime;
	private Integer weekdayTime;
	private Integer prescribedTime;
	private Integer totalLaborTime;
	private Integer childTime;
	private Integer careTime;
	private Integer flexTime;
	
	private Integer feeTimeNo;
	private Integer personFeeTime;
	
	private Integer breakCnt;
	private Integer breakTimeStart;
	private Integer breakTimeEnd;
	
	private String empCd;
	private String clsCd;
	private String bussinessTypeCd;
	private String jobId;
	private String wkpId;
}
