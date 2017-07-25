package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.DailyReportOperation;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.DailyReportOperationRepository;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KdwmtDailyReportOperation;

public class JpaDailyReportOperationRepository extends JpaRepository implements DailyReportOperationRepository {

	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KdwmtDailyReportOperation a ");
		builderString.append("WHERE a.kdwmtDailyReportOperationPK.companyId = :companyId ");
		FIND = builderString.toString();
	}

	@Override
	public Optional<DailyReportOperation> getSettingUnit(String companyId) {
		return this.queryProxy().query(FIND, KdwmtDailyReportOperation.class).setParameter("companyId", companyId)
				.getSingle(f -> toDomain(f));
	}

	private static DailyReportOperation toDomain(KdwmtDailyReportOperation kmkmtAgreementMonthSet) {

		DailyReportOperation dailyReportOperation = DailyReportOperation.createFromJavaType(
				kmkmtAgreementMonthSet.kdwmtDailyReportOperationPK.companyId, kmkmtAgreementMonthSet.settingUnit.intValue());

		return dailyReportOperation;
	}

}
