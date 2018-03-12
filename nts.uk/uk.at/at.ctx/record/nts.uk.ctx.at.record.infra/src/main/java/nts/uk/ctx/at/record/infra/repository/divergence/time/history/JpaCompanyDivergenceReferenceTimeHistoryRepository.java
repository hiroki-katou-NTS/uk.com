package nts.uk.ctx.at.record.infra.repository.divergence.time.history;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistory;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistoryRepository;

/**
 * The Class JpaCompanyDivergenceReferenceTimeHistoryRepository.
 */
@Stateless
public class JpaCompanyDivergenceReferenceTimeHistoryRepository extends JpaRepository
		implements CompanyDivergenceReferenceTimeHistoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeHistoryRepository#findByHistId(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public CompanyDivergenceReferenceTimeHistory findByHistId(String companyId, String histId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeHistoryRepository#findAll(java.lang.String)
	 */
	@Override
	public CompanyDivergenceReferenceTimeHistory findAll(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeHistoryRepository#add(nts.uk.ctx.at.record.dom.
	 * divergence.time.history.CompanyDivergenceReferenceTimeHistory)
	 */
	@Override
	public void add(CompanyDivergenceReferenceTimeHistory domain) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeHistoryRepository#update(nts.uk.ctx.at.record.
	 * dom.divergence.time.history.CompanyDivergenceReferenceTimeHistory)
	 */
	@Override
	public void update(CompanyDivergenceReferenceTimeHistory domain) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeHistoryRepository#delete(nts.uk.ctx.at.record.
	 * dom.divergence.time.history.CompanyDivergenceReferenceTimeHistory)
	 */
	@Override
	public void delete(CompanyDivergenceReferenceTimeHistory domain) {
		// TODO Auto-generated method stub

	}

}
