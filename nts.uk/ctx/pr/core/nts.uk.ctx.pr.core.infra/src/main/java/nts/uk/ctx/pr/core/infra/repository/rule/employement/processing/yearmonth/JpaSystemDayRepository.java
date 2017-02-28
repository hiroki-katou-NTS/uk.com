package nts.uk.ctx.pr.core.infra.repository.rule.employement.processing.yearmonth;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.SystemDayRepository;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.systemday.SystemDay;
import nts.uk.ctx.pr.core.infra.entity.rule.employement.processing.yearmonth.QpdmtSystemDay;

public class JpaSystemDayRepository extends JpaRepository implements SystemDayRepository {

	private final String SELECT_ALL = "SELECT c FROM QpdmtSystemDay c";
	private final String SELECT_ALL_BY_CCD = SELECT_ALL + " WHERE c.qpdmtSystemDayPk.ccd = :companyCode";
	private final String SELECT_ALL_BY_CCD_AND_PROCESSING_NO = SELECT_ALL_BY_CCD
			+ " AND c.qpdmtSystemDayPk.processingNo = :processingNo";

	@Override
	public List<SystemDay> findAll(String companyCode, String processingNo) {
		return this.queryProxy().query(SELECT_ALL_BY_CCD_AND_PROCESSING_NO, QpdmtSystemDay.class)
				.setParameter("companyCode", companyCode).setParameter("processingNo", processingNo)
				.getList(c -> toDomain(c));
	}

	
	private SystemDay toDomain(QpdmtSystemDay entity) {
		return SystemDay.createSimpleFromJavaType(entity.qpdmtSystemDayPk.ccd, entity.qpdmtSystemDayPk.processingNo,
				entity.socialInsuLevyMonAtr, entity.resitaxStdMon, entity.resitaxStdDay, entity.resitaxBeginMon,
				entity.pickupStdMonAtr, entity.pickupStdDay, entity.payStdDay, entity.accountDueMonAtr,
				entity.accountDueDay);
	}
}
