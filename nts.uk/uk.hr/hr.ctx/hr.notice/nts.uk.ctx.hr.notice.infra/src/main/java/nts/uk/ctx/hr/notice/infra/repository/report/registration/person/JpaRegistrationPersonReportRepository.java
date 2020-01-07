/**
 * 
 */
package nts.uk.ctx.hr.notice.infra.repository.report.registration.person;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportRegis;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportRegisPK;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaRegistrationPersonReportRepository extends JpaRepository implements RegistrationPersonReportRepository {

	private static final String getListBySId = "select c FROM  JhndtReportRegis c Where c.pk.cid = :cid and c.inputSid = :sid  ORDER BY c.reportName ASC";
	private static final String GET_MAX_REPORT_ID = "SELECT MAX(a.pk.reportId) FROM JhndtReportRegis a WHERE c.pk.cid = :cid and c.inputSid = :sid";

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
		return this.queryProxy().query(getListBySId, JhndtReportRegis.class).setParameter("sid", sid)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<RegistrationPersonReport> getDomain(String cid, Integer reportId) {
		if (reportId == null) {
			return Optional.empty();
		}
		Optional<JhndtReportRegis> entityOpt = this.queryProxy().find(new JhndtReportRegisPK(cid, reportId),
				JhndtReportRegis.class);
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
	public List<RegistrationPersonReport> findByJHN003(String cId, String sId, GeneralDate startDate,
			GeneralDate endDate, Integer reportId, Integer approvalStatus, String inputName, boolean approvalReport) {

		String query = SEL;

		if (approvalReport) {
			query += " INNER JOIN JhndtReportApproval a" + " ON r.pk.reportID = a.pk.reportID";
		}

		query += " WHERE r.pk.cid = @cId AND r.appDate BETWEEN @startDate AND @endDate";

		if (reportId != null) {
			query += "AND r.reportLayoutID = %r";
			String.format(query, reportId);
		}

		if (approvalStatus != null) {
			if (approvalReport) {
				query += " AND a.aprStatusName = %s";
				query += " AND r.aprStatus = %s";
				String.format(query, approvalStatus, approvalStatus);
			} else {
				query += " AND r.aprStatus = %s";
				String.format(query, approvalStatus);
			}
		}

		if (inputName != null) {
			if (approvalReport) {
				query += " AND (r.inputBussinessName LIKE %n OR r.appBussinessName LIKE %n )";
				String.format(query, inputName);
			} else {
				query += "AND r.inputSid = %s";
				String.format(query, sId);
			}
		}

		return this.queryProxy().query(query, JhndtReportRegis.class).setParameter("cId", cId)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getList(c -> toDomain(c));
	}

}
