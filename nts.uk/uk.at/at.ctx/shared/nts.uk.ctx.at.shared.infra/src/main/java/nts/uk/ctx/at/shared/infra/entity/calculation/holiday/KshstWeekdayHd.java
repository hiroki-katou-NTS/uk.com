package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_WEEKDAY_HD ")
public class KshstWeekdayHd extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstWeekdayHdPK kshstWeekdayHdPK;
	
	/** 変更後の法定内休出NO */
	@Column(name = "LEGAL_HD_NO")
	public int weekdayNo;
	
	/** 変更後の法定外休出NO*/
	@Column(name = "NON_LEGAL_HD_NO")
	public int excessHolidayNo;
	
	/** 変更後の祝日休出NO*/
	@Column(name = "NON_LEGAL_PUBLIC_HD_NO")
	public int excessSphdNo;
	
	@ManyToOne
	@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false)
	public KshstZeroTimeSet overDayCalcSet;
	
	@Override
	protected Object getKey() {
		return kshstWeekdayHdPK;
	}
}
