package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.multimonth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonAlarmCheckCondRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthAlarmCheckCond;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth.KrcmtMulMonAlarmCheck;

@Stateless
public class JpaMulMonAlarmCheckCondRepository extends JpaRepository implements MulMonAlarmCheckCondRepository {
	
	private static final String SELECT_BY_LIST_ID = "SELECT c FROM KrcmtMulMonAlarmCheck c "
			+ " WHERE c.errorAlarmCheckID IN :listErrorAlarmCheckID ORDER BY insDate ASC ";
	private static final String SELECT_BY_CODE  = "SELECT c FROM KrcmtMulMonAlarmCheck c "
			+ " WHERE c.errorAlarmCheckID =:errorAlarmCheckID" ;
	
	@Override
	public List<MulMonthAlarmCheckCond> getMulMonAlarmsByListID(List<String> listErrorAlarmCheckID) {
		List<MulMonthAlarmCheckCond> data = new ArrayList<>();
		CollectionUtil.split(listErrorAlarmCheckID, 1000, subIdList ->{
			data.addAll(this.queryProxy().query(SELECT_BY_LIST_ID,KrcmtMulMonAlarmCheck.class)
					.setParameter("listErrorAlarmCheckID", subIdList).getList(c->c.toDomain())
					);
		});
		return data;
	}
	
	@Override
	public Optional<MulMonthAlarmCheckCond> getMulMonAlarmByID(String errorAlarmCheckID) {
		Optional<MulMonthAlarmCheckCond> data = this.queryProxy().query(SELECT_BY_CODE, KrcmtMulMonAlarmCheck.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle(c->c.toDomain());
		return data;
	}
	
	@Override
	public void addMulMonAlarm(MulMonthAlarmCheckCond mulMonthAlarmCheckCond) {
		this.commandProxy().insert(KrcmtMulMonAlarmCheck.toEntity(mulMonthAlarmCheckCond));
		this.getEntityManager().flush();
	}
	
	@Override
	public void updateMulMonAlarm(MulMonthAlarmCheckCond mulMonthAlarmCheckCond) {
		KrcmtMulMonAlarmCheck updateEntity = this.queryProxy().find(mulMonthAlarmCheckCond.getErrorAlarmCheckID(), KrcmtMulMonAlarmCheck.class).get();
		KrcmtMulMonAlarmCheck newEntity = KrcmtMulMonAlarmCheck.toEntity(mulMonthAlarmCheckCond);
		updateEntity.nameAlarmCon = newEntity.nameAlarmCon;
		updateEntity.typeCheckItem = newEntity.typeCheckItem;
		updateEntity.messageBold = newEntity.messageBold;
		updateEntity.messageColor = newEntity.messageColor;
		updateEntity.messageDisplay = newEntity.messageDisplay;
		this.commandProxy().update(updateEntity);
		this.getEntityManager().flush();
	}
	
	@Override
	public void deleteMulMonAlarm(String errorAlarmCheckID) {
		this.commandProxy().remove(KrcmtMulMonAlarmCheck.class,errorAlarmCheckID);
		this.getEntityManager().flush();
	}

}
