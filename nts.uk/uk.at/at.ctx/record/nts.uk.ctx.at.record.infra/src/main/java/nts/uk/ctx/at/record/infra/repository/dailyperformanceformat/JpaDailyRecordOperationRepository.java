package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.DailyRecordOperation;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.DailyRecordOperationRepository;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KrcstDailyRecordOperation;

@Stateless
public class JpaDailyRecordOperationRepository extends JpaRepository implements DailyRecordOperationRepository {

	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcstDailyRecordOperation a ");
		builderString.append("WHERE a.krcstDailyRecordOperationPK.companyId = :companyId ");
		FIND = builderString.toString();
	}

	@Override
	public Optional<DailyRecordOperation> getSettingUnit(String companyId) {
		return this.queryProxy().query(FIND, KrcstDailyRecordOperation.class).setParameter("companyId", companyId)
				.getSingle(f -> toDomain(f));
	}

	private static DailyRecordOperation toDomain(KrcstDailyRecordOperation krcstDailyRecordOperation) {

		DailyRecordOperation dailyReportOperation = DailyRecordOperation.createFromJavaType(
				krcstDailyRecordOperation.krcstDailyRecordOperationPK.companyId,
				krcstDailyRecordOperation.settingUnit.intValue());

		return dailyReportOperation;
	}

}
