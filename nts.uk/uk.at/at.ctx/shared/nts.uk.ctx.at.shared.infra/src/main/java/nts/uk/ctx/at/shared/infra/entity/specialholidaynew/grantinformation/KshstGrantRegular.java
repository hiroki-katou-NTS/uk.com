package nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantinformation;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.FixGrantDate;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.GrantRegular;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSpecialHoliday;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANT_REGULAR")
public class KshstGrantRegular extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstGrantRegularPK kshstGrantRegularPK;

	/* 付与するタイミングの種類 */
	@Column(name = "TYPE_TIME")
	public int typeTime;
	
	/* 付与基準日 */
	@Column(name = "GRANT_DATE")
	public int grantDate;
	
	/* 取得できなかった端数は消滅する */
	@Column(name = "ALLOW_DISAPPEAR")
	public int allowDisappear;
	
	/* 周期 */
	@Column(name = "INTERVAL")
	public int interval;
	
	/* 固定付与日数 */
	@Column(name = "GRANTED_DAYS")
	public int grantedDays;
	
	@OneToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name = "SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
	})
	public KshstSpecialHoliday specialHoliday;
	
	@OneToOne(cascade = CascadeType.REMOVE, mappedBy="grantRegular", orphanRemoval = false)
	public KshstGrantDateTbl grantDateTbl;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kshstGrantRegularPK;
	}

	public KshstGrantRegular(KshstGrantRegularPK kshstGrantRegularPK, int typeTime, int grantDate, int allowDisappear, int interval, int grantedDays) {
		this.kshstGrantRegularPK = kshstGrantRegularPK;
		this.typeTime = typeTime;
		this.grantDate = grantDate;
		this.allowDisappear = allowDisappear;
		this.interval = interval;
		this.grantedDays = grantedDays;
	}
	
	/**
	 * To Entity
	 * 
	 * @param domain
	 * @return
	 */
	public static KshstGrantRegular toEntity(GrantRegular domain){
		FixGrantDate fixGrantDate = domain.getGrantTime().isPresent() ? domain.getGrantTime().get().getFixGrantDate() : null;
		
		return new KshstGrantRegular(new KshstGrantRegularPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v()), domain.getTypeTime().value, 
				domain.getGrantDate().value, domain.isAllowDisappear() ? 1 : 0, fixGrantDate != null ? fixGrantDate.getInterval() : 0, 
						fixGrantDate != null ? fixGrantDate.getGrantDays().v() : 0);
	}
}
