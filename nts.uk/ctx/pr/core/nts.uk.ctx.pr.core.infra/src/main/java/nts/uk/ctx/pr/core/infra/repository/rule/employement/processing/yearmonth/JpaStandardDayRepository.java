package nts.uk.ctx.pr.core.infra.repository.rule.employement.processing.yearmonth;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.StandardDayRepository;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.standardday.StandardDay;
import nts.uk.ctx.pr.core.infra.entity.rule.employement.processing.yearmonth.QpdmtStandardDay;

@Stateless
public class JpaStandardDayRepository extends JpaRepository implements StandardDayRepository {
	private final String SELECT_ALL = "SELECT c FROM QpdmtStandardDay c";
	private final String SELECT_ALL_BY_CCD = SELECT_ALL + " WHERE c.qpdmtStandardDayPk.ccd = :companyCode";
	private final String SELECT_ALL_BY_CCD_AND_PROCESSING_NO = SELECT_ALL_BY_CCD
			+ " AND c.qpdmtStandardDayPk.processingNo = :processingNo";

	@Override
	public List<StandardDay> findAll(String companyCode, String processingNo) {
		return this.queryProxy().query(SELECT_ALL_BY_CCD_AND_PROCESSING_NO, QpdmtStandardDay.class)
				.setParameter("companyCode", companyCode).setParameter("processingNo", processingNo)
				.getList(c -> toDomain(c));
	}

	private StandardDay toDomain(QpdmtStandardDay entity) {
		return StandardDay.createSimpleFromJavaType(entity.qpdmtStandardDayPk.ccd,
				entity.qpdmtStandardDayPk.processingNo, entity.socialInsStdYearAtr, entity.socialInsStdMon,
				entity.socialInsStdDay, entity.incometaxStdYearAtr, entity.incometaxStdMon, entity.incometaxStdDay,
				entity.empInsStdMon, entity.empInsStdDay);
	}
}
