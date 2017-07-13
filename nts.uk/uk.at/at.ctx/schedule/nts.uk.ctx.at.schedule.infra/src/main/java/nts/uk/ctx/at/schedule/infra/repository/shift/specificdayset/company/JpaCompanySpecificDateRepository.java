package nts.uk.ctx.at.schedule.infra.repository.shift.specificdayset.company;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.company.KsmmtComSpecDateSet;

@Stateless
public class JpaCompanySpecificDateRepository extends JpaRepository implements CompanySpecificDateRepository {

	private static final String SELECT_NO_WHERE = "SELECT s FROM KsmmtComSpecDateSet s";

	private static final String GET_BY_DATE = SELECT_NO_WHERE 
			+ " WHERE s.ksmmtComSpecDateSetPK.companyId = :companyId"
			+ " AND s.ksmmtComSpecDateSetPK.specificDate = :specificDate";

	@Override
	public List<CompanySpecificDateItem> getComSpecByDate(String companyId, int specificDate) {
		return this.queryProxy().query(GET_BY_DATE, KsmmtComSpecDateSet.class)
				.setParameter("companyId", companyId)
				.setParameter("specificDate", specificDate)
				.getList(x -> toDomain(x));
	}

	private static CompanySpecificDateItem toDomain(KsmmtComSpecDateSet entity) {
		CompanySpecificDateItem domain = CompanySpecificDateItem.createFromJavaType(
				entity.ksmmtComSpecDateSetPK.companyId, entity.ksmmtComSpecDateSetPK.specificDate,
				entity.ksmmtComSpecDateSetPK.specificDateItemNo);
		return domain;
	}
}
