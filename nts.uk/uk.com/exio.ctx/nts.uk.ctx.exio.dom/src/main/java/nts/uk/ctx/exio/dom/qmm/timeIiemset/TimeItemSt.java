package nts.uk.ctx.exio.dom.qmm.timeIiemset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 勤怠項目設定
 */
@Getter
public class TimeItemSt extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * 平均賃金区分
	 */
	private Optional<AverageWageAtr> averageWageAtr;

	/**
	 * 年間所定労働日数区分
	 */
	private Optional<ClassifiedWorkingDaysPerYear> workingDaysPerYear;

	/**
	 * 時間回数区分
	 */
	private TimeCountAtr timeCountAtr;

	/**
	 * 備考
	 */
	private Optional<String> note;

	public TimeItemSt(String cid, String salaryItemId, int averageWageAtr, int workingDaysPerYear, int timeCountAtr,
			String note) {
		super();
		this.cid = cid;
		this.salaryItemId = salaryItemId;
		this.averageWageAtr = Optional.of(EnumAdaptor.valueOf(averageWageAtr, AverageWageAtr.class));
		this.workingDaysPerYear = Optional
				.ofNullable(EnumAdaptor.valueOf(workingDaysPerYear, ClassifiedWorkingDaysPerYear.class));
		this.timeCountAtr = EnumAdaptor.valueOf(timeCountAtr, TimeCountAtr.class);
		this.note = Optional.ofNullable(note);
	}

}
