package nts.uk.ctx.at.function.infra.repository.monthlyworkschedule;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkSchedule;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleRepository;

@Stateless
public class JpaOutputItemMonthlyWorkScheduleRepository implements OutputItemMonthlyWorkScheduleRepository{

	@Override
	public Optional<OutputItemMonthlyWorkSchedule> findByCidAndCode(String companyId, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OutputItemMonthlyWorkSchedule> findByCid(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(OutputItemMonthlyWorkSchedule domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(OutputItemMonthlyWorkSchedule domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(OutputItemMonthlyWorkSchedule domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByCidAndCode(String companyId, String code) {
		// TODO Auto-generated method stub
		
	}

}
