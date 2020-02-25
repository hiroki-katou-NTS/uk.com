package nts.uk.ctx.hr.notice.infra.repository.report.registration.person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalActivity;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalStatus;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportApproval;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportApprovalPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaApprovalPersonReportRepository extends JpaRepository implements ApprovalPersonReportRepository{

	private static final String getListApproval = "select c FROM  JhndtReportApproval c Where c.pk.cid = :cid and c.pk.reportID = :reportId";
	private static final String deleteListApprovalByReportId = "delete FROM JhndtReportApproval c Where c.pk.cid = :cid and c.pk.reportID = :reportId";
	private static final String SEL_BY_REPORT_ID = "SELECT c FROM  JhndtReportApproval c WHERE c.pk.reportID = :reportId";
	private static final String SEL_BY_REPORT_ID_AND_APPROVER_ID = "SELECT c FROM  JhndtReportApproval c WHERE c.pk.cid = :cid AND c.pk.reportID = :reportId AND c.pk.aprSid = :sid";
	private static final String SEL = "select r FROM  JhndtReportApproval r";

	private ApprovalPersonReport toDomain(JhndtReportApproval entity) {
		return entity.toDomain();
	}
	
	private JhndtReportApproval toEntity(ApprovalPersonReport  domain) {
		JhndtReportApproval entity = new JhndtReportApproval();
		JhndtReportApprovalPK pk   = new JhndtReportApprovalPK(domain.getReportID(), domain.getPhaseNum(), domain.getAprNum(), domain.getCid(), domain.getAprSid());
		entity.pk  = pk;
		entity.rootSatteId = domain.getRootSatteId();
		entity.reportName  = domain.getReportName();
		entity.refDate  = domain.getRefDate();
		entity.inputDate  = domain.getInputDate();
		entity.appDate  = domain.getAppDate();
		entity.aprDate  = domain.getAprDate();
		entity.aprBussinessName  = domain.getAprBussinessName();
		entity.emailAddress  = domain.getEmailAddress();
		entity.aprStatus  = domain.getAprStatus() == null ? 1 : domain.getAprStatus().value;
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
	public List<ApprovalPersonReport> getListDomainByReportId(String cid, int reprtId) {
		return this.queryProxy().query(getListApproval, JhndtReportApproval.class)
				.setParameter("cid", cid)
				.setParameter("reportId", reprtId)
				.getList(c -> toDomain(c));
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
	public void delete(int reportID, int phaseNum, int aprNum, String cid, String aprSid) {
		if (checkExit(reportID, phaseNum, aprNum, cid, aprSid)) {
			this.commandProxy().remove(JhndtReportApproval.class, new JhndtReportApprovalPK(reportID, phaseNum, aprNum, cid, aprSid));
		}
	}

	@Override
	public boolean checkExit(int reportID, int phaseNum, int aprNum, String cid, String aprSid) {
		Optional<JhndtReportApproval> entityOpt = this.queryProxy().find(new JhndtReportApprovalPK(reportID, phaseNum, aprNum, cid, aprSid), JhndtReportApproval.class);
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

	@Override
		public List<ApprovalPersonReport> getByJHN003(String cId, String sId, GeneralDateTime startDate,
			GeneralDateTime endDate, Integer reportId, Integer approvalStatus, String inputName) {
		String query = SEL;

		query += " WHERE r.pk.cid = :cId AND r.appDate BETWEEN :startDate AND :endDate";

		if (reportId != null) {
			query += " AND r.reportLayoutID = %s";
			query = String.format(query, reportId);
		}

		if (approvalStatus != null) {
			query += " AND r.aprStatus = %s";
			query = String.format(query, approvalStatus);
		}

		query += " AND r.pk.aprSid = '%s'";
		query = String.format(query, sId);

		return this.queryProxy().query(query, JhndtReportApproval.class).setParameter("cId", cId)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getList(c -> toDomain(c));
	}

	public List<ApprovalPersonReport> getListDomainByReportId(int reprtId) {
		return this.queryProxy().query(SEL_BY_REPORT_ID, JhndtReportApproval.class)
				.setParameter("reportId", reprtId)
				.getList(c -> toDomain(c));
	}

	@Override
	public List<ApprovalPersonReport> getListDomainByReportIdAndSid(String cid, int reprtId, String approverId) {
		return this.queryProxy().query(SEL_BY_REPORT_ID_AND_APPROVER_ID, JhndtReportApproval.class)
				.setParameter("reportId", reprtId)
				.setParameter("sid", approverId)
				.setParameter("cid", cid)
				.getList(c -> toDomain(c));
	}

	@Override
	public void updateAll(List<ApprovalPersonReport> domains) {
		
		List<JhndtReportApproval> entities = domains.stream().map(dm -> toEntity(dm)).collect(Collectors.toList());
		
		this.commandProxy().updateAll(entities);
		
	}

	@Override
	public void updateSendBack(List<ApprovalPersonReport> domains, int reprtId, String sid) {
		String cid = AppContexts.user().companyId();
		List<JhndtReportApproval> entities = this.queryProxy()
				.query(SEL_BY_REPORT_ID_AND_APPROVER_ID, JhndtReportApproval.class)
				.setParameter("reportId", reprtId)
				.setParameter("sid", sid)
				.setParameter("cid", cid).getList();
		String comment = domains.get(0).getComment() == null ?  "" :  domains.get(0).getComment().toString() ;
		Integer sendBackClass = domains.get(0).getSendBackClass().isPresent() ? domains.get(0).getSendBackClass().get().value : null;
		String sendBackSID    = domains.get(0).getSendBackSID().isPresent()  ? domains.get(0).getSendBackSID().get() : null;
		entities.forEach(e -> {
			e.aprDate = GeneralDateTime.now();
			e.aprStatus = ApprovalStatus.Send_Back.value;
			e.aprActivity = ApprovalActivity.Activity.value;
			e.comment = comment;
			e.sendBackClass = sendBackClass;
			e.sendBackSID = sendBackSID;
		});
		this.commandProxy().updateAll(entities);

	}
}
