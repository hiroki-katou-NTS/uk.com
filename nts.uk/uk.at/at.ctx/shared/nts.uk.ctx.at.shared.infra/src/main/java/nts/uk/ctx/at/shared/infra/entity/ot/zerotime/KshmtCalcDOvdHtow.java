package nts.uk.ctx.at.shared.infra.entity.ot.zerotime;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.HdFromWeekday;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author phongtq 休日から平日への0時跨ぎ設定
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_CALC_D_OVD_HTOW")
public class KshmtCalcDOvdHtow extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshmtCalcDOvdHtowPK kshstOverdayHdAttSetPK;

	/** 変更後の残業枠NO */
	@Column(name = "OVERTIME_FRAME_NO")
	public BigDecimal overWorkNo;

	@ManyToOne
	@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
	public KshmtCalcDOvd overDayCalcSet;

	@Override
	protected Object getKey() {
		return kshstOverdayHdAttSetPK;
	}

	public KshmtCalcDOvdHtow(KshmtCalcDOvdHtowPK kshstOverdayHdAttSetPK, BigDecimal overtimeFrameNo) {
		super();
		this.kshstOverdayHdAttSetPK = kshstOverdayHdAttSetPK;
		this.overWorkNo = overtimeFrameNo;
	}

	/**
	 * Convert to Domain Holiday
	 * 
	 * @param kshstOverdayHdAttSet
	 * @return
	 */
	public HdFromWeekday toDomain() {
		return HdFromWeekday.createFromJavaType(this.kshstOverdayHdAttSetPK.companyId,
				this.kshstOverdayHdAttSetPK.holidayWorkFrameNo, this.overWorkNo);
	}

	public static KshmtCalcDOvdHtow toEntity(HdFromWeekday domain) {
		return new KshmtCalcDOvdHtow(
				new KshmtCalcDOvdHtowPK(domain.getCompanyId(), domain.getHolidayWorkFrameNo()),
				domain.getOverWorkNo());
	}
}
