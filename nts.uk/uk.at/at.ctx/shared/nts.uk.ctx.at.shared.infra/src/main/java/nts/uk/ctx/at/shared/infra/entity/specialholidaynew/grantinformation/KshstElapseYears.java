package nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 経過年数に対する付与日数
 * 
 * @author tanlv
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHST_ELAPSE_YEARS")
public class KshstElapseYears extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstElapseYearsPK pk;
	
	/* 特別休暇付与日数 */
	@Column(name = "GRANTED_DAYS")
	public double grantedDays;
	
	/* 経過年数.月数 */
	@Column(name = "MONTHS")
	public int months;
	
	/* 経過年数.年数 */
	@Column(name = "YEARS")
	public int years;
	
	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
    	@JoinColumn(name="SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false),
    	@JoinColumn(name="GD_TBL_CD", referencedColumnName="GD_TBL_CD", insertable = false, updatable = false)
    })
	public KshstGrantDateTbl grantDateTbl;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return pk;
	}
}
