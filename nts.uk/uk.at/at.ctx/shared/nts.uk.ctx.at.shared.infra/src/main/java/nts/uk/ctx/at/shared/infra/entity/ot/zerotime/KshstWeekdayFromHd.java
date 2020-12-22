package nts.uk.ctx.at.shared.infra.entity.ot.zerotime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.WeekdayHoliday;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author phongtq 平日から休日の0時跨ぎ設定
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHST_WEEKDAY_FROM_HD")
public class KshstWeekdayFromHd extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstWeekdayFromHdPK kshstWeekdayHdPK;

	/** 変更後の法定内休出NO */
	@Column(name = "LEGAL_HD_NO")
	public int weekdayNo;

	/** 変更後の法定外休出NO */
	@Column(name = "NON_LEGAL_HD_NO")
	public int excessHolidayNo;

	/** 変更後の祝日休出NO */
	@Column(name = "NON_LEGAL_PUBLIC_HD_NO")
	public int excessSphdNo;

	@ManyToOne
	@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
	public KshstZeroTimeSet overDayCalcSet;

	@Override
	protected Object getKey() {
		return kshstWeekdayHdPK;
	}

	public WeekdayHoliday toDomain() {
		return WeekdayHoliday.createFromJavaType(this.kshstWeekdayHdPK.companyId, this.kshstWeekdayHdPK.overworkFrameNo,
				this.weekdayNo, this.excessHolidayNo, this.excessSphdNo);
	}

	public static KshstWeekdayFromHd toEntity(WeekdayHoliday domain) {
		return new KshstWeekdayFromHd(new KshstWeekdayFromHdPK(domain.getCompanyId(), domain.getOverworkFrameNo()),
				domain.getWeekdayNo(), domain.getExcessHolidayNo(), domain.getExcessSphdNo());
	}

	public KshstWeekdayFromHd(KshstWeekdayFromHdPK kshstWeekdayHdPK, int weekdayNo, int excessHolidayNo,
			int excessSphdNo) {
		super();
		this.kshstWeekdayHdPK = kshstWeekdayHdPK;
		this.weekdayNo = weekdayNo;
		this.excessHolidayNo = excessHolidayNo;
		this.excessSphdNo = excessSphdNo;
	}
}
