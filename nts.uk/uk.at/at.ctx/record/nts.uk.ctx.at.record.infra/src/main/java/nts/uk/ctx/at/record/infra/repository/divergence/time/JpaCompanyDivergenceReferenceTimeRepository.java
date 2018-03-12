package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType;

/**
 * The Class JpaCompanyDivergenceReferenceTimeRepository.
 */
@Stateless
public class JpaCompanyDivergenceReferenceTimeRepository extends JpaRepository
		implements CompanyDivergenceReferenceTimeRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#findByKey(java.lang.String,
	 * nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType)
	 */
	@Override
	public CompanyDivergenceReferenceTime findByKey(String histId, DivergenceType divergenceTimeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#findAll(java.lang.String)
	 */
	@Override
	public List<CompanyDivergenceReferenceTime> findAll(String histId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#add(nts.uk.ctx.at.record.dom.
	 * divergence.time.history.CompanyDivergenceReferenceTime)
	 */
	@Override
	public void add(CompanyDivergenceReferenceTime domain) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#update(nts.uk.ctx.at.record.dom.
	 * divergence.time.history.CompanyDivergenceReferenceTime)
	 */
	@Override
	public void update(CompanyDivergenceReferenceTime domain) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#delete(nts.uk.ctx.at.record.dom.
	 * divergence.time.history.CompanyDivergenceReferenceTime)
	 */
	@Override
	public void delete(CompanyDivergenceReferenceTime domain) {
		// TODO Auto-generated method stub

	}

}
