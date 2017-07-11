package nts.uk.ctx.at.schedule.infra.repository.shift.specificdayset.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.company.KsmmtComSpecDateSet;

@Stateless
public class JpaCompanySpecificDateRepository extends JpaRepository implements CompanySpecificDateRepository{
	
	
	private static final String GET_BY_DATE = "SELECT s FROM KsmmtComSpecDateSet s WHERE s.companyId = :companyId AND s.specificDate =: specificDate ";
	
	
	@Override
	public Optional<CompanySpecificDateItem> getComSpecByDate(String companyId, int specificDate) {
		return this.queryProxy().query(GET_BY_DATE, KsmmtComSpecDateSet.class)
		.setParameter("companyId", companyId)
		.setParameter("specificDate", specificDate)
		.getSingle(x -> toDomain(x));
	}
	private CompanySpecificDateItem toDomain(KsmmtComSpecDateSet ksmmtComSpecDate){
		return CompanySpecificDateItem.createFromJavaType(ksmmtComSpecDate.companyId, ksmmtComSpecDate.specificDate,ksmmtComSpecDate.specificDateItemNo);
	}
}
