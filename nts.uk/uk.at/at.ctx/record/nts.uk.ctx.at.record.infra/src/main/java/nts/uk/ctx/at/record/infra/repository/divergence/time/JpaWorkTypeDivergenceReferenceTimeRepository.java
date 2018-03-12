package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * The Class JpaWorkTypeDivergenceReferenceTimeRepository.
 */
@Stateless
public class JpaWorkTypeDivergenceReferenceTimeRepository extends JpaRepository
		implements WorkTypeDivergenceReferenceTimeRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeRepository#findByKey(java.lang.String,
	 * nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode,
	 * nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType)
	 */
	@Override
	public WorkTypeDivergenceReferenceTime findByKey(String histId, WorkTypeCode workTypeCode,
			DivergenceType divergenceTimeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeRepository#findAll(java.lang.String)
	 */
	@Override
	public List<WorkTypeDivergenceReferenceTime> findAll(String histId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeRepository#add(nts.uk.ctx.at.record.dom.
	 * divergence.time.history.WorkTypeDivergenceReferenceTime)
	 */
	@Override
	public void add(WorkTypeDivergenceReferenceTime domain) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeRepository#update(nts.uk.ctx.at.record.dom.
	 * divergence.time.history.WorkTypeDivergenceReferenceTime)
	 */
	@Override
	public void update(WorkTypeDivergenceReferenceTime domain) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeRepository#delete(nts.uk.ctx.at.record.dom.
	 * divergence.time.history.WorkTypeDivergenceReferenceTime)
	 */
	@Override
	public void delete(WorkTypeDivergenceReferenceTime domain) {
		// TODO Auto-generated method stub

	}

}
