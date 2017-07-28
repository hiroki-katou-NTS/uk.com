package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessFormatSheet;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSheetRepository;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KrcmtBusinessFormatSheet;

public class JpaBusinessFormatSheetRepository extends JpaRepository implements BusinessFormatSheetRepository {

	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtBusinessFormatSheet a ");
		builderString.append("WHERE a.krcmtBusinessFormatSheetPK.companyId = :companyId ");
		builderString.append("WHERE a.krcmtBusinessFormatSheetPK.businessTypeCode = :businessTypeCode ");
		builderString.append("WHERE a.krcmtBusinessFormatSheetPK.sheetNo = :sheetNo ");
		FIND = builderString.toString();
	}

	@Override
	public Optional<BusinessFormatSheet> getSheetInformation(String companyId, BusinessTypeCode businessTypeCode,
			BigDecimal sheetNo) {
		return this.queryProxy().query(FIND, KrcmtBusinessFormatSheet.class).setParameter("companyId", companyId)
				.setParameter("businessTypeCode", businessTypeCode.v()).setParameter("sheetNo", sheetNo)
				.getSingle(f -> toDomain(f));
	}

	private static BusinessFormatSheet toDomain(KrcmtBusinessFormatSheet krcmtBusinessFormatSheet) {
		BusinessFormatSheet businessFormatSheet = BusinessFormatSheet.createJavaTye(
				krcmtBusinessFormatSheet.krcmtBusinessFormatSheetPK.companyId,
				krcmtBusinessFormatSheet.krcmtBusinessFormatSheetPK.businessTypeCode,
				krcmtBusinessFormatSheet.krcmtBusinessFormatSheetPK.sheetNo, krcmtBusinessFormatSheet.sheetName);
		return businessFormatSheet;
	}

}
