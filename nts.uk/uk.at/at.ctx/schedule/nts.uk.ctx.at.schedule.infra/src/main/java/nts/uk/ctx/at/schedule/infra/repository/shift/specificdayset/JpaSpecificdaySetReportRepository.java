package nts.uk.ctx.at.schedule.infra.repository.shift.specificdayset;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.app.export.shift.specificdayset.SpecificdaySetReportData;
import nts.uk.ctx.at.schedule.app.export.shift.specificdayset.SpecificdaySetReportRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.company.KsmmtComSpecDateSet;

@Stateless
public class JpaSpecificdaySetReportRepository extends JpaRepository implements SpecificdaySetReportRepository {
	
	private static final String GET_BY_USE_WITH_NAME = "SELECT p.name, s FROM KsmmtComSpecDateSet s"
			+ " INNER JOIN KsmstSpecificDateItem p ON p.ksmstSpecificDateItemPK.itemNo = s.ksmmtComSpecDateSetPK.specificDateItemNo"
			+ " WHERE s.ksmmtComSpecDateSetPK.companyId = :companyId AND p.useAtr = 1";
	
	
	@Override
	public Optional<Map<String, List<SpecificdaySetReportData>>> findAllSpecificdaySetCompany(String companyId) {
		List<SpecificdaySetReportData> data = this.queryProxy().query(GET_BY_USE_WITH_NAME, Object[].class)
				.setParameter("companyId", companyId)
				.getList(x -> toDomainWithName(x));
		return Optional.ofNullable(data.stream().collect(Collectors.groupingBy(SpecificdaySetReportData::getYearMonth)));
	}



	@Override
	public Optional<Map<String, List<SpecificdaySetReportData>>> findAllSpecificdaySetWorkplace(String companyId) {
		List<SpecificdaySetReportData> data = this.queryProxy().query(GET_BY_USE_WITH_NAME, Object[].class)
				.setParameter("companyId", companyId)
				.getList(x -> toDomainWithName(x));
		return Optional.of(data.stream().collect(Collectors.groupingBy(SpecificdaySetReportData::getYearMonth)));
	}
	
	
	private static SpecificdaySetReportData toDomainWithName(Object[] object) {
		String specificDateItemName = (String) object[0];
		KsmmtComSpecDateSet entity = (KsmmtComSpecDateSet) object[1];
		SpecificdaySetReportData domain = SpecificdaySetReportData.createFromJavaType(
				entity.ksmmtComSpecDateSetPK.companyId, entity.ksmmtComSpecDateSetPK.specificDate,
				entity.ksmmtComSpecDateSetPK.specificDateItemNo, specificDateItemName);
		return domain;
	}

	

}
