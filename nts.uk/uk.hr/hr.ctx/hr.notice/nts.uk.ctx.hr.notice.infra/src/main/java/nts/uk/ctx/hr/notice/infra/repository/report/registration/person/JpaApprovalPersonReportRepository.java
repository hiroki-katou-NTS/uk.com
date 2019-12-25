package nts.uk.ctx.hr.notice.infra.repository.report.registration.person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReportRepository;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportApproval;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportApprovalPK;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaApprovalPersonReportRepository extends JpaRepository implements ApprovalPersonReportRepository{

	private static final String getListApproval = "select c FROM  JhndtReportApproval c Where c.pk.cid = :cid and c.pk.reportID = :reportId";
	private static final String deleteListApprovalByReportId = "delete FROM JhndtReportApproval c Where c.pk.cid = :cid and c.pk.reportID = :reportId";

	private ApprovalPersonReport toDomain(JhndtReportApproval entity) {
		return entity.toDomain();
	}
	
	private JhndtReportApproval toEntity(ApprovalPersonReport  domain) {
		JhndtReportApproval entity = new JhndtReportApproval();
		JhndtReportApprovalPK pk   = new JhndtReportApprovalPK(domain.getReportID(), domain.getPhaseNum(), domain.getAprNum(), domain.getCid());
		entity.pk  = pk;
		entity.rootSatteId = domain.getRootSatteId();
		entity.reportName  = domain.getReportName();
		entity.refDate  = domain.getRefDate();
		entity.inputDate  = domain.getInputDate();
		entity.appDate  = domain.getAppDate();
		entity.aprDate  = domain.getAprDate();
		entity.aprSid  = domain.getAprSid();
		entity.aprBussinessName  = domain.getAprBussinessName();
		entity.emailAddress  = domain.getEmailAddress();
		entity.aprStatusName  = domain.getAprStatusName() == null ? null : domain.getAprStatusName().value;
		entity.arpAgency  = domain.isArpAgency();
		entity.comment  = domain.getComment() == null ? null : domain.getComment().toString();
		entity.aprActivity  = domain.getAprActivity() ==  null ? null : domain.getAprActivity().value;
		entity.emailTransmissionClass = domain.getEmailTransmissionClass() == null ? null : domain.getEmailTransmissionClass().value;
		entity.appSid  = domain.getAppSid();
		entity.inputSid  = domain.getInputSid();
		entity.reportLayoutID = domain.getReportLayoutID();
		entity.sendBackSID  = domain.getSendBackSID().isPresent() ? domain.getSendBackSID().get() : null;
		entity.sendBackClass = domain.getSendBackClass().isPresent() ? domain.getSendBackClass().get().value : null ;
		
		return entity;
	}
	
	@Override
	public List<ApprovalPersonReport> getListDomainByReportId(String cid, String reprtId) {
		return this.queryProxy().query(getListApproval, JhndtReportApproval.class)
				.setParameter("cid", cid)
				.setParameter("reportId", reprtId)
				.getList(c -> toDomain(c));
	}

	@Override
	public void registerApprovalData(ApprovalPersonReport domain) {
		
	}

	@Override
	public void add(ApprovalPersonReport domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(ApprovalPersonReport domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void delete(int reportID, int phaseNum, int aprNum, String cid) {
		if (checkExit(reportID, phaseNum, aprNum, cid)) {
			this.commandProxy().remove(JhndtReportApproval.class, new JhndtReportApprovalPK(reportID, phaseNum, aprNum, cid));
		}
	}

	@Override
	public boolean checkExit(int reportID, int phaseNum, int aprNum, String cid) {
		Optional<JhndtReportApproval> entityOpt = this.queryProxy().find(new JhndtReportApprovalPK(reportID, phaseNum, aprNum, cid), JhndtReportApproval.class);
		if (entityOpt.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void deleteByReportId(int reportId, String cid) {
		this.getEntityManager().createQuery(deleteListApprovalByReportId)
		.setParameter("cid", cid)
		.setParameter("reportId", reportId).executeUpdate();
	}

	@Override
	public void addAll(List<ApprovalPersonReport> domains) {
		List<JhndtReportApproval> entities = domains.stream().map(dm -> toEntity(dm)).collect(Collectors.toList());
		this.commandProxy().insertAll(entities);
	}
}
