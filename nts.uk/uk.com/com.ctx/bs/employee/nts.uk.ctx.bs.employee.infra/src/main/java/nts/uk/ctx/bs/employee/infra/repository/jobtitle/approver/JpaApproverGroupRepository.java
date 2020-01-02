package nts.uk.ctx.bs.employee.infra.repository.jobtitle.approver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.bs.employee.dom.jobtitle.approver.ApproverGroup;
import nts.uk.ctx.bs.employee.dom.jobtitle.approver.ApproverGroupRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.approver.ApproverJob;
import nts.uk.ctx.bs.employee.dom.jobtitle.approver.ApproverName;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleCode;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.approver.BsymtApproverGroup;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaApproverGroupRepository extends JpaRepository implements ApproverGroupRepository {
	
	private static final String FIND_ALL;
	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString = new StringBuilder();
		builderString.append("SELECT * FROM BSYMT_APPROVER_GROUP a JOIN BSYMT_APPROVER_G_LIST_JOB b ");
		builderString.append("on a.CID = b.CID and a.APPROVER_G_CD = b.APPROVER_G_CD ");
		builderString.append("where a.CID = 'companyID'");
		FIND_ALL = builderString.toString();
	}
	
	@AllArgsConstructor
	@Getter
	private class FullJoin {
	    private String companyID;
	    private String approverGroupCD;
	    private String approverGroupName;
	    private String jobID;
	    private int order;
	}
	
	private List<FullJoin> createFullJoin(ResultSet rs){
		return new NtsResultSet(rs)
				.getList(x -> {
					return new FullJoin(
							x.getString("CID"), 
							x.getString("APPROVER_G_CD"), 
							x.getString("APPROVER_G_NAME"), 
							x.getString("JOB_ID"),
							x.getInt("DISPLAY_ORDER"));
				});
	}
	
	private List<ApproverGroup> toDomain(List<FullJoin> listFullJoin) {
		return listFullJoin.stream().collect(Collectors.groupingBy(FullJoin::getApproverGroupCD))
			.entrySet().stream().map(x -> {
				List<ApproverJob> approverLst = x.getValue().stream().map(y -> new ApproverJob(y.getJobID(), y.getOrder())).collect(Collectors.toList());
				return new ApproverGroup(
						x.getValue().get(0).getCompanyID(), 
						new JobTitleCode(x.getValue().get(0).getApproverGroupCD()), 
						new ApproverName(x.getValue().get(0).getApproverGroupName()), 
						approverLst);
			}).collect(Collectors.toList());
	}

	@Override
	public List<ApproverGroup> findAll(String companyID) {
		String sql = FIND_ALL.replace("companyID", companyID);
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			return toDomain(createFullJoin(rs));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void insert(ApproverGroup approverGroup) {
		commandProxy().insert(BsymtApproverGroup.fromDomain(approverGroup));
	}

	@Override
	public void update(ApproverGroup approverGroup) {
		commandProxy().update(BsymtApproverGroup.fromDomain(approverGroup));
	}

	@Override
	public void delete(ApproverGroup approverGroup) {
		commandProxy().remove(BsymtApproverGroup.fromDomain(approverGroup));
	}

}
