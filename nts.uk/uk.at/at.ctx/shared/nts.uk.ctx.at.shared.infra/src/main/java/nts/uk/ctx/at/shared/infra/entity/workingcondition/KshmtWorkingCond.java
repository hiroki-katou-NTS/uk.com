/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtWorkingCond.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WORKING_COND")
public class KshmtWorkingCond extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt working cond PK. */
	@EmbeddedId
	protected KshmtWorkingCondPK kshmtWorkingCondPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The str D. */
	@Column(name = "START_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate strD;

	/** The end D. */
	@Column(name = "END_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate endD;

	/** The kshmt working cond items. */
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false) })
	private KshmtWorkingCondItem kshmtWorkingCondItem;

	/**
	 * Instantiates a new kshmt working cond.
	 */
	public KshmtWorkingCond() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWorkingCondPK != null ? kshmtWorkingCondPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorkingCond)) {
			return false;
		}
		KshmtWorkingCond other = (KshmtWorkingCond) object;
		if ((this.kshmtWorkingCondPK == null && other.kshmtWorkingCondPK != null)
				|| (this.kshmtWorkingCondPK != null
						&& !this.kshmtWorkingCondPK.equals(other.kshmtWorkingCondPK))) {
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
		return this.kshmtWorkingCondPK;
	}

	public KshmtWorkingCond(KshmtWorkingCondPK kshmtWorkingCondPK, String cid, GeneralDate strD, GeneralDate endD) {
		super();
		this.kshmtWorkingCondPK = kshmtWorkingCondPK;
		this.cid = cid;
		this.strD = strD;
		this.endD = endD;
	}
	
	public static List<KshmtWorkingCond> toEntity(WorkingCondition dom){
		List<KshmtWorkingCond> listEntity = new ArrayList<>();
		List<DateHistoryItem> listDate = dom.getDateHistoryItem();
		listDate.stream().forEach( x ->{
			KshmtWorkingCond data = new KshmtWorkingCond(
					new KshmtWorkingCondPK(dom.getEmployeeId(), x.identifier()),
					dom.getCompanyId(),
					x.span().start(),
					x.span().end());
			listEntity.add(data);
		});	
		return listEntity;
	}


	public static Optional<WorkingCondition> toDomainHis(List<KshmtWorkingCond> lstEntity){
		if(lstEntity.isEmpty()){
			return Optional.empty();
		}
		List<DateHistoryItem> lstDateHis = lstEntity.stream()
				.map(c -> new DateHistoryItem(c.getKshmtWorkingCondPK().getHistoryId(),
						new DatePeriod(c.getStrD(), c.getEndD())))
				.collect(Collectors.toList());
		WorkingCondition dom = new WorkingCondition(
				lstEntity.get(0).getCid(),
				lstEntity.get(0).getKshmtWorkingCondPK().getSid(),
				lstDateHis);
		return Optional.of(dom);
	}


}
