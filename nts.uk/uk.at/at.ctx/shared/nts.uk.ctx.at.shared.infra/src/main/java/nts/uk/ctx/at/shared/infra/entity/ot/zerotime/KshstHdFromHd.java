package nts.uk.ctx.at.shared.infra.entity.ot.zerotime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.HdFromHd;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author phongtq 休日から休日への0時跨ぎ設定
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHST_HD_FROM_HD")
public class KshstHdFromHd extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstHdFromHdPK kshstOverDayHdSetPK;

	/** 変更後の法定内休出NO */
	@Column(name = "LEGAL_HD_NO")
	public int calcOverDayEnd;

	/** 変更後の法定外休出NO */
	@Column(name = "NON_LEGAL_HD_NO")
	public int statutoryHd;

	/** 変更後の祝日休出NO */
	@Column(name = "NON_LEGAL_PUBLIC_HD_NO")
	public int excessHd;

	@ManyToOne
	@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
	public KshstZeroTimeSet overDayCalcSet;

	@Override
	protected Object getKey() {
		return kshstOverDayHdSetPK;
	}

	public HdFromHd toDomain() {
		return HdFromHd.createFromJavaType(this.kshstOverDayHdSetPK.companyId,
				this.kshstOverDayHdSetPK.holidayWorkFrameNo, this.calcOverDayEnd, this.statutoryHd, this.excessHd);
	}

	public static KshstHdFromHd toEntity(HdFromHd domain) {
		return new KshstHdFromHd(new KshstHdFromHdPK(domain.getCompanyId(), domain.getHolidayWorkFrameNo()),
				domain.getCalcOverDayEnd(), domain.getStatutoryHd(), domain.getExcessHd());
	}

	public KshstHdFromHd(KshstHdFromHdPK kshstOverDayHdSetPK, int calcOverDayEnd, int statutoryHd, int excessHd) {
		super();
		this.kshstOverDayHdSetPK = kshstOverDayHdSetPK;
		this.calcOverDayEnd = calcOverDayEnd;
		this.statutoryHd = statutoryHd;
		this.excessHd = excessHd;
	}

}
