package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 時間外時間の詳細
 */
@Getter
public class AppOvertimeDetail extends DomainObject {

	/**
	 * 会社ID
	 */
	@Setter
	private String cid;

	/**
	 * 申請ID
	 */
	@Setter
	private String appId;

	/**
	 * 申請時間
	 */
	private AttendanceTimeMonth applicationTime;

	/**
	 * 年月
	 */
	@Setter
	private YearMonth yearMonth;

	/**
	 * 実績時間
	 */
	private AttendanceTimeMonth actualTime;

	/**
	 * 36時間
	 */
	private AttendanceTimeMonth time36;

	/**
	 * 36年間超過月
	 */
	private List<Year36OverMonth> year36OverMonth;

	/**
	 * 36年間超過回数
	 */
	private NumberOfMonth numOfYear36Over;

	public AppOvertimeDetail(String cid, String appId, int applicationTime, int yearMonth, int actualTime, int time36,
			List<Integer> overMonth, int numOfYear36Over) {
		super();
		this.cid = cid;
		this.appId = appId;
		this.applicationTime = new AttendanceTimeMonth(applicationTime);
		this.yearMonth = new YearMonth(yearMonth);
		this.actualTime = new AttendanceTimeMonth(actualTime);
		this.time36 = new AttendanceTimeMonth(time36);
		this.year36OverMonth = new ArrayList<>();
		for (Integer month : overMonth) {
			this.year36OverMonth.add(new Year36OverMonth(cid, appId, month));
		}
		this.numOfYear36Over = new NumberOfMonth(numOfYear36Over);
	}

	public AppOvertimeDetail() {
		super();
		this.cid = "";
		this.appId = "";
		this.applicationTime = new AttendanceTimeMonth(0);
		this.yearMonth = new YearMonth(0);
		this.actualTime = new AttendanceTimeMonth(0);
		this.time36 = new AttendanceTimeMonth(0);
		this.numOfYear36Over = new NumberOfMonth(0);
		this.year36OverMonth = new ArrayList<>();
	}

	/**
	 * ３６上限チェック
	 */
	public boolean check36UpperLimit() {
		return this.time36.v() < this.actualTime.v() + this.applicationTime.v();
	}

	public boolean existOverMonth(YearMonth month) {
		return this.year36OverMonth.stream().filter(x -> x.getOverMonth().equals(month)).count() > 0;
	}

	public void plusNumOfYear36Over() {
		this.numOfYear36Over = new NumberOfMonth(this.numOfYear36Over.v() + 1);
	}

	public void addOverMonth(YearMonth month) {
		Year36OverMonth overMonth = new Year36OverMonth(this.cid, this.appId, month.v());
		this.year36OverMonth.add(overMonth);
	}

	public void setYear36OverMonth(List<YearMonth> monthList) {
		for (YearMonth month : monthList) {
			this.addOverMonth(month);
		}
	}

	public void setApplicationTime(int applicationTime) {
		this.applicationTime = new AttendanceTimeMonth(applicationTime);
	}

	public void setActualTime(int actualTime) {
		this.actualTime = new AttendanceTimeMonth(actualTime);
	}

	public void setTime36(int time36) {
		this.time36 = new AttendanceTimeMonth(time36);
	}

	public void setNumOfYear36Over(int numOfYear36Over) {
		this.numOfYear36Over = new NumberOfMonth(numOfYear36Over);
	}

}
