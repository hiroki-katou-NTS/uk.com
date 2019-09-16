/**
 * 2:14:20 PM Aug 21, 2017
 */
package nts.uk.screen.at.infra.dailyperformance.correction;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KrcmtBusinessTypeSDaily;
import nts.uk.screen.at.app.dailyperformance.correction.dto.FormatDPCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.mobile.DPScreenRepoMob;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ductm
 *
 */
@Stateless
public class JpaDPScreenRepoMob extends JpaRepository implements DPScreenRepoMob  {

	private final static String SEL_FORMAT_DP_CORRECTION;

	private final static String SEL_FORMAT_DP_CORRECTION_MULTI;

	static {
		StringBuilder builderString = new StringBuilder();

		builderString = new StringBuilder();
		builderString.append("SELECT b");
		builderString.append(" FROM KrcmtBusinessTypeSDaily b");
		builderString.append(" WHERE b.krcmtBusinessTypeMobileDailyPK.companyId = :companyId");
		builderString.append(" AND b.krcmtBusinessTypeMobileDailyPK.businessTypeCode IN :lstBusinessTypeCode ");
		builderString.append(" ORDER BY b.order ASC, b.krcmtBusinessTypeMobileDailyPK.attendanceItemId ASC");
		SEL_FORMAT_DP_CORRECTION = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT b");
		builderString.append(" FROM KrcmtBusinessTypeSDaily b INNER JOIN");
		builderString.append(" KrcstBusinessTypeSorted s");
		builderString.append(" WHERE s.krcstBusinessTypeSortedPK.companyId = :companyId");
		builderString.append(
				" AND b.krcmtBusinessTypeMobileDailyPK.attendanceItemId = s.krcstBusinessTypeSortedPK.attendanceItemId");
		builderString.append(" AND b.krcmtBusinessTypeMobileDailyPK.companyId = :companyId");
		builderString.append(" AND b.krcmtBusinessTypeMobileDailyPK.businessTypeCode IN :lstBusinessTypeCode ");
		builderString.append(" ORDER BY s.order ASC, b.krcmtBusinessTypeMobileDailyPK.attendanceItemId ASC");
		SEL_FORMAT_DP_CORRECTION_MULTI = builderString.toString();

	}

	public List<FormatDPCorrectionDto> getListFormatDPCorrection(List<String> lstBusinessType) {
		if (lstBusinessType.size() > 1) {
			return this.queryProxy().query(SEL_FORMAT_DP_CORRECTION_MULTI, KrcmtBusinessTypeSDaily.class)
					.setParameter("companyId", AppContexts.user().companyId())
					.setParameter("lstBusinessTypeCode", lstBusinessType).getList().stream()
					.map(f -> new FormatDPCorrectionDto(f.krcmtBusinessTypeMobileDailyPK.companyId,
							f.krcmtBusinessTypeMobileDailyPK.businessTypeCode, f.krcmtBusinessTypeMobileDailyPK.attendanceItemId,
							null,
							f.order, null))
					.distinct().collect(Collectors.toList());
		} else {
			return this.queryProxy().query(SEL_FORMAT_DP_CORRECTION, KrcmtBusinessTypeSDaily.class)
					.setParameter("companyId", AppContexts.user().companyId())
					.setParameter("lstBusinessTypeCode", lstBusinessType).getList().stream()
					.map(f -> new FormatDPCorrectionDto(f.krcmtBusinessTypeMobileDailyPK.companyId,
							f.krcmtBusinessTypeMobileDailyPK.businessTypeCode, f.krcmtBusinessTypeMobileDailyPK.attendanceItemId,
							null,
							f.order, null))
					.distinct().collect(Collectors.toList());
		}
	}

}
