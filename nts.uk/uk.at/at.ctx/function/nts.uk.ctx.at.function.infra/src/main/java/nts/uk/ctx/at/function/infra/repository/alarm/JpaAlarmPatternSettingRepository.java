package nts.uk.ctx.at.function.infra.repository.alarm;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingSimple;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;
import nts.uk.ctx.at.function.infra.entity.alarm.KfnmtAlarmPatternSet;
import nts.uk.ctx.at.function.infra.entity.alarm.KfnmtAlarmPatternSetPK;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.mutilmonth.KfnmtAlstPtnDeftmbsmon;

@Stateless
public class JpaAlarmPatternSettingRepository extends JpaRepository implements AlarmPatternSettingRepository {

//	private static final String SELECT_BASE = "SELECT a, b, b1 FROM KfnmtAlarmPatternSet a"
//			+ " JOIN KfnmtAlarmPerSet b ON a.pk.companyID = b.pk.companyID AND a.pk.alarmPatternCD = b.pk.alarmPatternCD"
//			+ " JOIN KfnmtAlarmPerSetItem b1 ON b.pk.companyID = b1.pk.companyID AND b.pk.alarmPatternCD = b1.pk.alarmPatternCD"
//			+ " JOIN KfnmtCheckCondition c ON a.pk.companyID = c.pk.companyID AND a.pk.alarmPatternCD = c.pk.alarmPatternCD"
//			+ " JOIN KfnmtCheckConItem c1 ON c.pk.companyID = c1.pk.companyID AND c.pk.alarmPatternCD = c1.pk.alarmPatternCD AND c.pk.alarmCategory = c1.pk.alarmCategory"
//			+ " JOIN KfnmtExtractionPeriodDaily d ON c.extractionId = d.kfnmtExtractionPeriodDailyPK.extractionId AND c.extractionRange = d.kfnmtExtractionPeriodDailyPK.extractionRange";
//

	private static final String SELECT_BY_COMPANY = "SELECT a FROM KfnmtAlarmPatternSet a WHERE a.pk.companyID = :companyId";

	private static final String SELECT_BY_ALARM_PATTERN_CD = "SELECT a FROM KfnmtAlarmPatternSet a WHERE  a.pk.companyID = :companyId  AND a.pk.alarmPatternCD = :alarmPatternCode";


	@Override
	public List<AlarmPatternSetting> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_COMPANY, KfnmtAlarmPatternSet.class).setParameter("companyId", companyId).getList(c -> c.toDomain());
	}

	@Override
	public Optional<AlarmPatternSetting> findByAlarmPatternCode(String companyId, String alarmPatternCode) {
		return this.queryProxy().query(SELECT_BY_ALARM_PATTERN_CD, KfnmtAlarmPatternSet.class)
				.setParameter("companyId", companyId).setParameter("alarmPatternCode", alarmPatternCode).getSingle(c ->c.toDomain());
	}

	@Override
	public void create(AlarmPatternSetting domain) {
		this.commandProxy().insert(KfnmtAlarmPatternSet.toEntity(domain));
	}

	@Override
	public void update(AlarmPatternSetting domain) {
		KfnmtAlarmPatternSet newEntity = KfnmtAlarmPatternSet.toEntity(domain);
		KfnmtAlarmPatternSet updateEntity = this.queryProxy().query(SELECT_BY_ALARM_PATTERN_CD, KfnmtAlarmPatternSet.class)
				.setParameter("companyId", domain.getCompanyID())
				.setParameter("alarmPatternCode", domain.getAlarmPatternCD().v()).getSingle().get();

		updateEntity.fromEntity(newEntity);
		this.commandProxy().update(updateEntity);
	}

	@Override
	public void delete(String companyId, String alarmPatternCode) {
		KfnmtAlarmPatternSetPK pk = new KfnmtAlarmPatternSetPK(companyId, alarmPatternCode);
		this.commandProxy().remove(KfnmtAlarmPatternSet.class, pk);
	}

	@Override
	public List<AlarmPatternSettingSimple> findByCompanyIdAndUser(String companyId) {
		return this.queryProxy().query(SELECT_BY_COMPANY, KfnmtAlarmPatternSet.class).setParameter("companyId", companyId).getList( c ->c.toSimpleDomain());
	}

	@Override
	public List<CheckCondition> getCheckCondition(String companyId, String alarmPatternCode) {
		return this.queryProxy().query(SELECT_BY_ALARM_PATTERN_CD, KfnmtAlarmPatternSet.class)
				.setParameter("companyId", companyId).setParameter("alarmPatternCode", alarmPatternCode)
				.getSingle(c -> c.toCheckCondition()).get();
	}

	private static final String SELECT_AVER = "SELECT a FROM KfnmtAlstPtnDeftmbsmon a WHERE a.pk.extractionId = :extractionId";
	@Override
	public Optional<AverageMonth> findAverageMonth(String extractionId) {
		return this.queryProxy().query(SELECT_AVER, KfnmtAlstPtnDeftmbsmon.class)
				.setParameter("extractionId", extractionId).getSingle(c ->c.toDomain());
	}

	@Override
	public void createAver(AverageMonth domain) {
		this.commandProxy().insert(KfnmtAlstPtnDeftmbsmon.toEntity(domain));
	}


}
