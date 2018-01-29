package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcmtErAlCondition;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author HieuNV
 *
 */
@Stateless
public class JpaErrorAlarmConditionRepository extends JpaRepository implements ErrorAlarmConditionRepository {

	private final String FIND_BY_ERROR_ALARM_CHECK_ID = "SELECT a FROM KrcmtErAlCondition a WHERE a.eralCheckId = :eralCheckId ";
	private final String DELETE_BY_ERROR_ALARM_CHECK_IDS = "DELETE FROM KrcmtErAlCondition a WHERE a.eralCheckId IN :erAlCheckIds ";
	private final String FIND_BY_ERROR_ALARM_CHECK_IDS = "SELECT a  FROM KrcmtErAlCondition a WHERE a.eralCheckId IN :erAlCheckIds ";
	
	@Override
	public void addErrorAlarmCondition(ErrorAlarmCondition conditionDomain) {
		this.commandProxy().insert(KrcmtErAlCondition.fromDomain(conditionDomain));
	}

	@Override
	public void updateErrorAlarmCondition(ErrorAlarmCondition conditionDomain) {
		
		Optional<KrcmtErAlCondition> optTargetEntity = this.findByErrorAlamCheckId(conditionDomain.getErrorAlarmCheckID());
		/*if (!domain.getFixedAtr()) {
			conditionDomain.setGroupId1(targetEntity.krcmtErAlCondition.atdItemConditionGroup1);
			conditionDomain.setGroupId2(targetEntity.krcmtErAlCondition.atdItemConditionGroup2);
		}*/
		if (!optTargetEntity.isPresent()) {
			return;
		}
		KrcmtErAlCondition targetEntity = optTargetEntity.get();
		KrcmtErAlCondition domainAfterConvert = KrcmtErAlCondition.fromDomain(conditionDomain);
		//TODO: setting data need to update. Will be check to assign needed field
		targetEntity.atdItemConditionGroup1 = domainAfterConvert.atdItemConditionGroup1;
		targetEntity.atdItemConditionGroup2 = domainAfterConvert.atdItemConditionGroup2;
		targetEntity.filterByBusinessType = domainAfterConvert.filterByBusinessType;
		targetEntity.filterByClassification = domainAfterConvert.filterByClassification;
		targetEntity.filterByEmployment  = domainAfterConvert.filterByEmployment;
		targetEntity.filterByJobTitle = domainAfterConvert.filterByJobTitle;
		targetEntity.group2UseAtr = domainAfterConvert.group2UseAtr;
		targetEntity.krcstErAlConGroup1 = domainAfterConvert.krcstErAlConGroup1;
		targetEntity.krcstErAlConGroup2 = domainAfterConvert.krcstErAlConGroup1;
		targetEntity.lstBusinessType = domainAfterConvert.lstBusinessType;
		targetEntity.lstClassification = domainAfterConvert.lstClassification;
		targetEntity.lstEmployment = domainAfterConvert.lstEmployment;
		targetEntity.lstJobTitle = domainAfterConvert.lstJobTitle;
		targetEntity.lstWhActual = domainAfterConvert.lstWhActual; 
		targetEntity.lstWhPlan = domainAfterConvert.lstWhPlan;
		targetEntity.lstWtActual = domainAfterConvert.lstWtActual;
		targetEntity.lstWtPlan = domainAfterConvert.lstWtPlan;
		targetEntity.messageDisplay = domainAfterConvert.messageDisplay;
		targetEntity.operatorBetweenGroups = domainAfterConvert.operatorBetweenGroups;
		targetEntity.whActualFilterAtr = domainAfterConvert.whActualFilterAtr;
		targetEntity.whCompareAtr = domainAfterConvert.whCompareAtr;
		targetEntity.whPlanActualOperator = domainAfterConvert.whPlanActualOperator;
		targetEntity.whPlanFilterAtr = domainAfterConvert.whPlanFilterAtr;
		targetEntity.workingHoursUseAtr = domainAfterConvert.workingHoursUseAtr;
		targetEntity.workTypeUseAtr = domainAfterConvert.workTypeUseAtr;
		targetEntity.wtActualFilterAtr = domainAfterConvert.wtActualFilterAtr;
		targetEntity.wtCompareAtr = domainAfterConvert.wtCompareAtr;
		targetEntity.wtPlanActualOperator = domainAfterConvert.wtPlanActualOperator;
		targetEntity.wtPlanFilterAtr = domainAfterConvert.wtPlanFilterAtr;

		this.commandProxy().update(targetEntity);
	}

	@Override
	public void removeErrorAlarmCondition(List<String> listErAlCheckID) {
		this.getEntityManager().createQuery(DELETE_BY_ERROR_ALARM_CHECK_IDS)
		.setParameter("erAlCheckIds", listErAlCheckID)
		.executeUpdate();
	}

	@Override
    public Optional<ErrorAlarmCondition> findConditionByErrorAlamCheckId(String eralCheckId) {
		String companyId = AppContexts.user().companyId();
		String errorAlarmCode = "";
        Optional<KrcmtErAlCondition> entity = findByErrorAlamCheckId(eralCheckId);
        return Optional.ofNullable(entity.isPresent() ? KrcmtErAlCondition.toDomain(entity.get(), companyId, errorAlarmCode) : null);
    }

	private Optional<KrcmtErAlCondition> findByErrorAlamCheckId(String eralCheckId) {
        return this.queryProxy().query(FIND_BY_ERROR_ALARM_CHECK_ID, KrcmtErAlCondition.class)
                .setParameter("eralCheckId", eralCheckId).getSingle();
    }
	@Override
	public List<ErrorAlarmCondition> findConditionByListErrorAlamCheckId(List<String> listEralCheckId) {
		return this.queryProxy().query(FIND_BY_ERROR_ALARM_CHECK_IDS, KrcmtErAlCondition.class)
                .setParameter("erAlCheckIds", listEralCheckId).getList().stream().map(item -> 
                KrcmtErAlCondition.toDomain(item, "", "")).collect(Collectors.toList());
	}

	@Override
	public List<ErrorAlarmCondition> findMessageConByListErAlCheckId(List<String> listEralCheckId) {
		return this.queryProxy().query(FIND_BY_ERROR_ALARM_CHECK_IDS, KrcmtErAlCondition.class)
                .setParameter("erAlCheckIds", listEralCheckId).getList().stream().map(item -> 
                	new ErrorAlarmCondition(item.eralCheckId, item.messageDisplay)).collect(Collectors.toList());
	}
}
