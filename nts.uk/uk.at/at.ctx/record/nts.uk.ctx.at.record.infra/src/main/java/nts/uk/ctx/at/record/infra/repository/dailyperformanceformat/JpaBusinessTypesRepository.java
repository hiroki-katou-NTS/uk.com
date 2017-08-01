package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessType;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypesRepository;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KrcmtBusinessType;

@Stateless
public class JpaBusinessTypesRepository extends JpaRepository implements BusinessTypesRepository {

	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KdwmtWorkType a ");
		builderString.append("WHERE a.kdwmtWorkTypePK.companyId = :companyId ");
		FIND = builderString.toString();
	}

	@Override
	public List<BusinessType> findAll(String companyId) {
		return this.queryProxy().query(FIND, KrcmtBusinessType.class).setParameter("companyId", companyId)
				.getList(f -> toDomain(f));
	}

	private static BusinessType toDomain(KrcmtBusinessType krcmtBusinessType) {
		BusinessType workType = BusinessType.createFromJavaType(
				krcmtBusinessType.krcmtBusinessTypePK.companyId,
				krcmtBusinessType.krcmtBusinessTypePK.businessTypeCode,
				krcmtBusinessType.businessTypeName);

		return workType;
	}

}
