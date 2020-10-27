/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

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
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtFleBrFlWek.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLE_BR_FL_WEK")
public class KshmtWtFleBrFlWek extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex ha rt set PK. */
	@EmbeddedId
	protected KshmtWtFleBrFlWekPK kshmtWtFleBrFlWekPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The fix rest time. */
	@Column(name = "FIX_REST_TIME")
	private int fixRestTime;

	/** The use rest after set. */
	@Column(name = "USE_REST_AFTER_SET")
	private int useRestAfterSet;

	/** The after rest time. */
	@Column(name = "AFTER_REST_TIME")
	private int afterRestTime;

	/** The after passage time. */
	@Column(name = "AFTER_PASSAGE_TIME")
	private int afterPassageTime;

	/** The kshmt flex work time sets. */
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true),
			@JoinColumn(name = "AM_PM_ATR", referencedColumnName = "AM_PM_ATR", insertable = true, updatable = true) })
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<KshmtWtFleWorkTs> kshmtWtFleWorkTss;

	/** The kshmt flex ot time sets. */
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true),
			@JoinColumn(name = "AM_PM_ATR", referencedColumnName = "AM_PM_ATR", insertable = true, updatable = true) })
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<KshmtWtFleOverTs> kshmtWtFleOverTss;
	
	/** The kshmt flex ha fix rests. */
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
		@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true),
		@JoinColumn(name = "AM_PM_ATR", referencedColumnName = "AM_PM_ATR", insertable = true, updatable = true) })
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<KshmtWtFleBrFiWekTs> kshmtWtFleBrFiWekTss;
	
	/** The kshmt flex ha rest sets. */
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
		@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true),
		@JoinColumn(name = "AM_PM_ATR", referencedColumnName = "AM_PM_ATR", insertable = true, updatable = true) })
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<KshmtWtFleBrFlWekTs> kshmtWtFleBrFlWekTss;

	/**
	 * Instantiates a new kshmt flex ha rt set.
	 */
	public KshmtWtFleBrFlWek() {
		super();
	}

	/**
	 * Instantiates a new kshmt flex ha rt set.
	 *
	 * @param kshmtWtFleBrFlWekPK the kshmt flex ha rt set PK
	 */
	public KshmtWtFleBrFlWek(KshmtWtFleBrFlWekPK kshmtWtFleBrFlWekPK) {
		super();
		this.kshmtWtFleBrFlWekPK = kshmtWtFleBrFlWekPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtFleBrFlWekPK != null ? kshmtWtFleBrFlWekPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFleBrFlWek)) {
			return false;
		}
		KshmtWtFleBrFlWek other = (KshmtWtFleBrFlWek) object;
		if ((this.kshmtWtFleBrFlWekPK == null && other.kshmtWtFleBrFlWekPK != null)
				|| (this.kshmtWtFleBrFlWekPK != null
						&& !this.kshmtWtFleBrFlWekPK.equals(other.kshmtWtFleBrFlWekPK))) {
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
		return this.kshmtWtFleBrFlWekPK;
	}


}
