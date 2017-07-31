package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KrcmtBusinessTypeDaily;

@Stateless
public class JpaBusinessTypeFormatDailyRepository extends JpaRepository implements BusinessTypeFormatDailyRepository {

	private static final String FIND;
	
	private static final String FIND_DETAIl;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtBusinessTypeDaily a ");
		builderString.append("WHERE a.krcmtBusinessTypeDailyPK.companyId = :companyId ");
		builderString.append("WHERE a.krcmtBusinessTypeDailyPK.businessTypeCode = :businessTypeCode ");
		FIND = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("UPDATE KrcmtBusinessTypeDaily a ");
		builderString.append("WHERE a.krcmtBusinessTypeDailyPK.companyId = :companyId ");
		builderString.append("WHERE a.krcmtBusinessTypeDailyPK.businessTypeCode = :businessTypeCode ");
		builderString.append("WHERE a.sheetNo = :sheetNo ");
		FIND_DETAIl = builderString.toString();
	}

	@Override
	public List<BusinessTypeFormatDaily> getBusinessTypeFormat(String companyId, String businessTypeCode) {
		return this.queryProxy().query(FIND, KrcmtBusinessTypeDaily.class).setParameter("companyId", companyId)
				.setParameter("businessTypeCode", businessTypeCode).getList(f -> toDomain(f));
	}

	@Override
	public List<BusinessTypeFormatDaily> getBusinessTypeFormatDailyDetail(String companyId, String businessTypeCode,
			BigDecimal sheetNo) {

		return this.queryProxy().query(FIND_DETAIl, KrcmtBusinessTypeDaily.class)
				.setParameter("companyId", companyId)
				.setParameter("businessTypeCode", businessTypeCode)
				.setParameter("sheetNo", sheetNo).getList(f -> toDomain(f));
	}

	private static BusinessTypeFormatDaily toDomain(KrcmtBusinessTypeDaily krcmtBusinessTypeDaily) {
		BusinessTypeFormatDaily workTypeFormatDaily = BusinessTypeFormatDaily.createFromJavaType(
				krcmtBusinessTypeDaily.krcmtBusinessTypeDailyPK.companyId,
				krcmtBusinessTypeDaily.krcmtBusinessTypeDailyPK.businessTypeCode,
				krcmtBusinessTypeDaily.krcmtBusinessTypeDailyPK.attendanceItemId,
				krcmtBusinessTypeDaily.sheetNo, krcmtBusinessTypeDaily.order, krcmtBusinessTypeDaily.columnWidth);
		return workTypeFormatDaily;
	}

}
