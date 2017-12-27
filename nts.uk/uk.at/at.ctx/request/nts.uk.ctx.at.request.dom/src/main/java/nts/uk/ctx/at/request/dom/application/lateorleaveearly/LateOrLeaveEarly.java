package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.DisabledSegment_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflectDaily_New;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.ReflectionInformation_New;

/**
 * 
 * @author hieult
 *遅刻早退取消申請
 */
@Getter
public class LateOrLeaveEarly extends Application_New {
	/** 会社ID */
	private final String companyID;
	/** 申請ID */
	private final String appID;
	/** 実績取消区分 */
	@Setter
	private int actualCancelAtr;
	/** 早退1 */
	@Setter
	private Select early1;
	/** 早退時刻1 */
	@Setter
	private TimeDay earlyTime1;
	/** 遅刻1 */
	@Setter
	private Select late1;
	/** 遅刻時刻1 */
	@Setter
	private TimeDay lateTime1;
	/** 早退2 */
	@Setter
	private Select early2;
	/** 早退時刻2 */
	@Setter
	private TimeDay earlyTime2;
	/** 遅刻2 */
	@Setter
	private Select late2;
	/** 遅刻時刻2 */
	@Setter
	private TimeDay lateTime2;

	/** All Agrs constructor */
	public LateOrLeaveEarly(String companyID, String appID, int prePostAtr, GeneralDateTime inputDate,
			String enteredPersonSID, String reversionReason, GeneralDate applicationDate, String applicationReason,
			int applicationType, String applicantSID, int reflectPlanScheReason,
			GeneralDateTime reflectPlanTime, int reflectPlanState, int reflectPlanEnforce,
			int reflectPerScheReason, GeneralDateTime reflectPerTime, int reflectPerState,
			int reflectPerEnforce,GeneralDate startDate,GeneralDate endDate,
			int early1, int earlyTime1, int late1, int lateTime1,
			int early2, int earlyTime2, int late2, int lateTime2) {
		super(0L, 
				companyID, 
				appID, 
				EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), 
				inputDate, 
				enteredPersonSID,
				new AppReason(reversionReason), 
				applicationDate, 
				new AppReason(applicationReason),
				EnumAdaptor.valueOf(applicationType, ApplicationType.class), 
				applicantSID, 
				Optional.ofNullable(startDate), 
				Optional.ofNullable(endDate),
				ReflectionInformation_New.builder()
				.stateReflection(EnumAdaptor.valueOf(reflectPlanState, ReflectedState_New.class)) 
				.stateReflectionReal(EnumAdaptor.valueOf(reflectPerState, ReflectedState_New.class))
				.forcedReflection(EnumAdaptor.valueOf(reflectPlanEnforce, DisabledSegment_New.class))
				.forcedReflectionReal(EnumAdaptor.valueOf(reflectPerEnforce, DisabledSegment_New.class))
				.notReason(Optional.ofNullable(reflectPlanScheReason).map(x -> EnumAdaptor.valueOf(x, ReasonNotReflect_New.class)))
				.notReasonReal(Optional.ofNullable(reflectPerScheReason).map(x -> EnumAdaptor.valueOf(x, ReasonNotReflectDaily_New.class)))		
				.dateTimeReflection(Optional.ofNullable(reflectPlanTime))
				.dateTimeReflectionReal(Optional.ofNullable(reflectPerTime)).build()
				);
		
		this.companyID = companyID;
		this.appID = appID;
		this.early1 = EnumAdaptor.valueOf(early1, Select.class);
		this.earlyTime1 = new TimeDay(earlyTime1);
		this.late1 = EnumAdaptor.valueOf(late1, Select.class);
		this.lateTime1 = new TimeDay(lateTime1);
		this.early2 = EnumAdaptor.valueOf(early2, Select.class);
		this.earlyTime2 = new TimeDay(earlyTime2);
		this.late2 = EnumAdaptor.valueOf(late2, Select.class);
		this.lateTime2 = new TimeDay(lateTime2);
	}

	public void changeApplication(int actualCancelAtr, int early1,
			int earlyTime1, int late1, int lateTime1, int early2, int earlyTime2, int late2, int lateTime2)
	{
		this.actualCancelAtr = actualCancelAtr;
		this.early1 = EnumAdaptor.valueOf(early1, Select.class);
		this.earlyTime1 = EnumAdaptor.valueOf(earlyTime1, TimeDay.class);
		this.late1 = EnumAdaptor.valueOf(late1, Select.class);
		this.lateTime1 = EnumAdaptor.valueOf(lateTime1, TimeDay.class);
		this.early2 = EnumAdaptor.valueOf(early2, Select.class);
		this.earlyTime2 = EnumAdaptor.valueOf(earlyTime2, TimeDay.class);
		this.late2 = EnumAdaptor.valueOf(late2, Select.class);
		this.lateTime2 = EnumAdaptor.valueOf(lateTime2, TimeDay.class);
	}
	
}