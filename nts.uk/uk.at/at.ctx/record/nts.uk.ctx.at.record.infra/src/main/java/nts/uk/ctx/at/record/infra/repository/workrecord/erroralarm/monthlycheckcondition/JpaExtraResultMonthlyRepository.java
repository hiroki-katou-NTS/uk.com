package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.monthlycheckcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition.KrcmtAlstChkmonUdthly;

@Stateless
public class JpaExtraResultMonthlyRepository extends JpaRepository implements ExtraResultMonthlyRepository {
	
	private static final String SELECT_BY_LIST_ID = "SELECT c FROM KrcmtAlstChkmonUdthly c "
			+ " WHERE c.errorAlarmCheckID IN :listErrorAlarmCheckID";
	private static final String SELECT_BY_CODE  = "SELECT c FROM KrcmtAlstChkmonUdthly c "
			+ " WHERE c.errorAlarmCheckID =:errorAlarmCheckID" ;
	
	@Override
	public List<ExtraResultMonthly> getExtraResultMonthlyByListID(List<String> listErrorAlarmCheckID) {
		List<ExtraResultMonthly> data = new ArrayList<>();
		CollectionUtil.split(listErrorAlarmCheckID, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT,
				subIdList -> {
					data.addAll(this.queryProxy()
							.query(SELECT_BY_LIST_ID, KrcmtAlstChkmonUdthly.class)
							.setParameter("listErrorAlarmCheckID", listErrorAlarmCheckID)
							.getList(c -> c.toDomain()));
				});
		return data;
	}

	@Override
	public Optional<ExtraResultMonthly> getExtraResultMonthlyByID(String errorAlarmCheckID) {
		Optional<ExtraResultMonthly> data = this.queryProxy().query(SELECT_BY_CODE,KrcmtAlstChkmonUdthly.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public void addExtraResultMonthly(ExtraResultMonthly extraResultMonthly) {
		this.commandProxy().insert(KrcmtAlstChkmonUdthly.toEntity(extraResultMonthly));
		this.getEntityManager().flush();
		
	}

	@Override
	public void updateExtraResultMonthly(ExtraResultMonthly extraResultMonthly) {
		KrcmtAlstChkmonUdthly updateEntity = this.queryProxy().find(extraResultMonthly.getErrorAlarmCheckID(), KrcmtAlstChkmonUdthly.class).get();
		KrcmtAlstChkmonUdthly newEntity = KrcmtAlstChkmonUdthly.toEntity(extraResultMonthly);
		updateEntity.sortBy = newEntity.sortBy;
		updateEntity.extraResultMonName = newEntity.extraResultMonName;
		updateEntity.useAtr = newEntity.useAtr;
		updateEntity.typeCheckItem = newEntity.typeCheckItem;
		updateEntity.messageBold = newEntity.messageBold;
		updateEntity.messageColor = newEntity.messageColor;
		updateEntity.messageDisplay = newEntity.messageDisplay;
		updateEntity.operatorBetweenGroups = newEntity.operatorBetweenGroups;
		updateEntity.group2UseAtr = newEntity.group2UseAtr;
		updateEntity.atdItemConditionGroup1 = newEntity.atdItemConditionGroup1;
		updateEntity.krcmtEralstCndexpiptchk1 = newEntity.krcmtEralstCndexpiptchk1;
		updateEntity.atdItemConditionGroup2 = newEntity.atdItemConditionGroup2;
		updateEntity.krcmtEralstCndexpiptchk2 = newEntity.krcmtEralstCndexpiptchk2;
		this.commandProxy().update(updateEntity);
		this.getEntityManager().flush();
	}

	@Override
	public void deleteExtraResultMonthly(String errorAlarmCheckID) {
		this.commandProxy().remove(KrcmtAlstChkmonUdthly.class,errorAlarmCheckID);
		this.getEntityManager().flush();
	}

}
