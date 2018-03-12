package nts.uk.ctx.at.record.infra.repository.divergence.time.history;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistory;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * The Class JpaWorkTypeDivergenceReferenceTimeHistoryRepository.
 */
@Stateless
public class JpaWorkTypeDivergenceReferenceTimeHistoryRepository extends JpaRepository
		implements WorkTypeDivergenceReferenceTimeHistoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#findByKey(java.lang.String,
	 * nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode, java.lang.String)
	 */
	@Override
	public WorkTypeDivergenceReferenceTimeHistory findByKey(String companyId, WorkTypeCode workTypeCode,
			String histId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#findAll(java.lang.String,
	 * nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode)
	 */
	@Override
	public List<WorkTypeDivergenceReferenceTimeHistory> findAll(String companyId, WorkTypeCode workTypeCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#add(nts.uk.ctx.at.record.dom
	 * .divergence.time.history.WorkTypeDivergenceReferenceTimeHistory)
	 */
	@Override
	public void add(WorkTypeDivergenceReferenceTimeHistory domain) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#update(nts.uk.ctx.at.record.
	 * dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistory)
	 */
	@Override
	public void update(WorkTypeDivergenceReferenceTimeHistory domain) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#delete(nts.uk.ctx.at.record.
	 * dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistory)
	 */
	@Override
	public void delete(WorkTypeDivergenceReferenceTimeHistory domain) {
		// TODO Auto-generated method stub

	}

}
