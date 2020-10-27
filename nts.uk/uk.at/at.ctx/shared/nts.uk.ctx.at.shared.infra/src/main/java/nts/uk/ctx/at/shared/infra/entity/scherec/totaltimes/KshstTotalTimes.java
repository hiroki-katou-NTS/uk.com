/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshstTotalTimes.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHST_TOTAL_TIMES")
public class KshstTotalTimes extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshst total times PK. */
	@EmbeddedId
	protected KshstTotalTimesPK kshstTotalTimesPK;

	/** The use atr. */
	@Column(name = "USE_ATR")
	private int useAtr;

	/** The count atr. */
	@Column(name = "COUNT_ATR")
	private int countAtr;

	/** The total times name. */
	@Column(name = "TOTAL_TIMES_NAME")
	private String totalTimesName;

	/** The total times abname. */
	@Column(name = "TOTAL_TIMES_ABNAME")
	private String totalTimesAbname;

	/** The summary atr. */
	@Column(name = "SUMMARY_ATR")
	private int summaryAtr;

	/** The list total subjects. */
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "TOTAL_TIMES_NO", referencedColumnName = "TOTAL_TIMES_NO", insertable = false, updatable = false) })
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	public List<KshstTotalSubjects> listTotalSubjects;

	/** The total condition. */
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "TOTAL_TIMES_NO", referencedColumnName = "TOTAL_TIMES_NO", insertable = false, updatable = false) })
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	public KshstTotalCondition totalCondition;

	/**
	 * Instantiates a new kshst total times.
	 */
	public KshstTotalTimes() {
		super();
	}

	/**
	 * Instantiates a new kshst total times.
	 *
	 * @param kshstTotalTimesPK
	 *            the kshst total times PK
	 */
	public KshstTotalTimes(KshstTotalTimesPK kshstTotalTimesPK) {
		this.kshstTotalTimesPK = kshstTotalTimesPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstTotalTimesPK != null ? kshstTotalTimesPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstTotalTimes)) {
			return false;
		}
		KshstTotalTimes other = (KshstTotalTimes) object;
		if ((this.kshstTotalTimesPK == null && other.kshstTotalTimesPK != null)
				|| (this.kshstTotalTimesPK != null
						&& !this.kshstTotalTimesPK.equals(other.kshstTotalTimesPK))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstTotalTimesPK;
	}

}
