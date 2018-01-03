package nts.uk.ctx.at.record.infra.repository.calculationattribute;


import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.repo.CalAttrOfDailyPerformanceRepository;

@Stateless
public class JpaCalAttrOfDailyPerformanceRepoImpl extends JpaRepository implements CalAttrOfDailyPerformanceRepository{

	@Override
	public CalAttrOfDailyPerformance find(String employeeId, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(CalAttrOfDailyPerformance domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(CalAttrOfDailyPerformance domain) {
		// TODO Auto-generated method stub
		
	}

}
