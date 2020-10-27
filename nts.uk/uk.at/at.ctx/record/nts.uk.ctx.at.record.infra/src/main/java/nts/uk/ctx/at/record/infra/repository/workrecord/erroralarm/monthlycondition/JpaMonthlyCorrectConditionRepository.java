/**
 * 3:55:28 PM Mar 29, 2018
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.monthlycondition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.MonthlyCorrectConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.MonthlyCorrectExtractCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.TimeItemCheckMonthly;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycondition.KrcmtEralMonSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycondition.KrcmtEralMonSetPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycondition.KrcmtMonCorrectCndAtd;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaMonthlyCorrectConditionRepository extends JpaRepository implements MonthlyCorrectConditionRepository {

	private static final String SELLECT_MONTHLY_CONDITION_BY_COMPANY = "SELECT m FROM KrcmtEralMonSet m WHERE m.krcmtEralMonSetPK.companyId = :companyId";
	private static final String SELECT_CHECKID = "SELECT c FROM KrcmtMonCorrectCndAtd c WHERE c.eralCheckId = :eralCheckId ";

	@Override
	public Optional<MonthlyCorrectExtractCondition> findMonthlyConditionByCode(String errCode) {
		String companyId = AppContexts.user().companyId();
		Optional<KrcmtEralMonSet> entity = this.queryProxy()
				.find(new KrcmtEralMonSetPK(companyId, errCode), KrcmtEralMonSet.class);
		if (entity.isPresent()) {
			return Optional.of(KrcmtEralMonSet.toDomain(entity.get()));
		} else {
			return Optional.ofNullable(null);
		}
	}

	@Override
	public List<MonthlyCorrectExtractCondition> findAllMonthlyConditionByCompanyId() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().query(SELLECT_MONTHLY_CONDITION_BY_COMPANY, KrcmtEralMonSet.class)
				.setParameter("companyId", companyId).getList().stream()
				.map(entity -> KrcmtEralMonSet.toDomain(entity)).collect(Collectors.toList());
	}

	@Override
	public Optional<TimeItemCheckMonthly> findTimeItemCheckMonthlyById(String checkId, String errorAlarmCode) {
		return Optional.ofNullable(
				KrcmtMonCorrectCndAtd.toDomain(this.queryProxy().find(checkId, KrcmtMonCorrectCndAtd.class).get(),
						AppContexts.user().companyId(), errorAlarmCode));
	}

	@Override
	public MonthlyCorrectExtractCondition updateMonthlyCorrectExtractCondition(MonthlyCorrectExtractCondition domain) {
		KrcmtEralMonSet targetEntity = this.queryProxy()
				.find(new KrcmtEralMonSetPK(domain.getCompanyId(), domain.getCode().v()),
						KrcmtEralMonSet.class)
				.get();
		domain.setCheckId(targetEntity.eralCheckId);
		KrcmtEralMonSet newEntity = KrcmtEralMonSet.fromDomain(domain);
		targetEntity.errorAlarmName = newEntity.errorAlarmName;
		targetEntity.useAtr = newEntity.useAtr;
		this.commandProxy().update(targetEntity);
		return KrcmtEralMonSet.toDomain(targetEntity);
	}

	@Override
	public void updateTimeItemCheckMonthly(TimeItemCheckMonthly domain) {
		this.commandProxy().insert(KrcmtMonCorrectCndAtd.fromDomain(domain));
	}

	@Override
	public void deleteMonthlyCorrectExtractCondition(String errorCd) {
		this.commandProxy().remove(KrcmtEralMonSet.class,
				new KrcmtEralMonSetPK(AppContexts.user().companyId(), errorCd));
	}

	@Override
	public MonthlyCorrectExtractCondition createMonthlyCorrectExtractCondition(MonthlyCorrectExtractCondition domain) {
		this.commandProxy().insert(KrcmtEralMonSet.fromDomain(domain));
		return domain;
	}

	@Override
	public void createTimeItemCheckMonthly(TimeItemCheckMonthly domain) {
		this.commandProxy().insert(KrcmtMonCorrectCndAtd.fromDomain(domain));
	}

	@Override
	public void removeTimeItemCheckMonthly(String errorAlarmCheckID) {
		KrcmtMonCorrectCndAtd targetEntity = this.queryProxy().query(SELECT_CHECKID, KrcmtMonCorrectCndAtd.class)
				.setParameter("eralCheckId", errorAlarmCheckID).getSingleOrNull();
		if (targetEntity != null) {
			this.commandProxy().remove(KrcmtMonCorrectCndAtd.class, targetEntity.eralCheckId);
			this.getEntityManager().flush();
		}
	}

	@Override
	public List<MonthlyCorrectExtractCondition> findUseMonthlyConditionByCompanyId() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().query(SELLECT_MONTHLY_CONDITION_BY_COMPANY + " AND m.useAtr = 1", KrcmtEralMonSet.class)
				.setParameter("companyId", companyId).getList().stream()
				.map(entity -> KrcmtEralMonSet.toDomain(entity)).collect(Collectors.toList());
	}

}
