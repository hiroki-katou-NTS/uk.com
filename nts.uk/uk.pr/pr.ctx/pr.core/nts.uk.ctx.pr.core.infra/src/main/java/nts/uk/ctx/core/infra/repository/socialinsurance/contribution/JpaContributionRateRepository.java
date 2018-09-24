package nts.uk.ctx.core.infra.repository.socialinsurance.contribution;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionByGrade;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRate;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.contribution.QpbmtContributionByGrade;
import nts.uk.ctx.core.infra.entity.socialinsurance.contribution.QpbmtContributionRate;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsurancePerGradeFee;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtGradeWelfarePensionInsurancePremium;

@Stateless
public class JpaContributionRateRepository extends JpaRepository implements ContributionRateRepository {

	private static final String GET_CONTRIBUTION_BY_GRADE_BY_HISTORY_ID = "SELECT a from QpbmtContributionByGrade a WHERE a.contributionByGradePk.historyId =:historyId";
	private static final String DELETE = "DELETE FROM QpbmtContributionByGrade a WHERE a.contributionHistPk.cid = :cid AND a.contributionByGradePk.socialInsuranceOfficeCd = :officeCode";
	private static final String DELETE_CONTRIBUTION_BY_GRADE_BY_HISTORY_ID = "DELETE FROM QpbmtContributionByGrade a WHERE a.contributionByGradePk.historyId IN :historyId";

	private ContributionRate toDomain(QpbmtContributionRate contributionRate,
			List<QpbmtContributionByGrade> contributionByGrade) {
		return new ContributionRate(contributionRate.historyId, contributionRate.childCareContributionRatio,
				contributionRate.autoCalculationCls,
				contributionByGrade.stream()
						.map(x -> new ContributionByGrade(x.contributionByGradePk.welfarePensionGrade,
								x.childCareContribution))
						.collect(Collectors.toList()));
	}

	@Override
	public Optional<ContributionRate> getContributionRateByHistoryId(String historyId) {
		Optional<QpbmtContributionRate> contributionRate = this.queryProxy().find(historyId,
				QpbmtContributionRate.class);
		List<QpbmtContributionByGrade> qpbmtContributionByGrade = this.queryProxy()
				.query(GET_CONTRIBUTION_BY_GRADE_BY_HISTORY_ID, QpbmtContributionByGrade.class)
				.setParameter("historyId", historyId).getList();
		return contributionRate.map(item -> toDomain(item, qpbmtContributionByGrade));
	}

	@Override
	public void deleteByHistoryIds(List<String> historyIds) {
		this.commandProxy().removeAll(QpbmtContributionRate.class, historyIds);
	}

	@Override
	public void add(ContributionRate domain) {
		this.commandProxy().insert(QpbmtContributionRate.toEntity(domain));
		this.commandProxy().insertAll(QpbmtContributionByGrade.toEntity(domain));

	}

	@Override
	public void update(ContributionRate domain) {
		this.commandProxy().update(QpbmtContributionRate.toEntity(domain));
		this.deleteContributionByGradeByHistoryId(Arrays.asList(domain.getHistoryId()));
		this.commandProxy().insertAll(QpbmtContributionByGrade.toEntity(domain));
	}

	@Override
	public void addContributionByGrade(ContributionRate domain) {
		this.commandProxy().insertAll(QpbmtContributionByGrade.toEntity(domain));
	}

	@Override
	public void updateContributionByGrade(ContributionRate domain) {
		this.commandProxy().updateAll(QpbmtContributionByGrade.toEntity(domain));
	}

	@Override
	public void deleteContributionByGradeByHistoryId(List<String> historyIds) {
		this.getEntityManager().createQuery(DELETE_CONTRIBUTION_BY_GRADE_BY_HISTORY_ID, QpbmtContributionByGrade.class)
				.setParameter("historyId", historyIds).executeUpdate();
	}

	@Override
	public void insertContributionByGrade(ContributionRate domain) {
		this.commandProxy().insertAll(QpbmtContributionByGrade.toEntity(domain));
	}

}
