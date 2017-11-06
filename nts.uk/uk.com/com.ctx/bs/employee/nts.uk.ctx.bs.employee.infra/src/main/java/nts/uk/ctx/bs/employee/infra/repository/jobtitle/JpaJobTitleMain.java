package nts.uk.ctx.bs.employee.infra.repository.jobtitle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMain;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMainRepository;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobTitleMain;

@Stateless
public class JpaJobTitleMain extends JpaRepository implements JobTitleMainRepository {

	private static final String SELECT_JOB_TITLE_MAIN_BY_ID = "SELECT j FROM BsymtJobTitleMain j "
			+ " WHERE j.bsymtJobTitleMainPK.jobTitleId = :jobTitleId";

	private static final String SELECT_BY_EID_STDD = "SELECT j FROM BsymtJobTitleMain j"
			+ " WHERE j.sId = :employeeId And j.bsymtJobPosMainHist.startDate <= :std And j.bsymtJobPosMainHist.endDate >= :std";
	
	private static final String GET_ALL_BY_SID = "SELECT c FROM BsymtJobTitleMain c "
			+ " WHERE c.sId = :sid";

	private JobTitleMain toDomain(BsymtJobTitleMain entity) {
		return JobTitleMain.creatFromJavaType(entity.jobTitleId, entity.sId, entity.histId,
				entity.bsymtJobPosMainHist.startDate, entity.bsymtJobPosMainHist.endDate);
	}
	
	private List<JobTitleMain> toListJobTitleMain(List<BsymtJobTitleMain> listEntity) {
		List<JobTitleMain> lstJobTitleMain = new ArrayList<>();
		if (!listEntity.isEmpty()) {
			listEntity.stream().forEach(c -> {
				JobTitleMain jobTitleMain = toDomain(c);
				
				lstJobTitleMain.add(jobTitleMain);
			});
		}
		return lstJobTitleMain;
	}
	
	

	@Override
	public Optional<JobTitleMain> getJobTitleMainById(String jobTitleMainId) {
		return this.queryProxy().query(SELECT_JOB_TITLE_MAIN_BY_ID, BsymtJobTitleMain.class)
				.setParameter("jobTitleId", jobTitleMainId).getSingle(x -> toDomain(x));
	}

	@Override
	public Optional<JobTitleMain> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate) {
		Optional<BsymtJobTitleMain> optData = this.queryProxy().query(SELECT_BY_EID_STDD, BsymtJobTitleMain.class)
				.setParameter("employeeId", employeeId).setParameter("std", standandDate).getSingle();
		if (optData.isPresent()) {
			BsymtJobTitleMain jtm = optData.get();
			return Optional.of(JobTitleMain.creatFromJavaType(jtm.jobTitleId, jtm.sId, jtm.histId,
					jtm.bsymtJobPosMainHist.startDate, jtm.bsymtJobPosMainHist.endDate));
		}
		return Optional.empty();
	}
	
	

	@Override
	public List<JobTitleMain> getListBiSid(String sid) {
		
		List<BsymtJobTitleMain> listEntity = this.queryProxy().query(GET_ALL_BY_SID, BsymtJobTitleMain.class)
				.setParameter("sid", sid)
				.getList();

		return toListJobTitleMain(listEntity);
	}

}
