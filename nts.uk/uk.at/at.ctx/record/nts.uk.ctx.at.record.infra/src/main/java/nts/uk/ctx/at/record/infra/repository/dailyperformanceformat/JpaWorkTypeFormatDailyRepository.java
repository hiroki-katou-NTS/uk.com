package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.WorkTypeFormatDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.WorkTypeFormatDailyRepository;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KdwmtWorkTypeDaily;

public class JpaWorkTypeFormatDailyRepository extends JpaRepository implements WorkTypeFormatDailyRepository {

	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KdwmtWorkTypeDaily a ");
		builderString.append("WHERE a.kdwmtWorkTypeDailyPK.companyId = :companyId ");
		builderString.append("WHERE a.kdwmtWorkTypeDailyPK.workTypeCode = :workTypeCode ");
		FIND = builderString.toString();
	}

	@Override
	public List<WorkTypeFormatDaily> getDailyDetail(String companyId, String workTypeCode) {
		return this.queryProxy().query(FIND, KdwmtWorkTypeDaily.class).setParameter("companyId", companyId)
				.setParameter("workTypeCode", workTypeCode).getList(f -> toDomain(f));
	}

	private static WorkTypeFormatDaily toDomain(KdwmtWorkTypeDaily kdwmtWorkTypeDaily) {
		WorkTypeFormatDaily workTypeFormatDaily = WorkTypeFormatDaily.createFromJavaType(
				kdwmtWorkTypeDaily.kdwmtWorkTypeDailyPK.companyId, kdwmtWorkTypeDaily.kdwmtWorkTypeDailyPK.workTypeCode,
				kdwmtWorkTypeDaily.kdwmtWorkTypeDailyPK.attendanceItemId, kdwmtWorkTypeDaily.sheetName,
				kdwmtWorkTypeDaily.sheetNo, kdwmtWorkTypeDaily.order, kdwmtWorkTypeDaily.columnWidth);
		return workTypeFormatDaily;
	}

}
