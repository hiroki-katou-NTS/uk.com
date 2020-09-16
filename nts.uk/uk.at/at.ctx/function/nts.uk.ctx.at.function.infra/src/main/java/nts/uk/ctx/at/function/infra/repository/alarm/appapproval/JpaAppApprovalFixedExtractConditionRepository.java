package nts.uk.ctx.at.function.infra.repository.alarm.appapproval;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractConditionRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.ErrorAlarmMessage;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.appapproval.KrqmtAppApprovalFixedExtractCondition;

@Stateless
public class JpaAppApprovalFixedExtractConditionRepository extends JpaRepository 
	implements AppApprovalFixedExtractConditionRepository {

	@Override
	public List<AppApprovalFixedExtractCondition> findAll(List<String> extractConditionIds, boolean useAtr) {
		String query = "SELECT a FROM KrqmtAppApprovalFixedExtractCondition a WHERE a.useAtr = :useAtr AND a.pk.erAlId IN :ids";
		List<KrqmtAppApprovalFixedExtractCondition> results = new ArrayList<>();
		
		CollectionUtil.split(extractConditionIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			results.addAll(this.queryProxy().query(query, KrqmtAppApprovalFixedExtractCondition.class)
					.setParameter("useAtr", useAtr).setParameter("ids", subList).getList());
		});
		
		return results.stream().map(a -> new AppApprovalFixedExtractCondition(
					a.getPk().getErAlId(), a.getPk().getNo(), new ErrorAlarmMessage(a.getMessage()), 
					a.getUseAtr() == 0 ? false : true))
				.collect(Collectors.toList());
	}
	
}
