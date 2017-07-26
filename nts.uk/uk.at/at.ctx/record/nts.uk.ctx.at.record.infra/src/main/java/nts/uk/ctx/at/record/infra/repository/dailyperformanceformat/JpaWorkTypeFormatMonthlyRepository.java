package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.WorkTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.WorkTypeFormatMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KdwmtWorkTypeMonthly;

@Stateless
public class JpaWorkTypeFormatMonthlyRepository extends JpaRepository implements WorkTypeFormatMonthlyRepository {

	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KdwmtWorkTypeMonthly a ");
		builderString.append("WHERE a.kdwmtWorkTypeMonthlyPK.companyId = :companyId ");
		builderString.append("WHERE a.kdwmtWorkTypeMonthlyPK.workTypeCode = :workTypeCode ");
		FIND = builderString.toString();
	}

	@Override
	public List<WorkTypeFormatMonthly> getMonthlyDetail(String companyId, String workTypeCode) {
		return this.queryProxy().query(FIND, KdwmtWorkTypeMonthly.class).setParameter("companyId", companyId)
				.setParameter("workTypeCode", workTypeCode).getList(f -> toDomain(f));
	}

	private static WorkTypeFormatMonthly toDomain(KdwmtWorkTypeMonthly kdwmtWorkTypeMonthly) {
		WorkTypeFormatMonthly workTypeFormatMonthly = WorkTypeFormatMonthly.createFromJavaType(
				kdwmtWorkTypeMonthly.kdwmtWorkTypeMonthlyPK.companyId,
				kdwmtWorkTypeMonthly.kdwmtWorkTypeMonthlyPK.workTypeCode,
				kdwmtWorkTypeMonthly.kdwmtWorkTypeMonthlyPK.attendanceItemId, kdwmtWorkTypeMonthly.order,
				kdwmtWorkTypeMonthly.columnWidth);
		return workTypeFormatMonthly;
	}

}
