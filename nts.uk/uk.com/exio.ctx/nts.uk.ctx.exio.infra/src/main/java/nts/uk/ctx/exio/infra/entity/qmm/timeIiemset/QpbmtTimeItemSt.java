package nts.uk.ctx.exio.infra.entity.qmm.timeIiemset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.qmm.timeIiemset.TimeItemSt;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 勤怠項目設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_TIME_ITEM_ST")
public class QpbmtTimeItemSt extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtTimeItemStPk timeItemStPk;

	/**
	 * 平均賃金区分
	 */
	@Basic(optional = true)
	@Column(name = "AVERAGE_WAGE_ATR")
	public Integer averageWageAtr;

	/**
	 * 年間所定労働日数区分
	 */
	@Basic(optional = true)
	@Column(name = "WORKING_DAYS_PER_YEAR")
	public Integer workingDaysPerYear;

	/**
	 * 時間回数区分
	 */
	@Basic(optional = false)
	@Column(name = "TIME_COUNT_ATR")
	public Integer timeCountAtr;

	/**
	 * 備考
	 */
	@Basic(optional = true)
	@Column(name = "NOTE")
	public String note;

	@Override
	protected Object getKey() {
		return timeItemStPk;
	}

	public TimeItemSt toDomain() {
		return new TimeItemSt(timeItemStPk.cid, timeItemStPk.salaryItemId, averageWageAtr, workingDaysPerYear,
				timeCountAtr, note);
	}

	public static QpbmtTimeItemSt toEntity(TimeItemSt domain) {
		return new QpbmtTimeItemSt(new QpbmtTimeItemStPk(domain.getCid(), domain.getSalaryItemId()),
				domain.getAverageWageAtr().map(i -> i.value).orElse(null),
				domain.getWorkingDaysPerYear().map(i -> i.value).orElse(null), domain.getTimeCountAtr().value,
				domain.getNote().orElse(null));
	}

}
