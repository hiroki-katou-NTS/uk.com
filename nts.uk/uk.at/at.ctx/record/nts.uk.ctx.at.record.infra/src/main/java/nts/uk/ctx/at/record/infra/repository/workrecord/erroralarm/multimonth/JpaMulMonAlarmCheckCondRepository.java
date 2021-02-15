package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.multimonth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonAlarmCheckCondRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthAlarmCheckCond;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth.KrcmtAlstChkmltUd;

@Stateless
public class JpaMulMonAlarmCheckCondRepository extends JpaRepository implements MulMonAlarmCheckCondRepository {
	
	private static final String SELECT_BY_LIST_ID = "SELECT c FROM KrcmtAlstChkmltUd c "
			+ " WHERE c.pk.eralCheckId IN :listErrorAlarmCheckID ORDER BY c.pk.condNo";
	private static final String SELECT_BY_CODE  = "SELECT c FROM KrcmtAlstChkmltUd c "
			+ " WHERE c.pk.eralCheckId =:errorAlarmCheckID" ;
	
	private static final String SELECT_BY_USEATR = "SELECT c FROM KrcmtAlstChkmltUd c "
			+ " WHERE c.pk.eralCheckId IN :lstId "
			+ " AND c.useAtr = :useAtr";
	@Override
	public List<MulMonthAlarmCheckCond> getMulMonAlarmsByListID(List<String> listErrorAlarmCheckID) {
		if(listErrorAlarmCheckID.isEmpty()) {
			return Collections.emptyList();
		}
		List<KrcmtAlstChkmltUd> data = new ArrayList<>();
		CollectionUtil.split(listErrorAlarmCheckID, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT,
				subIdList -> {
					data.addAll(
							this.queryProxy().query(SELECT_BY_LIST_ID, KrcmtAlstChkmltUd.class)
									.setParameter("listErrorAlarmCheckID", subIdList).getList());
				});
		//data.sort(Comparator.comparing(KrcmtAlstChkmltUd::getInsDate));
		return data.stream().map(c->c.toDomain()).collect(Collectors.toList());
	}
	
	@Override
	public Optional<MulMonthAlarmCheckCond> getMulMonAlarmByID(String errorAlarmCheckID) {
		Optional<MulMonthAlarmCheckCond> data = this.queryProxy().query(SELECT_BY_CODE, KrcmtAlstChkmltUd.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle(c->c.toDomain());
		return data;
	}
	
	@Override
	public void addMulMonAlarm(MulMonthAlarmCheckCond mulMonthAlarmCheckCond) {
		this.commandProxy().insert(KrcmtAlstChkmltUd.toEntity(mulMonthAlarmCheckCond));
		this.getEntityManager().flush();
	}
	
	@Override
	public void updateMulMonAlarm(MulMonthAlarmCheckCond mulMonthAlarmCheckCond) {
		KrcmtAlstChkmltUd newEntity = KrcmtAlstChkmltUd.toEntity(mulMonthAlarmCheckCond);
		this.commandProxy().update(newEntity);
		this.getEntityManager().flush();
	}
	
	@Override
	public void deleteMulMonAlarm(String errorAlarmCheckID) {
		this.commandProxy().remove(KrcmtAlstChkmltUd.class,errorAlarmCheckID);
		this.getEntityManager().flush();
	}

	@Override
	public List<MulMonthAlarmCheckCond> getMulCondByUseAtr(List<String> lstId, boolean useAtr) {
		List<KrcmtAlstChkmltUd> data = new ArrayList<>();
		data.addAll(
				this.queryProxy().query(SELECT_BY_USEATR, KrcmtAlstChkmltUd.class)
						.setParameter("lstId", lstId)
						.setParameter("useAtr", useAtr ? 1 : 0)
						.getList());
		return data.stream().map(c->c.toDomain()).collect(Collectors.toList());
	}

}
