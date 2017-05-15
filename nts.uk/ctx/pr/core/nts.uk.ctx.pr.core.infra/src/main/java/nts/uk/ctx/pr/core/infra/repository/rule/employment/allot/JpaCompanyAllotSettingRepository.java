package nts.uk.ctx.pr.core.infra.repository.rule.employment.allot;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.CompanyAllotSetting;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.CompanyAllotSettingRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.allot.QstmtStmtAllotCp;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.allot.QstmtStmtAllotCpPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaCompanyAllotSettingRepository extends JpaRepository implements CompanyAllotSettingRepository {
	private final String SEL_ALL = "SELECT c FROM QstmtStmtAllotCp c WHERE c.qstmtStmtAllotCpPK.companyCode = :companyCode";
	private final String SEL_01 = SEL_ALL
			+ " AND (c.paymentDetailCode != '00' OR c.bonusDetailCode != '00') ORDER BY c.startDate DESC";
	private final String SEL_09 = SEL_ALL + " ORDER BY c.endDate DESC";

	private final String SEL_MAX = SEL_ALL + " AND c.endDate = :endDate";

	@Override
	public Optional<CompanyAllotSetting> find(String companyCode) {
		List<QstmtStmtAllotCp> psettings = this.queryProxy().query(SEL_01, QstmtStmtAllotCp.class)
				.setParameter("companyCode", companyCode).getList();
		if (psettings.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(toDomain(psettings.get(0)));
	}

	@Override
	public List<CompanyAllotSetting> findAll(String companyCode) {
		return this.queryProxy().query(SEL_01, QstmtStmtAllotCp.class).setParameter("companyCode", companyCode)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<CompanyAllotSetting> findMax(String companyCode, int endDate) {
		return this.queryProxy().query(SEL_MAX, QstmtStmtAllotCp.class).setParameter("companyCode", companyCode)
				.setParameter("endDate", endDate).getSingle(c -> toDomain(c));
	}

	@Override
	public Optional<CompanyAllotSetting> maxStart(String companyCode) {
		return this.queryProxy().query(SEL_09, QstmtStmtAllotCp.class).setParameter("companyCode", companyCode)
				.getSingle(c -> toDomain(c));
	}

	private CompanyAllotSetting toDomain(QstmtStmtAllotCp entity) {
		val domain = CompanyAllotSetting.createFromJavaType(entity.qstmtStmtAllotCpPK.companyCode,
				entity.qstmtStmtAllotCpPK.histId, entity.startDate, entity.endDate, entity.paymentDetailCode,
				entity.bonusDetailCode);
		return domain;
	}

	private QstmtStmtAllotCp toEntity(CompanyAllotSetting companyAllotSetting) {
		return new QstmtStmtAllotCp(
				new QstmtStmtAllotCpPK(AppContexts.user().companyCode(), companyAllotSetting.getHistoryId()),
				companyAllotSetting.getStartDate().v(), companyAllotSetting.getEndDate().v(),
				companyAllotSetting.getPaymentDetailCode().v(), companyAllotSetting.getBonusDetailCode().v());
	}

	@Override
	public void update(CompanyAllotSetting companyAllotSetting) {
		try {
			this.commandProxy().update(toEntity(companyAllotSetting));
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public void insert(CompanyAllotSetting companyAllotSetting) {
		try {
			this.commandProxy().insert(toEntity(companyAllotSetting));
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public void remove(String historyId) {
		String companyCode = AppContexts.user().companyCode();

		this.commandProxy().remove(QstmtStmtAllotCp.class, new QstmtStmtAllotCpPK(companyCode, historyId));
	}

	@Override
	public Optional<CompanyAllotSetting> getPreviousHistory(String companyCode, int endDate) {
		return this.queryProxy().query(SEL_MAX, QstmtStmtAllotCp.class).setParameter("companyCode", companyCode)
				.setParameter("endDate", endDate).getSingle(c -> toDomain(c));
	}

}
