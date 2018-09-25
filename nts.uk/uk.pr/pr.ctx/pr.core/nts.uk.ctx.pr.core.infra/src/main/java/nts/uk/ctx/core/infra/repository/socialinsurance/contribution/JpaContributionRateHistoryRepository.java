package nts.uk.ctx.core.infra.repository.socialinsurance.contribution;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateHistoryRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.contribution.QpbmtContributionRateHistory;
import nts.uk.ctx.core.infra.entity.socialinsurance.contribution.QpbmtContributionRateHistoryPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 拠出金率履歴
 */
@Stateless
public class JpaContributionRateHistoryRepository extends JpaRepository implements ContributionRateHistoryRepository {

	private static final String FIND_BY_OFFICE_CODE = "select a from QpbmtContributionRateHistory a where a.contributionHistPk.cid = :cid AND a.contributionHistPk.socialInsuranceOfficeCd = :officeCode ORDER BY a.startYearMonth DESC";
	private static final String DELETE = "DELETE FROM QpbmtContributionRateHistory a WHERE a.contributionHistPk.cid = :cid AND a.contributionHistPk.socialInsuranceOfficeCd = :officeCode";

	/**
	 * Entity to domain
	 *
	 * @param entity
	 *            QpbmtContributionRateHistory
	 * @return ContributionRateHistory
	 */
	private Optional<ContributionRateHistory> toDomain(List<QpbmtContributionRateHistory> entity) {
		if (entity.size() > 0) {
			String companyId = entity.get(0).contributionHistPk.cid;
			String socialInsuranceCode = entity.get(0).contributionHistPk.socialInsuranceOfficeCd;
			List<YearMonthHistoryItem> history = entity.stream()
					.map(item -> new YearMonthHistoryItem(item.contributionHistPk.historyId,
							new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth))))
					.collect(Collectors.toList());
			return Optional.of(new ContributionRateHistory(companyId, socialInsuranceCode, history));
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Domain to entity
	 *
	 * @param socialInsuranceOfficeCd
	 *            社会保険事業所コード
	 * @param historyId
	 *            履歴ID
	 * @param startYearMonth
	 *            年月開始
	 * @param endYearMonth
	 *            年月終了
	 * @return QpbmtContributionRateHistory
	 */
	private QpbmtContributionRateHistory toEntity(String socialInsuranceOfficeCd, String historyId, int startYearMonth,
			int endYearMonth) {
		return new QpbmtContributionRateHistory(
				new QpbmtContributionRateHistoryPk(AppContexts.user().companyId(), socialInsuranceOfficeCd, historyId),
				startYearMonth, endYearMonth);
	}

	@Override
	public Optional<ContributionRateHistory> findByCodeAndCid(String cid, String officeCode) {
		return this.toDomain(this.queryProxy().query(FIND_BY_OFFICE_CODE, QpbmtContributionRateHistory.class)
				.setParameter("cid", AppContexts.user().companyId()).setParameter("officeCode", officeCode).getList());
	}

	@Override
	public void deleteByCidAndCode(String cid, String officeCode) {
		this.getEntityManager().createQuery(DELETE, QpbmtContributionRateHistory.class).setParameter("cid", cid)
				.setParameter("officeCode", officeCode).executeUpdate();
	}

	@Override
	public Optional<ContributionRateHistory> getContributionRateHistoryByOfficeCode(String officeCode) {
		return this.toDomain(this.queryProxy().query(FIND_BY_OFFICE_CODE, QpbmtContributionRateHistory.class)
				.setParameter("cid", AppContexts.user().companyId()).setParameter("officeCode", officeCode).getList());
	}

	@Override
	public void add(ContributionRateHistory domain) {
		domain.getHistory().forEach(item -> {
			this.commandProxy().insert(this.toEntity(domain.getSocialInsuranceCode().v(), item.identifier(),
					item.start().v(), item.end().v()));
		});

	}

	@Override
	public void update(ContributionRateHistory domain) {
		domain.getHistory().forEach(item -> {
			this.commandProxy().update(this.toEntity(domain.getSocialInsuranceCode().v(), item.identifier(),
					item.start().v(), item.end().v()));
		});
	}

	@Override
	public void remove(ContributionRateHistory domain) {
		this.deleteByCidAndCode(AppContexts.user().companyId(), domain.getSocialInsuranceCode().v());
		domain.getHistory().forEach(item -> {
			this.commandProxy().insert(this.toEntity(domain.getSocialInsuranceCode().v(), item.identifier(),
					item.start().v(), item.end().v()));
		});

	}

}
