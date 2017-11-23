package nts.uk.screen.at.infra.dailyperformance.correction;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityDailyPerformanceFormat;
import nts.uk.screen.at.app.dailyperformance.correction.DisplayFormatSelectionRepository;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceFormatDto;

@Stateless
public class JpaDisplayFormatSelectionRepository extends JpaRepository implements DisplayFormatSelectionRepository {

	private final String FIND_DAILY_PERFORMANCE_FORMAT_BY_COMPANY =
			"SELECT a FROM KfnmtAuthorityDailyPerformanceFormat a WHERE a.kfnmtAuthorityDailyPerformanceFormatPK.companyId = :companyId AND a.kfnmtAuthorityDailyPerformanceFormatPK.dailyPerformanceFormatCode IN :codeList";

	@Override
	public List<DailyPerformanceFormatDto> getDailyPerformanceFormatList(String companyId, List<String> codeList) {
		List<KfnmtAuthorityDailyPerformanceFormat> lstData =
				this.queryProxy().query(FIND_DAILY_PERFORMANCE_FORMAT_BY_COMPANY, KfnmtAuthorityDailyPerformanceFormat.class)
				.setParameter("companyId", companyId)
				.setParameter("codeList", codeList).getList();
		return lstData.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
	}

	private static DailyPerformanceFormatDto toDto(KfnmtAuthorityDailyPerformanceFormat entity) {
		return new DailyPerformanceFormatDto(entity.kfnmtAuthorityDailyPerformanceFormatPK.companyId,
										entity.kfnmtAuthorityDailyPerformanceFormatPK.dailyPerformanceFormatCode,
										entity.dailyPerformanceFormatName);
	}
}
