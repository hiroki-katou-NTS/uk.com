package nts.uk.ctx.pr.core.infra.repository.socialinsurance.contribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionByGrade;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionRate;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionRateHistory;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionRateRepository;
import nts.uk.ctx.pr.core.infra.entity.socialinsurance.contribution.QpbmtContributionByGrade;
import nts.uk.ctx.pr.core.infra.entity.socialinsurance.contribution.QpbmtContributionRate;
import nts.uk.ctx.pr.core.infra.entity.socialinsurance.contribution.QpbmtContributionRatePk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class JpaContributionRateRepository extends JpaRepository implements ContributionRateRepository {

	private static final String GET_CONTRIBUTION_HISTORY_BY_OFFICE_CODE = "SELECT a FROM QpbmtContributionRate a WHERE a.contributionRatePk.cid = :cid AND a.contributionRatePk.socialInsuranceOfficeCd = :officeCode ORDER BY a.startYearMonth DESC";
	private static final String GET_CONTRIBUTION_BY_GRADE_BY_HISTORY_ID = "SELECT a from QpbmtContributionByGrade a WHERE a.contributionByGradePk.historyId =:historyId";
	private static final String DELETE = "DELETE FROM QpbmtContributionByGrade a WHERE a.contributionHistPk.cid = :cid AND a.contributionByGradePk.socialInsuranceOfficeCd = :officeCode";
	private static final String DELETE_CONTRIBUTION_BY_GRADE_BY_HISTORY_ID = "DELETE FROM QpbmtContributionByGrade a WHERE a.contributionByGradePk.historyId IN :historyId";
	private static final String GET_CONTRIBUTION_RATE_BY_HISTORY_ID = "SELECT a from QpbmtContributionRate a WHERE a.contributionRatePk.historyId =:historyId";

	private ContributionRate toDomain(QpbmtContributionRate contributionRate,
									  List<QpbmtContributionByGrade> contributionByGrade) {
		return new ContributionRate(contributionRate.contributionRatePk.historyId,contributionRate.childCareContributionRatio,
				contributionRate.autoCalculationCls,
				contributionByGrade.stream()
						.map(x -> new ContributionByGrade(x.contributionByGradePk.welfarePensionGrade,
								x.childCareContribution))
						.collect(Collectors.toList()));
	}

	@Override
	public Optional<ContributionRateHistory> getContributionRateHistoryByOfficeCode(String officeCode) {
		return this.getContributionRateHistory(this.getContributionRateByOfficeCode(officeCode));
	}

	public List<QpbmtContributionRate> getContributionRateByOfficeCode (String officeCode) {
		return this.queryProxy().query(GET_CONTRIBUTION_HISTORY_BY_OFFICE_CODE, QpbmtContributionRate.class).setParameter("cid", AppContexts.user().companyId()).setParameter("officeCode", officeCode).getList();
	}

	private Optional<ContributionRateHistory> getContributionRateHistory (List<QpbmtContributionRate> entities){
		if (entities.isEmpty()) return Optional.empty();
		return Optional.of(new ContributionRateHistory(entities.get(0).contributionRatePk.cid, entities.get(0).contributionRatePk.socialInsuranceOfficeCd,
				entities.stream().map(item -> new YearMonthHistoryItem(item.contributionRatePk.historyId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
	}

	@Override
	public Optional<ContributionRate> getContributionRateByHistoryId(String historyId) {
		Optional<QpbmtContributionRate> contributionRate = this.queryProxy()
				.query(GET_CONTRIBUTION_RATE_BY_HISTORY_ID, QpbmtContributionRate.class)
				.setParameter("historyId", historyId).getSingle();
		List<QpbmtContributionByGrade> qpbmtContributionByGrade = this.queryProxy()
				.query(GET_CONTRIBUTION_BY_GRADE_BY_HISTORY_ID, QpbmtContributionByGrade.class)
				.setParameter("historyId", historyId).getList();
		return contributionRate.map(item -> toDomain(item, qpbmtContributionByGrade));
	}

	@Override
	public void deleteByHistoryIds(List<String> historyIds, String officeCode) {
		List<QpbmtContributionRatePk> en = new ArrayList<>();
		for (int i = 0; i < historyIds.size(); i++) {
			QpbmtContributionRatePk entity = new QpbmtContributionRatePk(AppContexts.user().companyId(),officeCode,historyIds.get(i));
			en.add(entity);
		}
		this.commandProxy().removeAll(QpbmtContributionRate.class, en);
	}

	@Override
	public void add(ContributionRate domain, String officeCode, YearMonthHistoryItem yearMonth) {
		this.commandProxy().insert(QpbmtContributionRate.toEntity(domain, officeCode, yearMonth));
		this.commandProxy().insertAll(QpbmtContributionByGrade.toEntity(domain, officeCode, yearMonth));

	}

	@Override
	public void update(ContributionRate domain, String officeCode, YearMonthHistoryItem yearMonth) {
		this.commandProxy().update(QpbmtContributionRate.toEntity(domain, officeCode, yearMonth));
		this.deleteContributionByGradeByHistoryId(Arrays.asList(domain.getHistoryId()));
		this.commandProxy().insertAll(QpbmtContributionByGrade.toEntity(domain, officeCode, yearMonth));
	}

	@Override
	public void deleteContributionByGradeByHistoryId(List<String> historyIds) {
		if (historyIds.isEmpty()) return;
		this.getEntityManager().createQuery(DELETE_CONTRIBUTION_BY_GRADE_BY_HISTORY_ID, QpbmtContributionByGrade.class)
				.setParameter("historyId", historyIds).executeUpdate();
	}

	@Override
	public void updateHistoryItem(String officeCode, YearMonthHistoryItem yearMonth) {
		this.updateContributionHistory(officeCode, yearMonth);
		this.updateContributionByGradeHistory(officeCode, yearMonth);
	}

	private void updateContributionHistory (String officeCode, YearMonthHistoryItem history) {
		Optional<QpbmtContributionRate> opt_entity = this.queryProxy().find(new QpbmtContributionRatePk(AppContexts.user().companyId(), officeCode, history.identifier()), QpbmtContributionRate.class);
		if (!opt_entity.isPresent()) return;
		QpbmtContributionRate entity = opt_entity.get();
		entity.startYearMonth = history.start().v();
		entity.endYearMonth = history.end().v();
		this.commandProxy().update(entity);
	}

	private void updateContributionByGradeHistory (String officeCode, YearMonthHistoryItem history) {
		List<QpbmtContributionByGrade> entities = this.queryProxy().query(GET_CONTRIBUTION_BY_GRADE_BY_HISTORY_ID, QpbmtContributionByGrade.class).setParameter("historyId", history.identifier()).getList();
		for(QpbmtContributionByGrade entity: entities){
			entity.startYearMonth = history.start().v();
			entity.endYearMonth = history.end().v();
		}
		this.commandProxy().updateAll(entities);
	}

	@Override
	public void insertContributionByGrade(ContributionRate domain) {
		Optional<QpbmtContributionRate> opt_entity = this.queryProxy().query(GET_CONTRIBUTION_RATE_BY_HISTORY_ID,QpbmtContributionRate.class)
				.setParameter("historyId", domain.getHistoryId())
				.getSingle();
		if (!opt_entity.isPresent()) return;
		QpbmtContributionRate entity = opt_entity.get();
		YearMonthHistoryItem yearMonth = new YearMonthHistoryItem(entity.contributionRatePk.historyId, new YearMonthPeriod(new YearMonth(entity.startYearMonth), new YearMonth(entity.endYearMonth)));
		this.commandProxy().insertAll(QpbmtContributionByGrade.toEntity(domain, entity.contributionRatePk.socialInsuranceOfficeCd, yearMonth));
	}


	@Override
	public void updateContributionByGrade(ContributionRate domain) {
		Optional<QpbmtContributionRate> opt_entity = this.queryProxy().query(GET_CONTRIBUTION_RATE_BY_HISTORY_ID,QpbmtContributionRate.class)
				.setParameter("historyId", domain.getHistoryId())
				.getSingle();
		if (!opt_entity.isPresent()) return;
		QpbmtContributionRate entity = opt_entity.get();
		YearMonthHistoryItem yearMonth = new YearMonthHistoryItem(entity.contributionRatePk.historyId, new YearMonthPeriod(new YearMonth(entity.startYearMonth), new YearMonth(entity.endYearMonth)));
		this.commandProxy().updateAll(QpbmtContributionByGrade.toEntity(domain, entity.contributionRatePk.socialInsuranceOfficeCd, yearMonth));
	}
}
