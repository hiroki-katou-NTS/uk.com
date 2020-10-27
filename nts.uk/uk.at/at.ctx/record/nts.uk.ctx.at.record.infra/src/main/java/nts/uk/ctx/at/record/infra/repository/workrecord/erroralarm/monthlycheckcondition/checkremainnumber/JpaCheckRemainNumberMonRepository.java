package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.monthlycheckcondition.checkremainnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckRemainNumberMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.KrcmtCheckRemainNumberMon;
@Stateless
public class JpaCheckRemainNumberMonRepository extends JpaRepository implements CheckRemainNumberMonRepository {

	private static final String SELECT_CHECK_REMAIN_BY_ID = " SELECT c FROM KrcmtCheckRemainNumberMon c"
			+ " WHERE c.errorAlarmCheckID = :errorAlarmCheckID ";
	
	@Override
	public Optional<CheckRemainNumberMon> getByEralCheckID(String errorAlarmID) {
		Optional<CheckRemainNumberMon> data = this.queryProxy().query(SELECT_CHECK_REMAIN_BY_ID,KrcmtCheckRemainNumberMon.class)
				.setParameter("errorAlarmCheckID", errorAlarmID)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public void addCheckRemainNumberMon(CheckRemainNumberMon checkRemainNumberMon) {
		this.commandProxy().insert(KrcmtCheckRemainNumberMon.toEntity(checkRemainNumberMon));
		
	}

	@Override
	public void updateCheckRemainNumberMon(CheckRemainNumberMon checkRemainNumberMon) {
		KrcmtCheckRemainNumberMon newEntity = KrcmtCheckRemainNumberMon.toEntity(checkRemainNumberMon);
		KrcmtCheckRemainNumberMon updateEntity = KrcmtCheckRemainNumberMon.toEntity(checkRemainNumberMon);
		if(updateEntity.krcmtAlstChkmonUdremvr != null) {
			if(newEntity.krcmtAlstChkmonUdremvr ==null ) {
				updateEntity.krcmtAlstChkmonUdremvr = null;
			}else{
				if(!newEntity.krcmtAlstChkmonUdremvr.equals(updateEntity.krcmtAlstChkmonUdremvr)) {
					updateEntity.krcmtAlstChkmonUdremvr.compareOperator = newEntity.krcmtAlstChkmonUdremvr.compareOperator ; 
					updateEntity.krcmtAlstChkmonUdremvr.startValueDay = newEntity.krcmtAlstChkmonUdremvr.startValueDay ;
					updateEntity.krcmtAlstChkmonUdremvr.startValueTime = newEntity.krcmtAlstChkmonUdremvr.startValueTime ;
					updateEntity.krcmtAlstChkmonUdremvr.endValueDay = newEntity.krcmtAlstChkmonUdremvr.endValueDay ;
					updateEntity.krcmtAlstChkmonUdremvr.endValueTime = newEntity.krcmtAlstChkmonUdremvr.endValueTime ;
				}
			}
		}else {
			if(newEntity.krcmtAlstChkmonUdremvr != null) {
				updateEntity.krcmtAlstChkmonUdremvr = newEntity.krcmtAlstChkmonUdremvr;
			}
		}

		if(updateEntity.krcmtAlstChkmonUdremvs !=null) {
			if(newEntity.krcmtAlstChkmonUdremvs ==null ) {
				updateEntity.krcmtAlstChkmonUdremvs = null;
			}else{
				if(!newEntity.krcmtAlstChkmonUdremvs.equals(updateEntity.krcmtAlstChkmonUdremvs)) {
					updateEntity.krcmtAlstChkmonUdremvs.compareOperator = newEntity.krcmtAlstChkmonUdremvs.compareOperator ; 
					updateEntity.krcmtAlstChkmonUdremvs.daysValue = newEntity.krcmtAlstChkmonUdremvs.daysValue ;
					updateEntity.krcmtAlstChkmonUdremvs.timeValue = newEntity.krcmtAlstChkmonUdremvs.timeValue ;
				}
			}
		}else {
			if(newEntity.krcmtAlstChkmonUdremvs != null) {
				updateEntity.krcmtAlstChkmonUdremvs = newEntity.krcmtAlstChkmonUdremvs;
			}
		}
		this.commandProxy().update(updateEntity);
		
	}

	@Override
	public void deleteCheckRemainNumberMon(String errorAlarmID) {
		KrcmtCheckRemainNumberMon newEntity = this.queryProxy().find(errorAlarmID, KrcmtCheckRemainNumberMon.class).get();
		this.commandProxy().remove(newEntity);
	}

}
