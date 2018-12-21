package nts.uk.ctx.at.schedule.infra.repository.shift.specificdayset;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.export.shift.specificdayset.SpecificdaySetCompanyReportData;
import nts.uk.ctx.at.schedule.app.export.shift.specificdayset.SpecificdaySetReportRepository;
import nts.uk.ctx.at.schedule.app.export.shift.specificdayset.SpecificdaySetWorkplaceReportData;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.company.KsmmtComSpecDateSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.workplace.KsmmtWpSpecDateSet;
import nts.uk.ctx.at.shared.app.find.pattern.monthly.setting.Period;

@Stateless
public class JpaSpecificdaySetReportRepository extends JpaRepository implements SpecificdaySetReportRepository {
	
	private static final String GET_COMPANY_SPEC_SET = "SELECT p.name, s FROM KsmmtComSpecDateSet s"
			+ " INNER JOIN KsmstSpecificDateItem p ON p.ksmstSpecificDateItemPK.itemNo = s.ksmmtComSpecDateSetPK.specificDateItemNo"
			+ " WHERE s.ksmmtComSpecDateSetPK.companyId = :companyId AND p.useAtr = 1"
			+ " AND s.ksmmtComSpecDateSetPK.specificDate >= :startYm"
			+ " AND s.ksmmtComSpecDateSetPK.specificDate <= :endYm";
	
	private static final String GET_WORKSPACE_SPEC_SET = "SELECT"
			+ " p.name, w.wkpcd, w.wkpName, s "
			+ "	FROM KsmmtWpSpecDateSet s"
			+ " INNER JOIN KsmstSpecificDateItem p ON s.ksmmtWpSpecDateSetPK.specificDateItemNo = p.ksmstSpecificDateItemPK.itemNo "
			+ " INNER JOIN BsymtWorkplaceInfo w ON w.bsymtWorkplaceInfoPK.cid = :companyId and w.bsymtWorkplaceInfoPK.wkpid = s.ksmmtWpSpecDateSetPK.workplaceId"
			+ " WHERE p.useAtr = 1"
			+ " AND s.ksmmtWpSpecDateSetPK.specificDate >= :startYm"
			+ " AND s.ksmmtWpSpecDateSetPK.specificDate <= :endYm";
	
	private static final String GET_BEGIN_MONTH_COMPANY = "SELECT a.MONTH_STR FROM BCMMT_COMPANY a WHERE  a.CID = ?companyId";
	
	@Override
	public Optional<Map<String, List<SpecificdaySetCompanyReportData>>> findAllSpecificdaySetCompany(String companyId, GeneralDate startDate, GeneralDate endDate) {
		List<SpecificdaySetCompanyReportData> data = this.queryProxy().query(GET_COMPANY_SPEC_SET, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("startYm", startDate)
				.setParameter("endYm", endDate)
				.getList(x -> toDomainWithCompany(x));
		return Optional.ofNullable(data.stream().collect(Collectors.groupingBy(SpecificdaySetCompanyReportData::getYearMonth)));
	}


	@Override
	public Optional<Map<String, List<SpecificdaySetWorkplaceReportData>>> findAllSpecificdaySetWorkplace(String companyId, GeneralDate startDate, GeneralDate endDate) {
		List<SpecificdaySetWorkplaceReportData> data = this.queryProxy().query(GET_WORKSPACE_SPEC_SET, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("startYm", startDate)
				.setParameter("endYm", endDate)
				.getList(x -> toDomainWithWorkspace(x));
		return Optional.of(data.stream().collect(Collectors.groupingBy(SpecificdaySetWorkplaceReportData::getWorkplaceCode)));
	}
	
	
	private static SpecificdaySetCompanyReportData toDomainWithCompany(Object[] object) {
		String specificDateItemName = (String) object[0];
		KsmmtComSpecDateSet entity = (KsmmtComSpecDateSet) object[1];
		SpecificdaySetCompanyReportData domain = SpecificdaySetCompanyReportData.createFromJavaType(
				entity.ksmmtComSpecDateSetPK.companyId, entity.ksmmtComSpecDateSetPK.specificDate,
				entity.ksmmtComSpecDateSetPK.specificDateItemNo, specificDateItemName);
		return domain;
	}
		
	private static SpecificdaySetWorkplaceReportData toDomainWithWorkspace(Object[] object) {
		String specificDateItemName = (String) object[0];
		String workplaceCode = (String) object[1];
		String workplaceName = (String) object[2];
		KsmmtWpSpecDateSet entity = (KsmmtWpSpecDateSet) object[3];
		String workplaceId = entity.ksmmtWpSpecDateSetPK.workplaceId;
		SpecificdaySetWorkplaceReportData domain = SpecificdaySetWorkplaceReportData.createFromJavaType(
				workplaceId, entity.ksmmtWpSpecDateSetPK.specificDate,
				entity.ksmmtWpSpecDateSetPK.specificDateItemNo, specificDateItemName, workplaceCode, workplaceName);
		return domain;
	}
	
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

}
