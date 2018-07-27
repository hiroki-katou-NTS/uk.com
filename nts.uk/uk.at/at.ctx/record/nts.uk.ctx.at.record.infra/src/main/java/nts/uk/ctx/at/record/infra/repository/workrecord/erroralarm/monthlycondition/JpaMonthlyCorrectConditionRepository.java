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
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycondition.KrcmtMonthlyCorrectCon;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycondition.KrcmtMonthlyCorrectConPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycondition.KrcmtTimeChkMonthly;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaMonthlyCorrectConditionRepository extends JpaRepository implements MonthlyCorrectConditionRepository {

	private static final String SELLECT_MONTHLY_CONDITION_BY_COMPANY = "SELECT m FROM KrcmtMonthlyCorrectCon m WHERE m.krcmtMonthlyCorrectConPK.companyId = :companyId";
	private static final String SELECT_CHECKID = "SELECT c FROM KrcmtTimeChkMonthly c WHERE c.eralCheckId = :eralCheckId ";

	@Override
	public Optional<MonthlyCorrectExtractCondition> findMonthlyConditionByCode(String errCode) {
		String companyId = AppContexts.user().companyId();
		Optional<KrcmtMonthlyCorrectCon> entity = this.queryProxy()
				.find(new KrcmtMonthlyCorrectConPK(companyId, errCode), KrcmtMonthlyCorrectCon.class);
		if (entity.isPresent()) {
			return Optional.of(KrcmtMonthlyCorrectCon.toDomain(entity.get()));
		} else {
			return Optional.ofNullable(null);
		}
	}

	@Override
	public List<MonthlyCorrectExtractCondition> findAllMonthlyConditionByCompanyId() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().query(SELLECT_MONTHLY_CONDITION_BY_COMPANY, KrcmtMonthlyCorrectCon.class)
				.setParameter("companyId", companyId).getList().stream()
				.map(entity -> KrcmtMonthlyCorrectCon.toDomain(entity)).collect(Collectors.toList());
	}

	@Override
	public Optional<TimeItemCheckMonthly> findTimeItemCheckMonthlyById(String checkId, String errorAlarmCode) {
		return Optional.ofNullable(
				KrcmtTimeChkMonthly.toDomain(this.queryProxy().find(checkId, KrcmtTimeChkMonthly.class).get(),
						AppContexts.user().companyId(), errorAlarmCode));
	}

	@Override
	public MonthlyCorrectExtractCondition updateMonthlyCorrectExtractCondition(MonthlyCorrectExtractCondition domain) {
		KrcmtMonthlyCorrectCon targetEntity = this.queryProxy()
				.find(new KrcmtMonthlyCorrectConPK(domain.getCompanyId(), domain.getCode().v()),
						KrcmtMonthlyCorrectCon.class)
				.get();
		domain.setCheckId(targetEntity.eralCheckId);
		KrcmtMonthlyCorrectCon newEntity = KrcmtMonthlyCorrectCon.fromDomain(domain);
		targetEntity.errorAlarmName = newEntity.errorAlarmName;
		targetEntity.useAtr = newEntity.useAtr;
		this.commandProxy().update(targetEntity);
		return KrcmtMonthlyCorrectCon.toDomain(targetEntity);
	}

	@Override
	public void updateTimeItemCheckMonthly(TimeItemCheckMonthly domain) {
		this.commandProxy().insert(KrcmtTimeChkMonthly.fromDomain(domain));
	}

	@Override
	public void deleteMonthlyCorrectExtractCondition(String errorCd) {
		this.commandProxy().remove(KrcmtMonthlyCorrectCon.class,
				new KrcmtMonthlyCorrectConPK(AppContexts.user().companyId(), errorCd));
	}

	@Override
	public MonthlyCorrectExtractCondition createMonthlyCorrectExtractCondition(MonthlyCorrectExtractCondition domain) {
		this.commandProxy().insert(KrcmtMonthlyCorrectCon.fromDomain(domain));
		return domain;
	}

	@Override
	public void createTimeItemCheckMonthly(TimeItemCheckMonthly domain) {
		this.commandProxy().insert(KrcmtTimeChkMonthly.fromDomain(domain));
	}

	@Override
	public void removeTimeItemCheckMonthly(String errorAlarmCheckID) {
		KrcmtTimeChkMonthly targetEntity = this.queryProxy().query(SELECT_CHECKID, KrcmtTimeChkMonthly.class)
				.setParameter("eralCheckId", errorAlarmCheckID).getSingleOrNull();
		if (targetEntity != null) {
			this.commandProxy().remove(KrcmtTimeChkMonthly.class, targetEntity.eralCheckId);
			this.getEntityManager().flush();
		}
	}

	@Override
	public List<MonthlyCorrectExtractCondition> findUseMonthlyConditionByCompanyId() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().query(SELLECT_MONTHLY_CONDITION_BY_COMPANY + " AND m.useAtr = 1", KrcmtMonthlyCorrectCon.class)
				.setParameter("companyId", companyId).getList().stream()
				.map(entity -> KrcmtMonthlyCorrectCon.toDomain(entity)).collect(Collectors.toList());
	}

}
