package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.toppagealarm.KrcdtTopAlStamp;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.toppagealarm.KrcdtTopAlStampPk;

/**
 * 
 * @author chungnt
 *
 */
@Stateless
public class JpaTopPageAlarmStampingRepository extends JpaRepository implements TopPageAlarmStampingRepository {

	/**
	 * [1] insert(web打刻用トップページアラーム)
	 */
	@Override
	public void insert(TopPageAlarmStamping domain) {
		this.commandProxy().insert(KrcdtTopAlStamp.toEntity(domain));
	}

	/**
	 * [2] update(web打刻用トップページアラーム)
	 */
	@Override
	public void update(TopPageAlarmStamping domain) {
		this.commandProxy().update(KrcdtTopAlStamp.toEntity(domain));
	}

	/**
	 * [3] 取得する
	 */
	@Override
	public Optional<TopPageAlarmStamping> get(String employeeId, GeneralDate date) {
//		Optional<KrcdtTopAlStamp> entity = this.queryProxy().find(new KrcdtTopAlStampPk(employeeId, date), KrcdtTopAlStamp.class);
//		if(entity.isPresent()) {
//			return Optional.of(entity.get().toDomain());
//		}
		return Optional.empty();
	}
}
