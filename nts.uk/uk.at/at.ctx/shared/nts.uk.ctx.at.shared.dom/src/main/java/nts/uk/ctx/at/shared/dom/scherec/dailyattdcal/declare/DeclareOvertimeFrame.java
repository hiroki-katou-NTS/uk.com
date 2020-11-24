package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 申告残業枠
 * @author shuichi_ishida
 */
@Getter
public class DeclareOvertimeFrame {

	/** 早出残業 */
	private Optional<OverTimeFrameNo> earlyOvertime;
	/** 早出残業深夜 */
	private Optional<OverTimeFrameNo> earlyOvertimeMn;
	/** 普通残業 */
	private Optional<OverTimeFrameNo> overtime;
	/** 普通残業深夜 */
	private Optional<OverTimeFrameNo> overtimeMn;
	
	/**
	 * コンストラクタ
	 */
	public DeclareOvertimeFrame(){
		this.earlyOvertime = Optional.empty();
		this.earlyOvertimeMn = Optional.empty();
		this.overtime = Optional.empty();
		this.overtimeMn = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param earlyOvertime 早出残業
	 * @param earlyOvertimeMn 早出残業深夜
	 * @param overtime 普通残業
	 * @param overtimeMn 普通残業深夜
	 * @return 申告残業枠
	 */
	public static DeclareOvertimeFrame of(
			OverTimeFrameNo earlyOvertime,
			OverTimeFrameNo earlyOvertimeMn,
			OverTimeFrameNo overtime,
			OverTimeFrameNo overtimeMn){
		
		DeclareOvertimeFrame myclass = new DeclareOvertimeFrame();
		myclass.earlyOvertime = Optional.ofNullable(earlyOvertime);
		myclass.earlyOvertimeMn = Optional.ofNullable(earlyOvertimeMn);
		myclass.overtime = Optional.ofNullable(overtime);
		myclass.overtimeMn = Optional.ofNullable(overtimeMn);
		return myclass;
	}
	
	/**
	 * ファクトリー (Java型)
	 * @param earlyOvertime 早出残業
	 * @param earlyOvertimeMn 早出残業深夜
	 * @param overtime 普通残業
	 * @param overtimeMn 普通残業深夜
	 * @return 申告残業枠
	 */
	public static DeclareOvertimeFrame createFromJavaType(
			Integer earlyOvertime,
			Integer earlyOvertimeMn,
			Integer overtime,
			Integer overtimeMn){
		
		DeclareOvertimeFrame myclass = new DeclareOvertimeFrame();
		myclass.earlyOvertime = (earlyOvertime == null ? Optional.empty()
				: Optional.ofNullable(new OverTimeFrameNo(earlyOvertime)));
		myclass.earlyOvertimeMn = (earlyOvertimeMn == null ? Optional.empty()
				: Optional.ofNullable(new OverTimeFrameNo(earlyOvertimeMn)));
		myclass.overtime = (overtime == null ? Optional.empty()
				: Optional.ofNullable(new OverTimeFrameNo(overtime)));
		myclass.overtimeMn = (overtimeMn == null ? Optional.empty()
				: Optional.ofNullable(new OverTimeFrameNo(overtimeMn)));
		return myclass;
	}
}
