package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeErrorCancelMethod;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeName;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeSetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeUseSet;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceType;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcAttendance;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcAttendancePK;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;

/**
 * The Class JpaDivergenceTimeRepositorySetMemento.
 */
public class JpaDivergenceTimeSetMemento implements DivergenceTimeSetMemento {

	/** The entity. */
	private KrcstDvgcTime entityDvgcTime;

	/** The entity dvgc attendance. */
	private List<KrcstDvgcAttendance> entityDvgcAttendanceList;

	/**
	 * Instantiates a new jpa divergence time repository set memento.
	 */
	public JpaDivergenceTimeSetMemento() {

	}

	/**
	 * Instantiates a new jpa divergence time repository set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaDivergenceTimeSetMemento(KrcstDvgcTime entityDvgcTime, List<KrcstDvgcAttendance> entityDvgcAttendanceList) {
		this.entityDvgcTime = entityDvgcTime;
		this.entityDvgcAttendanceList = entityDvgcAttendanceList;
	}

	/**
	 * Sets the divergence time no.
	 *
	 * @param DivergenceTimeNo
	 *            the new divergence time no
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeSetMemento#
	 * setDivergenceTimeNo(int)
	 */
	@Override
	public void setDivergenceTimeNo(int DivergenceTimeNo) {
		this.entityDvgcTime.getId().setNo(DivergenceTimeNo);
		;

	}

	/**
	 * Sets the company id.
	 *
	 * @param CompanyId
	 *            the new company id
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String CompanyId) {
		// No coding

	}

	/**
	 * Sets the div time use set.
	 *
	 * @param divTimeUset
	 *            the new div time use set
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeSetMemento#
	 * setDivTimeUseSet(nts.uk.ctx.at.record.dom.divergence.time.
	 * DivergenceTimeUseSet)
	 */
	@Override
	public void setDivTimeUseSet(DivergenceTimeUseSet divTimeUset) {
		this.entityDvgcTime.setDvgcTimeUseSet(new BigDecimal(divTimeUset.value));

	}

	/**
	 * Sets the div time name.
	 *
	 * @param divTimeName
	 *            the new div time name
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeSetMemento#
	 * setDivTimeName(nts.uk.ctx.at.record.dom.divergence.time.
	 * DivergenceTimeName)
	 */
	@Override
	public void setDivTimeName(DivergenceTimeName divTimeName) {
		this.entityDvgcTime.setDvgcTimeName(divTimeName.toString());

	}

	/**
	 * Sets the div type.
	 *
	 * @param divType
	 *            the new div type
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeSetMemento#
	 * setDivType(nts.uk.ctx.at.record.dom.divergence.time.DivergenceType)
	 */
	@Override
	public void setDivType(DivergenceType divType) {
		this.entityDvgcTime.setDvgcType(new BigDecimal(divType.value));

	}

	/**
	 * Sets the error cancel medthod.
	 *
	 * @param errorCancelMedthod
	 *            the new error cancel medthod
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeSetMemento#
	 * setErrorCancelMedthod(nts.uk.ctx.at.record.dom.divergence.time.
	 * DivergenceTimeErrorCancelMethod)
	 */
	@Override
	public void setErrorCancelMedthod(DivergenceTimeErrorCancelMethod errorCancelMedthod) {

		this.entityDvgcTime
				.setReasonInputCanceled(errorCancelMedthod.isReasonInputed() ? BigDecimal.ONE : BigDecimal.ZERO);

		this.entityDvgcTime
				.setReasonSelectCanceled(errorCancelMedthod.isReasonSelected() ? BigDecimal.ONE : BigDecimal.ZERO);

	}

	/**
	 * Sets the tarset items.
	 *
	 * @param targetItems
	 *            the new tarset items
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeSetMemento#
	 * setTarsetItems(java.util.List)
	 */
	@Override
	public void setTarsetItems(List<Integer> targetItems,int divNo, String companyId) {
		targetItems.forEach(item ->{
			KrcstDvgcAttendance entityDvgcAttendance = new KrcstDvgcAttendance();
			entityDvgcAttendance.setId(new KrcstDvgcAttendancePK(divNo, companyId, item));
			entityDvgcAttendanceList.add(entityDvgcAttendance);
		});

	}

}
