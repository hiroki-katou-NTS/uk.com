package nts.uk.ctx.at.shared.infra.entity.specialholiday.yearserviceper;

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
@Table(name = "KSHST_YEARSERVICE_PER_SET")
public class KshstYearServicePerSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KshstYearServicePerSetPK kshstYearServicePerSetPK;
	/** 月数 **/
	@Column(name = "YEAR_SERVICE_YEARS")
	public int year;
	/** 年数 **/
	@Column(name = "YEAR_SERVICE_MONTHS")
	public int month;
	/** 特別休暇付与日数 **/
	@Column(name = "GRANT_DATES")
	public int date;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "KSHST_YEAR_SERVICE_PER.CID", insertable = false, updatable = false),
			@JoinColumn(name = "SPHD_CD", referencedColumnName = "KSHST_YEAR_SERVICE_PER.SPHD_CD", insertable = false, updatable = false),
			@JoinColumn(name = "YEAR_SERVICE_CD", referencedColumnName = "KSHST_YEAR_SERVICE_PER.YEAR_SERVICE_CD", insertable = false, updatable = false)})
	public KshstYearServicePer kshstYearServicePer;
	
	@Override
	protected Object getKey() {
		return kshstYearServicePerSetPK;
	}

	public KshstYearServicePerSet(KshstYearServicePerSetPK kshstYearServiceSetPK) {
		super();
		this.kshstYearServicePerSetPK = kshstYearServiceSetPK;
	}
}
