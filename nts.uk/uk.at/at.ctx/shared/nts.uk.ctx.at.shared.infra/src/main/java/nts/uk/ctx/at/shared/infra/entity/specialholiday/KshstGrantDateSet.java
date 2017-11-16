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
@Table(name = "KSHST_GRANTDATE_COM_SET")
public class KshstGrantDateSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstGrantDateSetPK kshstGrantDateSetPK;

	/* 月数 */
	@Column(name = "GRANT_DATE_M")
	public int grantDateM;

	/* 年数 */
	@Column(name = "GRANT_DATE_Y")
	public int grantDateY;

	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
    	@JoinColumn(name="SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
    })
	public KshstGrantDateCom grantDateCom;
	
	public KshstGrantDateSet(KshstGrantDateSetPK kshstGrantDateSetPK, int grantDateM, int grantDateY) {
		super();
		this.kshstGrantDateSetPK = kshstGrantDateSetPK;
		this.grantDateM = grantDateM;
		this.grantDateY = grantDateY;
	}
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}