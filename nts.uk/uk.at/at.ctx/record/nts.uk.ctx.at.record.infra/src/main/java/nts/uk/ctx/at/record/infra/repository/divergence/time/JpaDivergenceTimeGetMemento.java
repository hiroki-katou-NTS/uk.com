package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeErrorCancelMethod;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeName;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeUseSet;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceType;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcAttendance;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcAttendancePK;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;

/**
 * The Class JpaDivergenceTimeRepositoryGetMemento.
 */
public class JpaDivergenceTimeGetMemento implements DivergenceTimeGetMemento {

	/** The entityDvgcTime. */
	private KrcstDvgcTime entityDvgcTime;

	/** The entity dvgc attendance. */
	private List<KrcstDvgcAttendance> entityDvgcAttendance;

	/**
	 * Instantiates a new jpa divergence time repository get memento.
	 *
	 * @param entityDvgcTime            the entityDvgcTime
	 * @param entityDvgcAttendance the entity dvgc attendance
	 */
	public JpaDivergenceTimeGetMemento(KrcstDvgcTime entityDvgcTime, List<KrcstDvgcAttendance> entityDvgcAttendance) {

		this.entityDvgcTime = entityDvgcTime;
		this.entityDvgcAttendance = entityDvgcAttendance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#
	 * getDivergenceTimeNo()
	 */
	@Override
	public Integer getDivergenceTimeNo() {
		return entityDvgcTime.getId().getNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#
	 * getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return entityDvgcTime.getId().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#
	 * getDivTimeUseSet()
	 */
	@Override
	public DivergenceTimeUseSet getDivTimeUseSet() {
		return DivergenceTimeUseSet.valueOf(entityDvgcTime.getDvgcTimeUseSet().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#
	 * getDivTimeName()
	 */
	@Override
	public DivergenceTimeName getDivTimeName() {
		return new DivergenceTimeName(entityDvgcTime.getDvgcTimeName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#
	 * getDivType()
	 */
	@Override
	public DivergenceType getDivType() {
		return DivergenceType.valueOf(entityDvgcTime.getDvgcType().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#
	 * getErrorCancelMedthod()
	 */
	@Override
	public DivergenceTimeErrorCancelMethod getErrorCancelMedthod() {
		return new DivergenceTimeErrorCancelMethod(entityDvgcTime.getReasonInputCanceled().intValue(),
				entityDvgcTime.getReasonSelectCanceled().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#
	 * getTargetItems()
	 */
	@Override
	public List<Integer> getTargetItems() {
		if(entityDvgcAttendance == null){
			return new ArrayList<Integer>() ;
		}
		List<Integer> temp =  entityDvgcAttendance.stream().map(item -> {			
			KrcstDvgcAttendancePK pk = item.getId();
			return pk.getAttendanceId();
			}).collect(Collectors.toList());
		return temp;
	}

}
