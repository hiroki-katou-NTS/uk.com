package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import java.util.Optional;

import javax.persistence.SecondaryTable;

import org.apache.logging.log4j.util.Strings;

import lombok.Getter;
import lombok.Setter;
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
public class GoBackDirectly_Old extends AggregateRoot {
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
	private Optional<WorkTypeCode> workTypeCD;
	/**
	 * 就業時間帯
	 */
	private Optional<WorkTimeCode> siftCD;
	/**
	 * 勤務を変更する
	 */
	private Optional<UseAtr> workChangeAtr;
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
	@Setter
	private Optional<WorkTimeGoBack> workTimeStart1;
	/**
	 * 勤務時間終了1
	 */
	@Setter
	private Optional<WorkTimeGoBack> workTimeEnd1;
	/**
	 * 勤務場所選択1
	 */
	private Optional<String> workLocationCD1;
	/**
	 * 勤務直行2
	 */
	private Optional<UseAtr> goWorkAtr2;
	/**
	 * 勤務直帰2
	 */
	private Optional<UseAtr> backHomeAtr2;
	/**
	 * 勤務時間開始2
	 */
	private Optional<WorkTimeGoBack> workTimeStart2;
	/**
	 * 勤務時間終了2
	 */
	private Optional<WorkTimeGoBack> workTimeEnd2;
	/**
	 * 勤務場所選択２
	 */
	private Optional<String> workLocationCD2;

	public GoBackDirectly_Old(String companyID, String appID, String workTypeCD, String siftCD, Integer workChangeAtr,
			Integer goWorkAtr1, Integer backHomeAtr1, Integer workTimeStart1, Integer workTimeEnd1, String workLocationCD1,
			Integer goWorkAtr2, Integer backHomeAtr2, Integer workTimeStart2, Integer workTimeEnd2, String workLocationCD2) {
		this.companyID = companyID;
		this.appID = appID;
		this.workTypeCD = Optional.ofNullable(Strings.isBlank(workTypeCD) ? null : new WorkTypeCode(workTypeCD));
		this.siftCD = Optional.ofNullable(Strings.isBlank(siftCD) ? null : new WorkTimeCode(siftCD));
		this.workChangeAtr = Optional.ofNullable(workChangeAtr == null ? null : EnumAdaptor.valueOf(workChangeAtr, UseAtr.class));
		this.goWorkAtr1 = EnumAdaptor.valueOf(goWorkAtr1, UseAtr.class);
		this.backHomeAtr1 = EnumAdaptor.valueOf(backHomeAtr1, UseAtr.class);
		this.workTimeStart1 = Optional.ofNullable(workTimeStart1 == null ? null : new WorkTimeGoBack(workTimeStart1));
		this.workTimeEnd1 = Optional.ofNullable(workTimeEnd1 == null ? null : new WorkTimeGoBack(workTimeEnd1));
		this.workLocationCD1 = Optional.ofNullable(Strings.isBlank(workLocationCD1) ? null : workLocationCD1);
		this.goWorkAtr2 = Optional.ofNullable(goWorkAtr2 == null ? null : EnumAdaptor.valueOf(goWorkAtr2, UseAtr.class));
		this.backHomeAtr2 = Optional.ofNullable(backHomeAtr2 == null ? null : EnumAdaptor.valueOf(backHomeAtr2, UseAtr.class));
		this.workTimeStart2 = Optional.ofNullable(workTimeStart2 == null ? null : new WorkTimeGoBack(workTimeStart2));
		this.workTimeEnd2 = Optional.ofNullable(workTimeEnd2 == null ? null : new WorkTimeGoBack(workTimeEnd2));
		this.workLocationCD2 = Optional.ofNullable(Strings.isBlank(workLocationCD2) ? null : workLocationCD2);
	}

	public GoBackDirectly_Old createFromJavaType(String companyID, String appID, String workTypeCD, String siftCD,
			Integer workChangeAtr, Integer goWorkAtr1, Integer backHomeAtr1, Integer workTimeStart1, Integer workTimeEnd1,
			String workLocationCD1, Integer goWorkAtr2, Integer backHomeAtr2, Integer workTimeStart2, Integer workTimeEnd2,
			String workLocationCD2) {
		return new GoBackDirectly_Old(companyID, appID, workTypeCD, siftCD, workChangeAtr, goWorkAtr1, backHomeAtr1,
				workTimeStart1, workTimeEnd1, workLocationCD1, goWorkAtr2, backHomeAtr2, workTimeStart2, workTimeEnd2,
				workLocationCD2);
	}
}