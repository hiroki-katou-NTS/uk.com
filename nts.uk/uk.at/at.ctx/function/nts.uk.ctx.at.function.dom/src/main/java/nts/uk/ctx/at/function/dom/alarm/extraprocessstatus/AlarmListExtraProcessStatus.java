package nts.uk.ctx.at.function.dom.alarm.extraprocessstatus;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
/**
 * アラームリスト抽出処理状況
 * @author tutk
 *
 */
@Getter
public class AlarmListExtraProcessStatus extends AggregateRoot {
	/**会社ID */
	private String companyID;
	/** 開始年月日*/
	private GeneralDate startDate;
	/** 開始時刻 */
	private int startTime;
	/**実行社員*/
	private String employeeID;
	/**終了年月日*/
	private Optional<GeneralDate> endDate;
	/**終了時刻*/
	private Integer endTime;

	public AlarmListExtraProcessStatus(String companyID, GeneralDate startDate, int startTime, String employeeID,
			Optional<GeneralDate> endDate, Integer endTime) {
		super();
		this.companyID = companyID;
		this.startDate = startDate;
		this.startTime = startTime;
		this.employeeID = employeeID;
		this.endDate = endDate;
		this.endTime = endTime;
	}
	
	public void setEndDateAndEndTime(GeneralDate endDate,int endTime) {
		this.endDate = Optional.of(endDate);
		this.endTime = endTime;
	}

}
