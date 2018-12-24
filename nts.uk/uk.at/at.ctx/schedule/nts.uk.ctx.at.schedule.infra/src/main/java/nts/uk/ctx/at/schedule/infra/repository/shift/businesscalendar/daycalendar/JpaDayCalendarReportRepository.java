package nts.uk.ctx.at.schedule.infra.repository.shift.businesscalendar.daycalendar;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.export.shift.businesscalendar.daycalendar.ClassCalendarReportData;
import nts.uk.ctx.at.schedule.app.export.shift.businesscalendar.daycalendar.CompanyCalendarReportData;
import nts.uk.ctx.at.schedule.app.export.shift.businesscalendar.daycalendar.DayCalendarReportRepository;
import nts.uk.ctx.at.schedule.app.export.shift.businesscalendar.daycalendar.WorkplaceCalendarReportData;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.daycalendar.KsmmtCalendarClass;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.daycalendar.KsmmtCalendarCompany;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.daycalendar.KsmmtCalendarWorkplace;
import nts.uk.ctx.at.shared.app.find.pattern.monthly.setting.Period;

@Stateless
public class JpaDayCalendarReportRepository extends JpaRepository implements DayCalendarReportRepository{

	private static final String GET_BEGIN_MONTH_COMPANY = "SELECT a.MONTH_STR FROM BCMMT_COMPANY a WHERE  a.CID = ?companyId";
	private static final String SELECT_COMPANY_CALENDAR_BY_DATE = " SELECT c FROM KsmmtCalendarCompany c " 
			+ " WHERE c.ksmmtCalendarCompanyPK.companyId = :companyId"
			+ " AND c.ksmmtCalendarCompanyPK.date >= :startDate "
			+ " AND c.ksmmtCalendarCompanyPK.date <= :endDate";
	
	private static final String GET_WORKSPACE_CALENDAR_BY_DATE = "SELECT"
			+ " w.wkpcd, w.wkpName, s "
			+ "	FROM KsmmtCalendarWorkplace s"
			+ " INNER JOIN BsymtWorkplaceInfo w ON w.bsymtWorkplaceInfoPK.cid = :companyId and w.bsymtWorkplaceInfoPK.wkpid = s.ksmmtCalendarWorkplacePK.workPlaceId"
			+ " WHERE s.ksmmtCalendarWorkplacePK.date >= :startYm"
			+ " AND s.ksmmtCalendarWorkplacePK.date <= :endYm";
//	BsymtClassification
	
	private static final String GET_CLASS_CALENDAR_BY_DATE = "SELECT"
			+ " w.bsymtClassificationPK.clscd, w.clsname, s "
			+ "	FROM KsmmtCalendarClass s"
			+ " INNER JOIN BsymtClassification w ON w.bsymtClassificationPK.cid = :companyId and w.bsymtClassificationPK.clscd = s.ksmmtCalendarClassPK.classId"
			+ " WHERE s.ksmmtCalendarClassPK.date >= :startYm"
			+ " AND s.ksmmtCalendarClassPK.date <= :endYm";
	
	
	@Override
	public Period getBaseDateByCompany(String companyId, GeneralDate startExportDate, GeneralDate endExportDate) {
		Integer beginMonth = ((BigDecimal)this.getEntityManager().createNativeQuery(GET_BEGIN_MONTH_COMPANY)
				.setParameter("companyId", companyId).getSingleResult()).intValue();
		int startYear = startExportDate.year();
		int endYear = endExportDate.year();
		
		GeneralDate start = GeneralDate.ymd(startYear, beginMonth, 1);
		
		int endMonth = beginMonth - 1;
		if (endMonth > 0) {
			endYear = endYear + 1;
		}
		else {
			endMonth = 12;
		}
		GeneralDate startdateOfEndMonth = GeneralDate.ymd(endYear, endMonth, 1);
		GeneralDate end = GeneralDate.ymd(endYear, endMonth, startdateOfEndMonth.lastDateInMonth());
		
		return new Period(start, end);
	}

	@Override
	public Optional<Map<String, List<CompanyCalendarReportData>>> findCalendarCompanyByDate(String companyId, GeneralDate startDate,
			GeneralDate endDate) {
		List<CompanyCalendarReportData> data = this.queryProxy().query(SELECT_COMPANY_CALENDAR_BY_DATE, KsmmtCalendarCompany.class)
				.setParameter("companyId", companyId)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(x -> toDomainCalendarCompany(x));
		return Optional.ofNullable(data.stream().collect(Collectors.groupingBy(CompanyCalendarReportData::getYearMonth)));
	}
	
	/**
	 * toDomanin calendar company
	 * @param entity
	 * @return
	 */
	private static CompanyCalendarReportData toDomainCalendarCompany(KsmmtCalendarCompany entity){
		val domain = CompanyCalendarReportData.createFromJavaType(
				entity.ksmmtCalendarCompanyPK.companyId,
				entity.ksmmtCalendarCompanyPK.date, 
				entity.workingDayAtr);
		return domain;
	}
	
	
	@Override
	public Optional<Map<String, List<WorkplaceCalendarReportData>>> findCalendarWorkplaceByDate(String companyId, GeneralDate startDate, GeneralDate endDate) {
		List<WorkplaceCalendarReportData> data = this.queryProxy().query(GET_WORKSPACE_CALENDAR_BY_DATE, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("startYm", startDate)
				.setParameter("endYm", endDate)
				.getList(x -> toDomainWithWorkspace(x));
		return Optional.of(data.stream().collect(Collectors.groupingBy(WorkplaceCalendarReportData::getWorkplaceCode)));
	}

	private static WorkplaceCalendarReportData toDomainWithWorkspace(Object[] object) {
		String workplaceCode = (String) object[0];
		String workplaceName = (String) object[1];
		KsmmtCalendarWorkplace entity = (KsmmtCalendarWorkplace) object[2];
		WorkplaceCalendarReportData domain = WorkplaceCalendarReportData.createFromJavaType(
				entity.ksmmtCalendarWorkplacePK.workPlaceId,
				entity.ksmmtCalendarWorkplacePK.date,
				entity.workingDayAtr,
				workplaceCode, workplaceName);
		return domain;
	}
	
	@Override
	public Optional<Map<String, List<ClassCalendarReportData>>> findCalendarClassByDate(String companyId, GeneralDate startDate, GeneralDate endDate) {
		List<ClassCalendarReportData> data = this.queryProxy().query(GET_CLASS_CALENDAR_BY_DATE, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("startYm", startDate)
				.setParameter("endYm", endDate)
				.getList(x -> toDomainWithClass(x));
		return Optional.of(data.stream().collect(Collectors.groupingBy(ClassCalendarReportData::getClassCode)));
	}

	private static ClassCalendarReportData toDomainWithClass(Object[] object) {
		String classCode = (String) object[0];
		String className = (String) object[1];
		KsmmtCalendarClass entity = (KsmmtCalendarClass) object[2];
		ClassCalendarReportData domain = ClassCalendarReportData.createFromJavaType(
				entity.ksmmtCalendarClassPK.classId,
				entity.ksmmtCalendarClassPK.date,
				entity.workingDayAtr,
				classCode, className);
		return domain;
	}
}
