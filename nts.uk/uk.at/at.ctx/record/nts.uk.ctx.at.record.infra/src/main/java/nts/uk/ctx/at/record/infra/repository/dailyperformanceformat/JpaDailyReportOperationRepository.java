package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.DailyReportOperation;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.DailyReportOperationRepository;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KrcstDailyRecordOperation;

@Stateless
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
		return this.queryProxy().query(FIND, KrcstDailyRecordOperation.class).setParameter("companyId", companyId)
				.getSingle(f -> toDomain(f));
	}

	private static DailyReportOperation toDomain(KrcstDailyRecordOperation krcstDailyRecordOperation) {

		DailyReportOperation dailyReportOperation = DailyReportOperation.createFromJavaType(
				krcstDailyRecordOperation.krcstDailyRecordOperationPK.companyId,
				krcstDailyRecordOperation.settingUnit.intValue());

		return dailyReportOperation;
	}

}
