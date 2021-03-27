package nts.uk.ctx.at.shared.dom.scherec.application.overtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
/**
 * 36協定月間時間
 * @author thanhnx
 *
 */
@AllArgsConstructor
@Getter
public class Time36AgreeMonthShare extends DomainObject {
	
	/*
	 * 実績時間
	 */
	private AttendanceTimeMonth actualTime;
	
	/*
	 * 限度アラーム時間
	 */
	private AgreementOneMonthTime limitAlarmTime;
	
	/*
	 * 限度エラー時間
	 */
	private AgreementOneMonthTime limitErrorTime;
	
	/*
	 * 36年間超過回数
	 */
	private NumberOfMonthShare numOfYear36Over;
	
	/*
	 * 36年間超過月
	 */
	private List<YearMonth> year36OverMonth = new ArrayList<>();
	
	/*
	 * 特例限度アラーム時間
	 */
	private Optional<AgreementOneMonthTime> exceptionLimitAlarmTime;
	
	/*
	 * 特例限度エラー時間
	 */
	private Optional<AgreementOneMonthTime> exceptionLimitErrorTime;
	
	public Time36AgreeMonthShare(){
		this.actualTime = new AttendanceTimeMonth(0);
		this.limitAlarmTime = new AgreementOneMonthTime(0);
		this.limitErrorTime = new AgreementOneMonthTime(0);
		this.numOfYear36Over = new NumberOfMonthShare(0);
		this.year36OverMonth = new ArrayList<>();
		this.exceptionLimitAlarmTime = Optional.empty();
		this.exceptionLimitErrorTime = Optional.empty();
	}
	
	public void setActualTime(Integer actualTime){
		this.actualTime = new AttendanceTimeMonth(actualTime);
	}

	public void setLimitAlarmTime(Integer limitAlarmTime){
		this.limitAlarmTime = new AgreementOneMonthTime(limitAlarmTime);
	}
	
	public void setLimitErrorTime(Integer limitErrorTime){
		this.limitErrorTime = new AgreementOneMonthTime(limitErrorTime);
	}
	
	public void setNumOfYear36Over(Integer numOfYear36Over){
		this.numOfYear36Over = new NumberOfMonthShare(numOfYear36Over);
	}
	
	public void setYear36OverMonth(List<YearMonth> year36OverMonth){
		this.year36OverMonth = year36OverMonth;
	}

	public void setExceptionLimitAlarmTime(Integer exceptionLimitAlarmTime){
		this.exceptionLimitAlarmTime = exceptionLimitAlarmTime==null ? Optional.empty() : Optional.ofNullable(new AgreementOneMonthTime(exceptionLimitAlarmTime));
	}
	
	public void setExceptionLimitErrorTime(Integer exceptionLimitErrorTime){
		this.exceptionLimitErrorTime = exceptionLimitErrorTime==null ? Optional.empty() : Optional.ofNullable(new AgreementOneMonthTime(exceptionLimitErrorTime));
	}
	
}
