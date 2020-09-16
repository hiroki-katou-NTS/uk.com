package nts.uk.ctx.at.function.infra.repository.alarm.mastercheck;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.ErrorAlarmMessage;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedExtractCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedExtractConditionRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.mastercheck.KrcmtMasterCheckFixedExtractCondition;

@Stateless
public class JpaMasterCheckFixedExtractConditionRepository extends JpaRepository
	implements MasterCheckFixedExtractConditionRepository {

	@Override
	public List<MasterCheckFixedExtractCondition> findAll(List<String> extractConditionIds, boolean useAtr) {
		String query = "SELECT a FROM KrcmtMasterCheckFixedExtractCondition a WHERE a.useAtr = :useAtr AND a.pk.erAlId IN :ids";
		List<KrcmtMasterCheckFixedExtractCondition> results = new ArrayList<>();
		
		CollectionUtil.split(extractConditionIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			results.addAll(this.queryProxy().query(query, KrcmtMasterCheckFixedExtractCondition.class)
					.setParameter("useAtr", useAtr).setParameter("ids", subList).getList());
		});
		
		return results.stream().map(a -> new MasterCheckFixedExtractCondition(
					a.getPk().getErAlId(), a.getPk().getNo(), new ErrorAlarmMessage(a.getMessage()), 
					a.getUseAtr() == 0 ? false : true))
				.collect(Collectors.toList());
	}

}
