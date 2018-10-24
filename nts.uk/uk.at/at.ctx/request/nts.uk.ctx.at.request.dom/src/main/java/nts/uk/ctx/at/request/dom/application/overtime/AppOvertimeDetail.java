package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;

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
	 * 限度エラー時間
	 */
	private LimitOneMonth limitErrorTime;

	/**
	 * 限度アラーム時間
	 */
	private LimitOneMonth limitAlarmTime;

	/**
	 * 特例限度エラー時間
	 */
	private Optional<LimitOneMonth> exceptionLimitErrorTime;

	/**
	 * 特例限度アラーム時間
	 */
	private Optional<LimitOneMonth> exceptionLimitAlarmTime;

	/**
	 * 36年間超過月
	 */
	private List<Year36OverMonth> year36OverMonth;

	/**
	 * 36年間超過回数
	 */
	private NumberOfMonth numOfYear36Over;

	public AppOvertimeDetail(String cid, String appId, int applicationTime, int yearMonth, int actualTime,
			int limitErrorTime, int limitAlarmTime, Integer exceptionLimitErrorTime, Integer exceptionLimitAlarmTime,
			List<Integer> overMonth, int numOfYear36Over) {
		super();
		this.cid = cid;
		this.appId = appId;
		this.setApplicationTime(applicationTime);
		this.yearMonth = new YearMonth(yearMonth);
		this.setActualTime(actualTime);
		this.setLimitErrorTime(limitErrorTime);
		this.setLimitAlarmTime(limitAlarmTime);
		this.setExceptionLimitErrorTime(exceptionLimitErrorTime);
		this.setExceptionLimitAlarmTime(exceptionLimitAlarmTime);
		this.year36OverMonth = new ArrayList<>();
		for (Integer month : overMonth) {
			this.year36OverMonth.add(new Year36OverMonth(cid, appId, month));
		}
		this.setNumOfYear36Over(numOfYear36Over);
	}

	public AppOvertimeDetail() {
		super();
		this.cid = "";
		this.appId = "";
		this.setApplicationTime(0);
		this.yearMonth = new YearMonth(0);
		this.setActualTime(0);
		this.setLimitErrorTime(0);
		this.setLimitAlarmTime(0);
		this.setExceptionLimitErrorTime(null);
		this.setExceptionLimitAlarmTime(null);
		this.year36OverMonth = new ArrayList<>();
		this.setNumOfYear36Over(0);
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

	public void setLimitErrorTime(int limitErrorTime) {
		this.limitErrorTime = new LimitOneMonth(limitErrorTime);
	}

	public void setLimitAlarmTime(int limitAlarmTime) {
		this.limitAlarmTime = new LimitOneMonth(limitAlarmTime);
	}

	public void setExceptionLimitErrorTime(Integer exceptionLimitErrorTime) {
		this.exceptionLimitErrorTime = Objects.isNull(exceptionLimitErrorTime) ? Optional.empty()
				: Optional.ofNullable(new LimitOneMonth(exceptionLimitErrorTime));
	}

	public void setExceptionLimitAlarmTime(Integer exceptionLimitAlarmTime) {
		this.exceptionLimitAlarmTime = Objects.isNull(exceptionLimitAlarmTime) ? Optional.empty()
				: Optional.ofNullable(new LimitOneMonth(exceptionLimitAlarmTime));
	}

	public void setNumOfYear36Over(int numOfYear36Over) {
		this.numOfYear36Over = new NumberOfMonth(numOfYear36Over);
	}

}
