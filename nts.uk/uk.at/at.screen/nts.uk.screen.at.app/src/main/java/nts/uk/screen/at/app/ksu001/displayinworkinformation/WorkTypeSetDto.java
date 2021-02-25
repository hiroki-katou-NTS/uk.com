package nts.uk.screen.at.app.ksu001.displayinworkinformation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
/**
 * 
 * @author laitv
 *
 */
@Getter
@NoArgsConstructor
public class WorkTypeSetDto {

	/**
	 * 会社ID
	 */
	public String companyId;
	
	/**
	 * 勤務種類コード
	 */
	public String workTypeCd;
	
	/**
	 * 勤務の単位
	 */
	public int workAtr;
	
	/**
	 * 公休を消化する
	 */
	public int digestPublicHd;
	
	/**
	 * 休日区分
	 */
	public int holidayAtr;
	
	/**
	 * 休日日数をカウントする
	 */
	public int countHodiday;
	
	/**
	 * 休業区分
	 */
	public int closeAtr;
	
	/**
	 * 欠勤枠
	 */
	public int sumAbsenseNo;
	
	/**
	 * 特別休暇枠
	 */
	public int sumSpHodidayNo;
	
	/**
	 * 退勤時刻を直帰とする
	 */
	public int timeLeaveWork;
	
	/**
	 * 出勤時刻を直行とする
	 */
	public int attendanceTime;
	
	/**
	 * 代休を発生させる
	 */
	public int genSubHodiday;
	
	/**
	 * 日勤・夜勤時間を求める
	 */
	public int dayNightTimeAsk;

	
	public WorkTypeSetDto(WorkTypeSet workTypeSet) {
		super();
		this.companyId = workTypeSet.getCompanyId();
		this.workTypeCd = workTypeSet.getWorkTypeCd().v();
		this.workAtr = workTypeSet.getWorkAtr() == null ? null : workTypeSet.getWorkAtr().value;
		this.digestPublicHd = workTypeSet.getDigestPublicHd() == null ? null : workTypeSet.getDigestPublicHd().value;
		this.holidayAtr = workTypeSet.getHolidayAtr() == null ? null : workTypeSet.getHolidayAtr().value;
		this.countHodiday = workTypeSet.getCountHodiday() == null ? null : workTypeSet.getCountHodiday().value;
		this.closeAtr = workTypeSet.getCloseAtr() == null ? null : workTypeSet.getCloseAtr().value ;
		this.sumAbsenseNo = workTypeSet.getSumAbsenseNo();
		this.sumSpHodidayNo = workTypeSet.getSumSpHodidayNo();
		this.timeLeaveWork = workTypeSet.getTimeLeaveWork() == null ? null : workTypeSet.getTimeLeaveWork().value;
		this.attendanceTime = workTypeSet.getAttendanceTime() == null ? null : workTypeSet.getAttendanceTime().value;
		this.genSubHodiday = workTypeSet.getGenSubHodiday() == null ? null : workTypeSet.getGenSubHodiday().value;
		this.dayNightTimeAsk = workTypeSet.getDayNightTimeAsk() == null ? null : workTypeSet.getDayNightTimeAsk().value;
	}
	
}
