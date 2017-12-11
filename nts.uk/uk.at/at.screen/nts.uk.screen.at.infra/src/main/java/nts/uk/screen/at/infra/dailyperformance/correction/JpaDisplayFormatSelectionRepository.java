package nts.uk.screen.at.infra.dailyperformance.correction;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityDailyPerformanceFormat;
//import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityDailyPerformanceFormat;
import nts.uk.screen.at.app.dailyperformance.correction.DisplayFormatSelectionRepository;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceFormatDto;

@Stateless
public class JpaDisplayFormatSelectionRepository extends JpaRepository implements DisplayFormatSelectionRepository {

	private final static String FIND_DAILY_PERFORMANCE_FORMAT_BY_COMPANY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KfnmtAuthorityDailyPerformanceFormat a ");
		builderString.append("WHERE a.kfnmtAuthorityDailyPerformanceFormatPK.companyId = :companyId ");
		FIND_DAILY_PERFORMANCE_FORMAT_BY_COMPANY = builderString.toString();
	}
	
	@Override
	public List<DailyPerformanceFormatDto> getDailyPerformanceFormatList(String companyId) {
		List<KfnmtAuthorityDailyPerformanceFormat> lstData = this.queryProxy().query(FIND_DAILY_PERFORMANCE_FORMAT_BY_COMPANY, KfnmtAuthorityDailyPerformanceFormat.class)
				.setParameter("companyId", companyId).getList();
		return lstData.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
	}

	private static DailyPerformanceFormatDto toDto(KfnmtAuthorityDailyPerformanceFormat entity) {
		return new DailyPerformanceFormatDto(entity.kfnmtAuthorityDailyPerformanceFormatPK.companyId,
										entity.kfnmtAuthorityDailyPerformanceFormatPK.dailyPerformanceFormatCode,
										entity.dailyPerformanceFormatName);
	}
}
