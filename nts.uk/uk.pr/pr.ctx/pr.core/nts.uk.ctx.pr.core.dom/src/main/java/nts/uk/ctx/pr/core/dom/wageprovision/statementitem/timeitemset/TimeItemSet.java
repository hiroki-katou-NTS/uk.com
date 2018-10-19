package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.AverageWageAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemNameCode;
import nts.uk.shr.com.primitive.Memo;

/**
 *
 * @author thanh.tq 勤怠項目設定
 *
 */
@Getter
public class TimeItemSet extends AggregateRoot {

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
	private Optional<Memo> note;

    /**
     * 項目名コード
     */
    private ItemNameCode itemNameCode;

    /**
     * カテゴリ区分
     */
    private CategoryAtr categoryAtr;

    /**
     * 勤怠アラーム範囲設定
     */
    private DetailTimeErrorAlarmRangeSetting alarmRangeSetting;

    /**
     * 勤怠エラー範囲設定
     */
    private DetailTimeErrorAlarmRangeSetting errorRangeSetting;

	public TimeItemSet(String cid, String salaryItemId, int averageWageAtr, int workingDaysPerYear, int timeCountAtr,
			String note) {
		super();
		this.cid = cid;
		this.salaryItemId = salaryItemId;
		this.averageWageAtr = Optional.of(EnumAdaptor.valueOf(averageWageAtr, AverageWageAtr.class));
		this.workingDaysPerYear = Optional
				.ofNullable(EnumAdaptor.valueOf(workingDaysPerYear, ClassifiedWorkingDaysPerYear.class));
		this.timeCountAtr = EnumAdaptor.valueOf(timeCountAtr, TimeCountAtr.class);
		this.note = note == null ? Optional.empty() : Optional.of(new Memo(note));
	}

}
