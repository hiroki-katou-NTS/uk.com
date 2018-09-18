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
	
	/**ID*/
	private String extraProcessStatusID;
	/**会社ID */
	private String companyID;
	/** 開始年月日*/
	private GeneralDate startDate;
	/** 開始時刻 */
	private int startTime;
	/**実行社員*/
	private Optional<String> employeeID;
	/**終了年月日*/
	private Optional<GeneralDate> endDate;
	/**終了時刻*/
	private Optional<Integer> endTime;
	/**状態*/
	private ExtractionState status;


	
	public void setEndDateAndEndTime(GeneralDate endDate,Integer endTime) {
		this.endDate = Optional.ofNullable(endDate);
		this.endTime = Optional.ofNullable(endTime);
	}

	public void setEndDateAndEndTime(GeneralDate endDate,Integer endTime,ExtractionState status) {
		this.endDate = Optional.ofNullable(endDate);
		this.endTime = Optional.ofNullable(endTime);
		this.status = status;
	}

	public AlarmListExtraProcessStatus(String extraProcessStatusID, String companyID, GeneralDate startDate,
			int startTime, String employeeID, GeneralDate endDate, Integer endTime,
			ExtractionState status) {
		super();
		this.extraProcessStatusID = extraProcessStatusID;
		this.companyID = companyID;
		this.startDate = startDate;
		this.startTime = startTime;
		this.employeeID = Optional.ofNullable(employeeID);
		this.endDate = Optional.ofNullable(endDate);
		this.endTime = Optional.ofNullable(endTime);
		this.status = status;
	}



	public void setStatus(ExtractionState status) {
		this.status = status;
	}
	
}
