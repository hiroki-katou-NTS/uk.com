package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KrcmtBusinessTypeMonthly;

@Stateless
public class JpaBusinessTypeFormatMonthlyRepository extends JpaRepository implements BusinessTypeFormatMonthlyRepository {

	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtBusinessTypeMonthly a ");
		builderString.append("WHERE a.krcmtBusinessTypeMonthlyPK.companyId = :companyId ");
		builderString.append("WHERE a.krcmtBusinessTypeMonthlyPK.businessTypeCode = :businessTypeCode ");
		FIND = builderString.toString();
	}

	@Override
	public List<BusinessTypeFormatMonthly> getMonthlyDetail(String companyId, String workTypeCode) {
		return this.queryProxy().query(FIND, KrcmtBusinessTypeMonthly.class).setParameter("companyId", companyId)
				.setParameter("workTypeCode", workTypeCode).getList(f -> toDomain(f));
	}

	private static BusinessTypeFormatMonthly toDomain(KrcmtBusinessTypeMonthly krcmtBusinessTypeMonthly) {
		BusinessTypeFormatMonthly workTypeFormatMonthly = BusinessTypeFormatMonthly.createFromJavaType(
				krcmtBusinessTypeMonthly.krcmtBusinessTypeMonthlyPK.companyId,
				krcmtBusinessTypeMonthly.krcmtBusinessTypeMonthlyPK.businessTypeCode,
				krcmtBusinessTypeMonthly.krcmtBusinessTypeMonthlyPK.attendanceItemId,
				krcmtBusinessTypeMonthly.order,
				krcmtBusinessTypeMonthly.columnWidth);
		return workTypeFormatMonthly;
	}

}
