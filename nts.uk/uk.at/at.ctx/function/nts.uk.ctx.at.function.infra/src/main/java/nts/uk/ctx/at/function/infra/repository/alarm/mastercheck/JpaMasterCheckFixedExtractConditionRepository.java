package nts.uk.ctx.at.function.infra.repository.alarm.mastercheck;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.ErrorAlarmMessageMSTCHK;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedExtractCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedExtractConditionRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.mastercheck.KrcmtMasterCheckFixedExtractCondition;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.mastercheck.KrcmtMasterCheckFixedExtractConditionPK;

@Stateless
public class JpaMasterCheckFixedExtractConditionRepository extends JpaRepository
	implements MasterCheckFixedExtractConditionRepository {
	
	private static final String DELETE_MASTER_CHECK_FIXED_CON_BY_ID =  "DELETE FROM KrcmtMasterCheckFixedExtractCondition c "
			+ " WHERE c.pk.erAlId = :erAlId ";
	
	private static final String SELECT_MASTER_CHECK_FIXED_CON_BY_ID =  "SELECT c FROM KrcmtMasterCheckFixedExtractCondition c "
			+ " WHERE c.pk.erAlId = :erAlId ";

	@Override
	public List<MasterCheckFixedExtractCondition> findAll(List<String> extractConditionIds, boolean useAtr) {
		String query = "SELECT a FROM KrcmtMasterCheckFixedExtractCondition a WHERE a.useAtr = :useAtr AND a.pk.erAlId IN :ids";
		List<KrcmtMasterCheckFixedExtractCondition> results = new ArrayList<>();
		
		CollectionUtil.split(extractConditionIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			results.addAll(this.queryProxy().query(query, KrcmtMasterCheckFixedExtractCondition.class)
					.setParameter("useAtr", useAtr).setParameter("ids", subList).getList());
		});
		
		return results.stream().map(a -> new MasterCheckFixedExtractCondition(
					a.getPk().getErAlId(), a.getPk().getNo(), new ErrorAlarmMessageMSTCHK(a.getMessage()), 
					a.getUseAtr() == 0 ? false : true))
				.collect(Collectors.toList());
	}

	@Override
	public List<MasterCheckFixedExtractCondition> getAllFixedMasterCheckConById(String errorAlarmCheckId) {
		List<MasterCheckFixedExtractCondition> data = this.queryProxy().query(SELECT_MASTER_CHECK_FIXED_CON_BY_ID, KrcmtMasterCheckFixedExtractCondition.class)
				.setParameter("erAlId", errorAlarmCheckId)
				.getList(c->c.toDomain());
		return data;
	}

	@Override
	public void addMasterCheckFixedCondition(MasterCheckFixedExtractCondition masterCheckFixedCondition) {
		this.commandProxy().insert(KrcmtMasterCheckFixedExtractCondition.toEntity(masterCheckFixedCondition));
		this.getEntityManager().flush();
	}

	@Override
	public void updateMasterCheckFixedCondition(MasterCheckFixedExtractCondition masterCheckFixedCondition) {
		KrcmtMasterCheckFixedExtractCondition newEntity = KrcmtMasterCheckFixedExtractCondition.toEntity(masterCheckFixedCondition);
		KrcmtMasterCheckFixedExtractCondition updateEntity = this.queryProxy().find(
				new KrcmtMasterCheckFixedExtractConditionPK(
						newEntity.getPk().getErAlId(), 
						newEntity.getPk().getNo()),
				KrcmtMasterCheckFixedExtractCondition.class).get();
		updateEntity.setMessage(newEntity.getMessage());
		updateEntity.setUseAtr(newEntity.getUseAtr());
		this.commandProxy().update(updateEntity);
	}

	@Override
	public void deleteMasterCheckFixedCondition(String errorAlarmCheckId) {
		this.getEntityManager().createQuery(DELETE_MASTER_CHECK_FIXED_CON_BY_ID)
		.setParameter("erAlId", errorAlarmCheckId).executeUpdate();
	}
}
