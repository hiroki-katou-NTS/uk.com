package nts.uk.ctx.at.shared.dom.remainingnumber.work;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

/**
 * 残数発生使用明細
 * 
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OccurrenceUseDetail {
	/** 日数 */
	private double days = 0;
	/** 利用区分 */
	private boolean useAtr = false;
	/** 勤務種類の分類 */
	private WorkTypeClassification workTypeAtr;
	/** 時間消化 */
	private List<VacationUsageTimeDetail> vacationUsageTimeDetails = new ArrayList<>();
	/** 代休時間 */
	private Optional<AttendanceTime> substituteHolidayTime = Optional.empty();

	public OccurrenceUseDetail(double days, boolean useAtr, WorkTypeClassification workTypeAtr) {
		super();
		this.days = days;
		this.useAtr = useAtr;
		this.workTypeAtr = workTypeAtr;
	}

}
