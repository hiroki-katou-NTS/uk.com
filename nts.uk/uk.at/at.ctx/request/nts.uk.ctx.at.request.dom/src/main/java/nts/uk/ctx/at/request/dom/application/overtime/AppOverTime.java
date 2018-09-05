package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * @author loivt
 * 残業申請
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppOverTime extends AggregateRoot{
	
	/**
	 * application
	 */
	private Application_New application;
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 残業区分
	 */
	private OverTimeAtr overTimeAtr;
	/**
	 * 残業申請時間設定
	 */
	private List<OverTimeInput> overTimeInput;
	/**
	 * 勤務種類コード
	 */
	private WorkTypeCode workTypeCode;
	/**
	 * 就業時間帯
	 */
	private WorkTimeCode siftCode;
	/**
	 * 勤務時間From1
	 */
	private Integer workClockFrom1;
	/**
	 * 勤務時間To1
	 */
	private Integer workClockTo1;
	/**
	 * 勤務時間From2
	 */
	private Integer workClockFrom2;
	/**
	 * 勤務時間To2
	 */
	private Integer workClockTo2;
	/**
	 * 乖離定型理由
	 */
	private String divergenceReason;
	/**
	 * フレックス超過時間
	 */
	private Integer flexExessTime;
	/**
	 * 就業時間外深夜時間
	 */
	private Integer overTimeShiftNight;
	/**
	 * 時間外時間の詳細
	 */
	private Optional<AppOvertimeDetail> appOvertimeDetail;

	public AppOverTime(String companyID,
						String appID,
						int overTimeAtr,
						String workTypeCode, 
						String siftCode, 
						Integer workClockFrom1,
						Integer workClockTo1,
						Integer workClockFrom2,
						Integer workClockTo2,
						String divergenceReason,
						Integer flexExessTime,
						Integer overTimeShiftNight){
		this.companyID = companyID;
		this.appID = appID;
		this.overTimeAtr = EnumAdaptor.valueOf(overTimeAtr, OverTimeAtr.class);
		this.workTypeCode = workTypeCode == null ? null : new WorkTypeCode(workTypeCode);
		this.siftCode = siftCode == null ? null : new WorkTimeCode(siftCode);
		this.workClockFrom1 = workClockFrom1;
		this.workClockTo1 = workClockTo1;
		this.workClockFrom2 = workClockFrom2;
		this.workClockTo2 = workClockTo2;
		this.divergenceReason = divergenceReason;
		this.flexExessTime = flexExessTime;
		this.overTimeShiftNight = overTimeShiftNight;
	}
	
	public static AppOverTime createSimpleFromJavaType(String companyID,
														String appID, 
														int overTimeAtr,
														String workTypeCode, 
														String siftCode, 
														Integer workClockFrom1,
														Integer workClockTo1,
														Integer workClockFrom2,
														Integer workClockTo2,
														String divergenceReason,
														Integer flexExessTime,
														Integer overTimeShiftNight ){
		return new AppOverTime(companyID,
								appID,
								overTimeAtr,
								workTypeCode,
								siftCode,
								workClockFrom1, 
								workClockTo1,
								workClockFrom2,
								workClockTo2, 
								divergenceReason, 
								flexExessTime,
								overTimeShiftNight);
		
	}
}
