package nts.uk.ctx.bs.employee.infra.repository.jobtitle;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMain;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMainRepository;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobTitleMain;

@Stateless
public class JpaJobTitleMain extends JpaRepository implements JobTitleMainRepository{

	private static final String SELECT_JOB_TITLE_MAIN_BY_ID = "SELECT j FROM BsymtJobTitleMain j "
			+ " WHERE j.bsymtJobTitleMainPK.jobTitleId = :jobTitleId";
	
	private JobTitleMain toDomain(BsymtJobTitleMain entity){
		return JobTitleMain.creatFromJavaType(entity.getBsymtJobTitleMainPK().getJobTitleId(), 
				entity.getSId(), entity.getHistId(), entity.bsymtJobPosMainHist.getStartDate(), entity.bsymtJobPosMainHist.getEndDate());
	}
	
	@Override
	public Optional<JobTitleMain> getJobTitleMainById(String jobTitleMainId) {
		return this.queryProxy().query(SELECT_JOB_TITLE_MAIN_BY_ID, BsymtJobTitleMain.class)
				.setParameter("jobTitleId", jobTitleMainId)
				.getSingle(x -> toDomain(x));
	}

	@Override
	public Optional<JobTitleMain> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
