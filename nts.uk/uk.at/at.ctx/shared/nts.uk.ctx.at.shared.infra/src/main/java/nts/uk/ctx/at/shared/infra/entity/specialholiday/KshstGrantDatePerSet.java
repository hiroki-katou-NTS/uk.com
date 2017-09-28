package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANTDATE_PER_SET")
public class KshstGrantDatePerSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KshstGrantDatePerSetPK kshstGrantDatePerSetPK;
	
	/* 月数 */
	@Column(name = "GRANT_DATE_M")
	public int grantDateMonth;

	/* 年数 */
	@Column(name = "GRANT_DATE_Y")
	public int grantDateYear;

	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
    	@JoinColumn(name="SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false),
    	@JoinColumn(name="PERSONAL_GRANT_DATE_CD", referencedColumnName="PERSONAL_GRANT_DATE_CD", insertable = false, updatable = false)
    })
	public KshstGrantDatePer grantDatePer;
	
	public KshstGrantDatePerSet(KshstGrantDatePerSetPK kshstGrantDatePerSetPK, int grantDateMonth, int grantDateYear) {
		
		this.kshstGrantDatePerSetPK = kshstGrantDatePerSetPK;
		this.grantDateMonth = grantDateMonth;
		this.grantDateYear = grantDateYear;
	}
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kshstGrantDatePerSetPK;
	}
}
