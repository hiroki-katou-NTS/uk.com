package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.toppagealarm.KrcdtTopAlStamp;

/**
 * 
 * @author chungnt
 *
 */

public class JpaTopPageAlarmStampingRepository extends JpaRepository implements TopPageAlarmStampingRepository {

	/**
	 * [1] insert(web打刻用トップページアラーム)
	 */
	@Override
	public void insert(TopPageAlarmStamping domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	/**
	 * [2] update(web打刻用トップページアラーム)
	 */
	@Override
	public void update(TopPageAlarmStamping domain) {

	}

	/**
	 * [3] 取得する
	 */
	@Override
	public void get(String employeeId, GeneralDate date) {

	}

	public KrcdtTopAlStamp toEntity(TopPageAlarmStamping domain) {
		KrcdtTopAlStamp entity = new KrcdtTopAlStamp();

		entity.existenceError = domain.pageAlarm.getError().value;
		entity.isCancelled = domain.pageAlarm.getIsCancelled().value;
		entity.pk.finishDateTime = domain.getPageAlarm().getFinishDateTime();
		entity.pk.sidTgt = domain.getLstTopPageDetail().get(0).getSid();

		return entity;
	}

//	public TopPageAlarmStamping toDomain(KrcdtTopAlStamp entity) {
//		TopPageAlarm pageArm = new TopPageAlarm(error, lstsid)
//		
//		
//		TopPageAlarmStamping alarmStamping = new TopPageAlarmStamping(lstTopPageDetail, pageArm);
//		
//		return alarmStamping;
//	}

}
