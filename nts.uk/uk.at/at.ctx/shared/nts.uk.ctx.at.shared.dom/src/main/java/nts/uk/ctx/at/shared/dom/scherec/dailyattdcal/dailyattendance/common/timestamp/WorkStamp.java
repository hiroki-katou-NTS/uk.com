package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 勤怠打刻
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.Common.勤怠打刻
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class WorkStamp extends DomainObject{
	

	/*
	 * 時刻
	 */
	private WorkTimeInformation timeDay;
	
	/*
	 * 場所コード
	 */
	private Optional<WorkLocationCD> locationCode;
	

	public WorkStamp(TimeWithDayAttr timeWithDay, WorkLocationCD locationCode,
			TimeChangeMeans timeChangeMeans,EngravingMethod engravingMethod) {
		super();
		this.timeDay = new WorkTimeInformation(new ReasonTimeChange(timeChangeMeans, engravingMethod), timeWithDay);
		this.locationCode = Optional.ofNullable(locationCode);
	}
	
	public WorkStamp(TimeWithDayAttr timeWithDay, WorkLocationCD locationCode,
			TimeChangeMeans timeChangeMeans) {
		super();
		this.timeDay = new WorkTimeInformation(new ReasonTimeChange(timeChangeMeans, null), timeWithDay);
		this.locationCode = Optional.ofNullable(locationCode);
	}
	
	
	public void setPropertyWorkStamp(TimeWithDayAttr timeWithDay, WorkLocationCD locationCode,
			TimeChangeMeans timeChangeMeans){
		this.timeDay = new WorkTimeInformation(new ReasonTimeChange(timeChangeMeans, null), timeWithDay);
		this.locationCode = Optional.ofNullable(locationCode);
		
	}
	
	public boolean isFromSPR() {
		return this.timeDay.getReasonTimeChange().getTimeChangeMeans() == TimeChangeMeans.SPR_COOPERATION;
	}
	
	public void setStampFromPcLogOn(TimeWithDayAttr PcLogOnStamp) {
		this.timeDay.setTimeWithDay(Optional.ofNullable(PcLogOnStamp));
	}

	public WorkStamp(WorkTimeInformation timeDay,
			Optional<WorkLocationCD> locationCode) {
		super();
		this.timeDay = timeDay;
		this.locationCode = locationCode;
	}
	
	/**
	 * 以下
	 * @param compareValue
	 * @return
	 */
	public boolean lessThan(WorkStamp compareValue) {
		if(this.getTimeDay().getTimeWithDay().isPresent() && compareValue.getTimeDay().getTimeWithDay().isPresent()) {
			return this.getTimeDay().getTimeWithDay().get().lessThan(compareValue.getTimeDay().getTimeWithDay().get());
		}

		return false;
	}
	

}
