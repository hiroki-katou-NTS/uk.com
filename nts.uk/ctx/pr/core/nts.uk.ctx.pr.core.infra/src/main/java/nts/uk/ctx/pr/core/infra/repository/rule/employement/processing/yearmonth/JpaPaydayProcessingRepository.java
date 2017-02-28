package nts.uk.ctx.pr.core.infra.repository.rule.employement.processing.yearmonth;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.*;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.paydayprocessing.*;
import nts.uk.ctx.pr.core.infra.entity.rule.employement.processing.yearmonth.*;

@Stateless
public class JpaPaydayProcessingRepository extends JpaRepository implements PaydayProcessingRepository {

	private final String SELECT_ALL = "SELECT c FROM QpdmtPaydayProcessing c";
	private final String SELECT_ALL_BY_CCD = SELECT_ALL + " WHERE c.qpdmtPaydayProcessingPK.ccd = :companyCode";

	@Override
	public List<PaydayProcessing> findAll3(String companyCode) {
		return this.queryProxy().query(SELECT_ALL_BY_CCD, QpdmtPaydayProcessing.class)
				.setParameter("companyCode", companyCode).getList(c -> toDomain(c));
	}

	private PaydayProcessing toDomain(QpdmtPaydayProcessing entity) {
		return PaydayProcessing.createSimpleFromJavaType(entity.qpdmtPaydayProcessingPK.ccd,
				entity.qpdmtPaydayProcessingPK.processingNo, entity.processingName, entity.dispAtr,
				entity.currentProcessingYm, entity.bonusAtr, entity.bCurrentProcessingYm);
	}
}
