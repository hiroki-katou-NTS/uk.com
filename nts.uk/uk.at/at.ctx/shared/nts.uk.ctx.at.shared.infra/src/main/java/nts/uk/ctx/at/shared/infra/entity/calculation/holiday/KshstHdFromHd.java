package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

/**
 * @author phongtq
 * 休日から休日への0時跨ぎ設定
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_HD_FROM_HD ")
public class KshstHdFromHd extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstHdFromHdPK kshstOverDayHdSetPK;

	/** 変更後の法定内休出NO */
	@Column(name = "WEEKDAY_NO")
	public int legalHdNo;

	/** 変更後の法定外休出NO */
	@Column(name = "NON_LEGAL_HD_NO")
	public int nonLegalHdNo;

	/** 変更後の祝日休出NO */
	@Column(name = "NON_LEGAL_PUBLIC_HD_NO")
	public int nonLegalPublicHdNo;
	
	@ManyToOne
	@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
	public KshstZeroTimeSet overDayCalcSet;

	@Override
	protected Object getKey() {
		return kshstOverDayHdSetPK;
	}
}
