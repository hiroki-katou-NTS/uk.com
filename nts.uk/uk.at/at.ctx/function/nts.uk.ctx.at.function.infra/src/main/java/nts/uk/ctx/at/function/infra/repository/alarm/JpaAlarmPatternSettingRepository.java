package nts.uk.ctx.at.function.infra.repository.alarm;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.KfnmtAlarmPatternSet;

@Stateless
public class JpaAlarmPatternSettingRepository extends JpaRepository implements AlarmPatternSettingRepository {

	private final String SELECT_BASE = "SELECT a, b, b1 FROM KfnmtAlarmPatternSet a"
			+ " JOIN KfnmtAlarmPerSet b ON a.pk.companyID = b.pk.companyID AND a.pk.alarmPatternCD = b.pk.alarmPatternCD"
			+ " JOIN KfnmtAlarmPerSetItem b1 ON b.pk.companyID = b1.pk.companyID AND b.pk.alarmPatternCD = b1.pk.alarmPatternCD"
			+ " JOIN KfnmtCheckCondition c ON a.pk.companyID = c.pk.companyID AND a.pk.alarmPatternCD = c.pk.alarmPatternCD"
			+ " JOIN KfnmtCheckConItem c1 ON c.pk.companyID = c1.pk.companyID AND c.pk.alarmPatternCD = c1.pk.alarmPatternCD AND c.pk.alarmCategory = c1.pk.alarmCategory"
			+ " JOIN KfnmtExtractionPeriodDaily d ON c.extractionId = d.kfnmtExtractionPeriodDailyPK.extractionId AND c.extractionRange = d.kfnmtExtractionPeriodDailyPK.extractionRange";
	
	
	private final String SELECT_BY_COMPANY = "SELECT a FROM KfnmtAlarmPatternSet a WHERE a.pk.companyID = = :companyId";
	@Override
	public List<AlarmPatternSetting> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_COMPANY, KfnmtAlarmPatternSet.class).setParameter("companyId", companyId).getList(c -> c.toDomain());
	}

	@Override
	public Optional<AlarmPatternSetting> findByAlarmPatternCode(String companyId, String alarmPatternCode) {

		return null;
	}

	@Override
	public void create(AlarmPatternSetting domain) {

		
	}

	@Override
	public void update(AlarmPatternSetting domain) {

		
	}

	@Override
	public void delete(AlarmPatternSetting domain) {

		
	}

}
