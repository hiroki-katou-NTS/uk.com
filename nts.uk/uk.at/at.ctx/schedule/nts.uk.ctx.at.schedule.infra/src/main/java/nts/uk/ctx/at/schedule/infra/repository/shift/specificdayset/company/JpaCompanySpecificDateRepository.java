package nts.uk.ctx.at.schedule.infra.repository.shift.specificdayset.company;

import java.util.List;

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

	// get List With Name of Specific
	private static final String GET_BY_USE_WITH_NAME = "SELECT p.name,p.useAtr, s FROM KsmmtComSpecDateSet s"
			+ " INNER JOIN KsmstSpecificDateItem p ON p.itemNo = s.ksmmtComSpecDateSetPK.specificDateItemNo"
			+ " WHERE s.ksmmtComSpecDateSetPK.companyId = :companyId"
			+ " AND CAST(s.ksmmtComSpecDateSetPK.specificDate AS VARCHAR(8)) LIKE CONCAT( :specificDate,'%')"
			+ " AND p.useAtr = :useAtr";

	// No WITH name
	@Override
	public List<CompanySpecificDateItem> getComSpecByDate(String companyId, int specificDate) {
		return this.queryProxy().query(GET_BY_DATE, KsmmtComSpecDateSet.class)
				.setParameter("companyId", companyId)
				.setParameter("specificDate", specificDate).getList(x -> toDomain(x));
	}

	// WITH name
	@Override
	public List<CompanySpecificDateItem> getComSpecByDateWithName(String companyId, String specificDate, int useAtr) {
		return this.queryProxy().query(GET_BY_USE_WITH_NAME, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("specificDate", specificDate)
				.setParameter("useAtr", useAtr)
				.getList(x -> toDomainWithName(x));
	}

	// No with name
	private static CompanySpecificDateItem toDomain(KsmmtComSpecDateSet entity) {
		CompanySpecificDateItem domain = CompanySpecificDateItem.createFromJavaType(
				entity.ksmmtComSpecDateSetPK.companyId, entity.ksmmtComSpecDateSetPK.specificDate,
				entity.ksmmtComSpecDateSetPK.specificDateItemNo, "");
		return domain;
	}

	// With NAME
	private static CompanySpecificDateItem toDomainWithName(Object[] object) {
		String specificDateItemName = (String) object[0];
		KsmmtComSpecDateSet entity = (KsmmtComSpecDateSet) object[2];
		CompanySpecificDateItem domain = CompanySpecificDateItem.createFromJavaType(
				entity.ksmmtComSpecDateSetPK.companyId, entity.ksmmtComSpecDateSetPK.specificDate,
				entity.ksmmtComSpecDateSetPK.specificDateItemNo, specificDateItemName);
		return domain;
	}
}
