package nts.uk.ctx.pr.core.infra.repository.rule.employment.processing.yearmonth;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.PaydayRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.payday.Payday;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.processing.yearmonth.QpdmtPayday;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.processing.yearmonth.QpdmtPaydayPK;

@Stateless
public class JpaPaydayRepository extends JpaRepository implements PaydayRepository {

	private final String SELECT_ALL = "SELECT c FROM QpdmtPayday c";
	private final String SELECT_ALL_BY_CCD = SELECT_ALL + " WHERE c.qpdmtPaydayPK.ccd = :companyCode";

	private final String SELECT_ALL_1_3 = SELECT_ALL_BY_CCD
			+ " AND c.qpdmtPaydayPK.processingNo = :processingNo AND c.qpdmtPaydayPK.payBonusAtr = :payBonusAtr AND c.qpdmtPaydayPK.processingYm = :processingYm AND c.qpdmtPaydayPK.sparePayAtr = :sparePayAtr";

	private final String SELECT_ALL_2 = SELECT_ALL_BY_CCD
			+ " AND c.qpdmtPaydayPK.payBonusAtr = :payBonusAtr AND c.qpdmtPaydayPK.processingYm = :processingYm AND c.qpdmtPaydayPK.sparePayAtr = :sparePayAtr";

	private final String SELECT_ALL_4 = SELECT_ALL_BY_CCD + " AND c.qpdmtPaydayPK.processingNo = :processingNo";

	private final String SELECT_ALL_5 = SELECT_ALL_BY_CCD
			+ " AND c.qpdmtPaydayPK.processingNo = :processingNo AND c.qpdmtPaydayPK.payBonusAtr = :payBonusAtr";

	private final String SELECT_ALL_6 = SELECT_ALL_BY_CCD
			+ " AND c.qpdmtPaydayPK.processingNo = :processingNo AND c.qpdmtPaydayPK.processingYm = :processingYm ";

	private final String SELECT_ALL_7 = SELECT_ALL_BY_CCD
			+ " AND c.qpdmtPaydayPK.processingNo = :processingNo AND c.qpdmtPaydayPK.payBonusAtr = :payBonusAtr AND c.payDate >= :strYmd AND c.payDate <= :endYmd ";

	private final String SELECT_ALL_11 = SELECT_ALL_BY_CCD
			+ " AND c.qpdmtPaydayPK.processingNo = :processingNo AND c.qpdmtPaydayPK.payBonusAtr = :payBonusAtr AND c.qpdmtPaydayPK.processingYm >= :strYmd AND c.qpdmtPaydayPK.processingYm <= :endYmd ";

	@Override
	public List<Payday> find1_3(String companyCode, int processingNo, int payBonusAtr, int processingYm,
			int sparePayAtr) {
		return this.queryProxy().query(SELECT_ALL_1_3, QpdmtPayday.class).setParameter("companyCode", companyCode)
				.setParameter("processingNo", processingNo).setParameter("payBonusAtr", payBonusAtr)
				.setParameter("processingYm", processingYm).setParameter("sparePayAtr", sparePayAtr)
				.getList(c -> toDomain(c));
	}

	@Override
	public List<Payday> find2(String companyCode, int payBonusAtr, int processingYm, int sparePayAtr) {
		return this.queryProxy().query(SELECT_ALL_2, QpdmtPayday.class).setParameter("companyCode", companyCode)
				.setParameter("payBonusAtr", payBonusAtr).setParameter("processingYm", processingYm)
				.setParameter("sparePayAtr", sparePayAtr).getList(c -> toDomain(c));
	}

	@Override
	public List<Payday> find4(String companyCode, int processingNo) {
		return this.queryProxy().query(SELECT_ALL_4, QpdmtPayday.class).setParameter("companyCode", companyCode)
				.setParameter("processingNo", processingNo).getList(c -> toDomain(c));
	}

	@Override
	public List<Payday> find5(String companyCode, int processingNo, int payBonusAtr) {
		return this.queryProxy().query(SELECT_ALL_5, QpdmtPayday.class).setParameter("companyCode", companyCode)
				.setParameter("processingNo", processingNo).setParameter("payBonusAtr", payBonusAtr)
				.getList(c -> toDomain(c));
	}

	@Override
	public List<Payday> find6(String companyCode, int processingNo, int processingYm) {
		return this.queryProxy().query(SELECT_ALL_6, QpdmtPayday.class).setParameter("companyCode", companyCode)
				.setParameter("processingNo", processingNo).setParameter("processingYm", processingYm)
				.getList(c -> toDomain(c));
	}

	@Override
	public List<Payday> find7(String companyCode, int processingNo, int payBonusAtr, GeneralDate strYmd,
			GeneralDate endYmd) {
		return this.queryProxy().query(SELECT_ALL_7, QpdmtPayday.class).setParameter("companyCode", companyCode)
				.setParameter("processingNo", processingNo).setParameter("payBonusAtr", payBonusAtr)
				.setParameter("strYmd", strYmd).setParameter("endYmd", endYmd).getList(c -> toDomain(c));
	}

	@Override
	public List<Payday> find11(String companyCode, int processingNo, int payBonusAtr, GeneralDate strYmd,
			GeneralDate endYmd) {
		return this.queryProxy().query(SELECT_ALL_11, QpdmtPayday.class).setParameter("companyCode", companyCode)
				.setParameter("processingNo", processingNo).setParameter("payBonusAtr", payBonusAtr)
				.setParameter("strYmd", strYmd).setParameter("endYmd", endYmd).getList(c -> toDomain(c));
	}

	@Override
	public void insert1(Payday domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	private Payday toDomain(QpdmtPayday entity) {
		return Payday.createSimpleFromJavaType(entity.qpdmtPaydayPK.ccd, entity.qpdmtPaydayPK.processingNo,
				entity.qpdmtPaydayPK.payBonusAtr, entity.qpdmtPaydayPK.processingYm, entity.qpdmtPaydayPK.sparePayAtr,
				entity.payDate, entity.stdDate, entity.accountingClosing, entity.socialInsLevyMon,
				entity.socialInsStdDate, entity.incomeTaxStdDate, entity.neededWorkDay, entity.empInsStdDate,
				entity.stmtOutputMon);
	}

	private QpdmtPayday toEntity(Payday domain) {
		QpdmtPaydayPK qpdmtPaydayPK = new QpdmtPaydayPK(domain.getCompanyCode().v(), domain.getProcessingNo().v(),
				domain.getPayBonusAtr().value, domain.getProcessingYm().v(), domain.getSparePayAtr().value);

		return new QpdmtPayday(qpdmtPaydayPK, domain.getPayDate(), domain.getStdDate(),
				domain.getSocialInsLevyMon().v(), domain.getSocialInsStdDate(), domain.getIncomeTaxStdDate(),
				domain.getEmpInsStdDate(), domain.getStmtOutputMon().v(), domain.getNeededWorkDay().v(),
				domain.getAccountingClosing());
	}

}
