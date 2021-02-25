package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.Application;

/**
 * 古い遅刻早退取消申請 (古いクラス → 削除予定 → 使わないでください (Old Class → Delete plan → Please don't use it))
 * @author hieult
 */
@Getter
@AllArgsConstructor
@Builder
public class LateOrLeaveEarly extends AggregateRoot {

	@Setter
	private Application application;

	/** 実績取消区分 */
	@Setter
	private int actualCancelAtr;
	/** 早退1 */
	@Setter
	private Select early1;
	/** 早退時刻1 */
	@Setter
	private TimeDay earlyTime1;
	/** 遅刻1 */
	@Setter
	private Select late1;
	/** 遅刻時刻1 */
	@Setter
	private TimeDay lateTime1;
	/** 早退2 */
	@Setter
	private Select early2;
	/** 早退時刻2 */
	@Setter
	private TimeDay earlyTime2;
	/** 遅刻2 */
	@Setter
	private Select late2;
	/** 遅刻時刻2 */
	@Setter
	private TimeDay lateTime2;

	public void changeApplication(int actualCancelAtr, int early1, int earlyTime1, int late1, int lateTime1, int early2,
			int earlyTime2, int late2, int lateTime2) {
		this.actualCancelAtr = actualCancelAtr;
		this.early1 = EnumAdaptor.valueOf(early1, Select.class);
		this.earlyTime1 = EnumAdaptor.valueOf(earlyTime1, TimeDay.class);
		this.late1 = EnumAdaptor.valueOf(late1, Select.class);
		this.lateTime1 = EnumAdaptor.valueOf(lateTime1, TimeDay.class);
		this.early2 = EnumAdaptor.valueOf(early2, Select.class);
		this.earlyTime2 = EnumAdaptor.valueOf(earlyTime2, TimeDay.class);
		this.late2 = EnumAdaptor.valueOf(late2, Select.class);
		this.lateTime2 = EnumAdaptor.valueOf(lateTime2, TimeDay.class);
	}

	public Integer getLateTime1AsMinutes() {
		return lateTime1 == null ? null : this.lateTime1.valueAsMinutes();
	}

	public Integer getLateTime2AsMinutes() {
		return lateTime2 == null ? null : this.lateTime2.valueAsMinutes();
	}

	public Integer getEarlyTime1AsMinutes() {
		return earlyTime1 == null ? null : this.earlyTime1.valueAsMinutes();
	}

	public Integer getEarlyTime2AsMinutes() {
		return earlyTime2 == null ? null : this.earlyTime2.valueAsMinutes();
	}

}