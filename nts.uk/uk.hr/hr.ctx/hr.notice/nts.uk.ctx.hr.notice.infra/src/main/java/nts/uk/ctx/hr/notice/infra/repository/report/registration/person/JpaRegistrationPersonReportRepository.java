/**
 * 
 */
package nts.uk.ctx.hr.notice.infra.repository.report.registration.person;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.StringUtil;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportRegis;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportRegisPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaRegistrationPersonReportRepository extends JpaRepository implements RegistrationPersonReportRepository {

	private static final String getListBySId = "select c FROM  JhndtReportRegis c Where c.pk.cid = :cid and c.inputSid = :sid  ORDER BY c.reportName ASC";
	private static final String GET_MAX_REPORT_ID = "SELECT MAX(a.pk.reportId) FROM JhndtReportRegis a WHERE c.pk.cid = :cid and c.inputSid = :sid";
	private static final String getListReportSaveDraft = "select c FROM  JhndtReportRegis c Where c.pk.cid = :cid "
			+ " and c.inputSid = :sid and c.appSid = :sid "
			+ " and c.regStatus = 1 and c.delFlg = 0 ORDER BY c.reportName ASC ";
	private static final String getDomainDetail = "select c FROM  JhndtReportRegis c Where c.pk.cid = :cid and c.reportLayoutID = :reportLayoutID ";
	
	private static final String getDomainByReportId = "select c FROM  JhndtReportRegis c Where c.pk.cid = :cid and c.pk.reportId = :reportId ";
	

	private static final String SEL = "select r FROM  JhndtReportRegis r";

	private RegistrationPersonReport toDomain(JhndtReportRegis entity) {
		return entity.toDomain();
	}

	private JhndtReportRegis toEntity(RegistrationPersonReport domain) {
		return JhndtReportRegis.toEntity(domain);
	}

	@Override
	public int getMaxReportId(String sid, String cid) {
		Object max = this.queryProxy().query(GET_MAX_REPORT_ID, Object.class).setParameter("cid", cid)
				.setParameter("sid", sid).getSingleOrNull();
		if (max.equals(null)) {
			return 0;
		} else {
			return (int) max;
		}
	}

	@Override
	public List<RegistrationPersonReport> getListBySIds(String sid) {
		String cid =  AppContexts.user().companyId();
		return this.queryProxy().query(getListBySId, JhndtReportRegis.class)
				.setParameter("cid", cid)
				.setParameter("sid", sid).getList(c -> toDomain(c));
	}

	@Override
	public List<RegistrationPersonReport> getListReportSaveDraft(String sid) {
		String cid =  AppContexts.user().companyId();
		return this.queryProxy().query(getListReportSaveDraft, JhndtReportRegis.class)
				.setParameter("cid", cid)
				.setParameter("sid", sid).getList(c -> toDomain(c));
	}

	@Override
	public Optional<RegistrationPersonReport> getDomain(String cid, Integer reportLayoutID) {
		if (reportLayoutID == null) {
			return Optional.empty();
		}
		
		Optional<JhndtReportRegis> entityOpt = this.queryProxy().query(getDomainDetail, JhndtReportRegis.class)
				.setParameter("cid", cid)
				.setParameter("reportLayoutID", reportLayoutID).getSingle();
		
		if (!entityOpt.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(toDomain(entityOpt.get()));
		}
	}
	
	@Override
	public Optional<RegistrationPersonReport> getDomainByReportId(String cid, Integer reportId) {
		if (reportId == null) {
			return Optional.empty();
		}
		
		Optional<JhndtReportRegis> entityOpt = this.queryProxy().query(getDomainByReportId, JhndtReportRegis.class)
				.setParameter("cid", cid)
				.setParameter("reportId", reportId).getSingle();
		
		if (!entityOpt.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(toDomain(entityOpt.get()));
		}
	}

	@Override
	public void add(RegistrationPersonReport domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(RegistrationPersonReport domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void remove(String cid, int reportId) {
		JhndtReportRegisPK pk = new JhndtReportRegisPK(cid, reportId);
		this.commandProxy().remove(JhndtReportRegis.class, pk);
	}

	@Override
	public List<RegistrationPersonReport> findByJHN003(String cId, String sId, GeneralDateTime startDate,
			GeneralDateTime endDate, Integer reportId, Integer approvalStatus, String inputName,
			boolean approvalReport) {

		String query = SEL;

		if (approvalReport) {
			query += " INNER JOIN JhndtReportApproval a" + " ON r.pk.reportId = a.pk.reportID";
		}

		query += " WHERE r.pk.cid = :cId AND r.appDate BETWEEN :startDate AND :endDate";

		if (reportId != null) {
			query += " AND r.reportLayoutID = %s";
			query = String.format(query, reportId);
		}

		if (approvalStatus != null) {
			if (approvalReport) {
				query += " AND a.aprStatusName = '%s'  AND r.aprStatus = %s";
				query = String.format(query, approvalStatus, approvalStatus);
			} else {
				query += " AND r.aprStatus = %s";
				query = String.format(query, approvalStatus);
			}
		}
		
		if (approvalReport) {
			query += " AND a.aprSid = '%s'";
			query = String.format(query, sId);
		} else {
			if (!StringUtil.isNullOrEmpty(inputName, false)) {
				query += " AND (r.inputBussinessName LIKE '%s' OR r.appBussinessName LIKE '%s' )";
				query = String.format(query, inputName, inputName);
			}
		}
		

		return this.queryProxy().query(query, JhndtReportRegis.class).setParameter("cId", cId)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getList(c -> toDomain(c));
	}

}
