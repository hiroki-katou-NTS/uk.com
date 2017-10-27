package nts.uk.ctx.at.shared.infra.entity.specialholiday.yearserviceset;

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
import nts.uk.ctx.at.shared.infra.entity.specialholiday.yearservicecom.KshstYearServiceCom;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_YEARSERVICE_COM_SET")
public class KshstYearServiceSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KshstYearServiceSetPK kshstYearServiceSetPK;
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
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "KSHST_YEAR_SERVICE_COM.CID", insertable = false, updatable = false),
			@JoinColumn(name = "SPHD_CD", referencedColumnName = "KSHST_YEAR_SERVICE_COM.SPHD_CD", insertable = false, updatable = false) })
	public KshstYearServiceCom kshstYearServiceCom;
	
	
	@Override
	protected Object getKey() {
		return kshstYearServiceSetPK;
	}

	public KshstYearServiceSet(KshstYearServiceSetPK kshstYearServiceSetPK) {
		super();
		this.kshstYearServiceSetPK = kshstYearServiceSetPK;
	}
}
