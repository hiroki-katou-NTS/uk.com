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
		if(updateEntity.krcmtCompareRange != null) {
			if(newEntity.krcmtCompareRange ==null ) {
				updateEntity.krcmtCompareRange = null;
			}else{
				if(!newEntity.krcmtCompareRange.equals(updateEntity.krcmtCompareRange)) {
					updateEntity.krcmtCompareRange.compareOperator = newEntity.krcmtCompareRange.compareOperator ; 
					updateEntity.krcmtCompareRange.startValueDay = newEntity.krcmtCompareRange.startValueDay ;
					updateEntity.krcmtCompareRange.startValueTime = newEntity.krcmtCompareRange.startValueTime ;
					updateEntity.krcmtCompareRange.endValueDay = newEntity.krcmtCompareRange.endValueDay ;
					updateEntity.krcmtCompareRange.endValueTime = newEntity.krcmtCompareRange.endValueTime ;
				}
			}
		}else {
			if(newEntity.krcmtCompareRange != null) {
				updateEntity.krcmtCompareRange = newEntity.krcmtCompareRange;
			}
		}

		if(updateEntity.krcmtCompareSingleVal !=null) {
			if(newEntity.krcmtCompareSingleVal ==null ) {
				updateEntity.krcmtCompareSingleVal = null;
			}else{
				if(!newEntity.krcmtCompareSingleVal.equals(updateEntity.krcmtCompareSingleVal)) {
					updateEntity.krcmtCompareSingleVal.compareOperator = newEntity.krcmtCompareSingleVal.compareOperator ; 
					updateEntity.krcmtCompareSingleVal.daysValue = newEntity.krcmtCompareSingleVal.daysValue ;
					updateEntity.krcmtCompareSingleVal.timeValue = newEntity.krcmtCompareSingleVal.timeValue ;
				}
			}
		}else {
			if(newEntity.krcmtCompareSingleVal != null) {
				updateEntity.krcmtCompareSingleVal = newEntity.krcmtCompareSingleVal;
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
