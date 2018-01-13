package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.WorkTimeGoBack;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 直行直帰申請
 * 
 * @author ducpm
 *
 */
@Getter
public class GoBackDirectly extends AggregateRoot {
	/**
	 * 会社ID
	 */
	private String companyID;
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 勤務種類
	 */
	private WorkTypeCode workTypeCD;
	/**
	 * 就業時間帯
	 */
	private WorkTimeCode siftCD;
	/**
	 * 勤務を変更する
	 */
	private UseAtr workChangeAtr;
	/**
	 * 勤務直行1
	 */
	private UseAtr goWorkAtr1;
	/**
	 * 勤務直帰1
	 */
	private UseAtr backHomeAtr1;
	/**
	 * 勤務時間開始1
	 */
	private WorkTimeGoBack workTimeStart1;
	/**
	 * 勤務時間終了1
	 */
	private WorkTimeGoBack workTimeEnd1;
	/**
	 * 勤務場所選択1
	 */
	private String workLocationCD1;
	/**
	 * 勤務直行2
	 */
	private UseAtr goWorkAtr2;
	/**
	 * 勤務直帰2
	 */
	private UseAtr backHomeAtr2;
	/**
	 * 勤務時間開始2
	 */
	private WorkTimeGoBack workTimeStart2;
	/**
	 * 勤務時間終了2
	 */
	private WorkTimeGoBack workTimeEnd2;
	/**
	 * 勤務場所選択２
	 */
	private String workLocationCD2;

	public GoBackDirectly(String companyID, String appID, String workTypeCD, String siftCD, int workChangeAtr,
			int goWorkAtr1, int backHomeAtr1, Integer workTimeStart1, Integer workTimeEnd1, String workLocationCD1,
			int goWorkAtr2, int backHomeAtr2, Integer workTimeStart2, Integer workTimeEnd2, String workLocationCD2) {
		this.companyID = companyID;
		this.appID = appID;
		this.workTypeCD = new WorkTypeCode(workTypeCD);
		this.siftCD = new WorkTimeCode(siftCD);
		this.workChangeAtr = EnumAdaptor.valueOf(workChangeAtr, UseAtr.class);
		this.goWorkAtr1 = EnumAdaptor.valueOf(goWorkAtr1, UseAtr.class);
		this.backHomeAtr1 = EnumAdaptor.valueOf(backHomeAtr1, UseAtr.class);
		this.workTimeStart1 = new WorkTimeGoBack(workTimeStart1);
		this.workTimeEnd1 = new WorkTimeGoBack(workTimeEnd1);
		this.workLocationCD1 = workLocationCD1;
		this.goWorkAtr2 = EnumAdaptor.valueOf(goWorkAtr2, UseAtr.class);
		this.backHomeAtr2 = EnumAdaptor.valueOf(backHomeAtr2, UseAtr.class);
		this.workTimeStart2 = new WorkTimeGoBack(workTimeStart2);
		this.workTimeEnd2 = new WorkTimeGoBack(workTimeEnd2);
		this.workLocationCD2 = workLocationCD2;
	}

	public GoBackDirectly createFromJavaType(String companyID, String appID, String workTypeCD, String siftCD,
			int workChangeAtr, int goWorkAtr1, int backHomeAtr1, Integer workTimeStart1, Integer workTimeEnd1,
			String workLocationCD1, int goWorkAtr2, int backHomeAtr2, Integer workTimeStart2, Integer workTimeEnd2,
			String workLocationCD2) {
		return new GoBackDirectly(companyID, appID, workTypeCD, siftCD, workChangeAtr, goWorkAtr1, backHomeAtr1,
				workTimeStart1, workTimeEnd1, workLocationCD1, goWorkAtr2, backHomeAtr2, workTimeStart2, workTimeEnd2,
				workLocationCD2);
	}
}