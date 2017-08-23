package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.UseAtr;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.WorkTimeGoBack;
import nts.uk.ctx.at.shared.dom.worktime.SiftCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Value
public class GoBackDirectly {

	private String companyID;

	private String appID;

	private WorkTypeCode workTypeCD;

	private SiftCode siftCd;

	private UseAtr workChangeAtr;

	private WorkTimeGoBack workTimeStart1;

	private WorkTimeGoBack workTimeEnd1;

	private String workLocationCD1;

	private UseAtr goWorkAtr1;

	private UseAtr backHomeAtr1;

	private WorkTimeGoBack workTimeStart2;

	private WorkTimeGoBack workTimeEnd2;

	private String workLocationCD2;

	private UseAtr goWorkAtr2;

	private UseAtr backHomeAtr2;

	public GoBackDirectly(String companyID, String appID, WorkTypeCode workTypeCD, SiftCode siftCd,
			UseAtr workChangeAtr, WorkTimeGoBack workTimeStart1, WorkTimeGoBack workTimeEnd1, String workLocationCD1,
			UseAtr goWorkAtr1, UseAtr backHomeAtr1, WorkTimeGoBack workTimeStart2, WorkTimeGoBack workTimeEnd2,
			String workLocationCD2, UseAtr goWorkAtr2, UseAtr backHomeAtr2) {
		super();
		this.companyID = companyID;
		this.appID = appID;
		this.workTypeCD = workTypeCD;
		this.siftCd = siftCd;
		this.workChangeAtr = workChangeAtr;
		this.workTimeStart1 = workTimeStart1;
		this.workTimeEnd1 = workTimeEnd1;
		this.workLocationCD1 = workLocationCD1;
		this.goWorkAtr1 = goWorkAtr1;
		this.backHomeAtr1 = backHomeAtr1;
		this.workTimeStart2 = workTimeStart2;
		this.workTimeEnd2 = workTimeEnd2;
		this.workLocationCD2 = workLocationCD2;
		this.goWorkAtr2 = goWorkAtr2;
		this.backHomeAtr2 = backHomeAtr2;
	}
}
