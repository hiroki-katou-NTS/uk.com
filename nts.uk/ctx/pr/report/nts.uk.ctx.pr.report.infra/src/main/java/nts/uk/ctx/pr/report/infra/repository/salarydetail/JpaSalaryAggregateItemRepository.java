package nts.uk.ctx.pr.report.infra.repository.salarydetail;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItem;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemRepository;

@Stateless
public class JpaSalaryAggregateItemRepository implements SalaryAggregateItemRepository {

	@Override
	public void save(SalaryAggregateItem aggregateItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(SalaryAggregateItem aggregateItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SalaryAggregateItem findByCode(String code, CompanyCode companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
