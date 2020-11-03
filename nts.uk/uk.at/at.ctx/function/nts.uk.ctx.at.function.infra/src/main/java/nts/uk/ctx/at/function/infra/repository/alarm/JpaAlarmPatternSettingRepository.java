package nts.uk.ctx.at.function.infra.repository.alarm;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingSimple;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;
import nts.uk.ctx.at.function.infra.entity.alarm.KfnmtAlarmPatternSet;
import nts.uk.ctx.at.function.infra.entity.alarm.KfnmtAlarmPatternSetPK;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtCheckCondition;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.mutilmonth.KfnmtAlstPtnDeftmbsmon;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The class Jpa alarm pattern setting repository.<br>
 * Repository アラームリストパターン設定
 */
@Stateless
public class JpaAlarmPatternSettingRepository extends JpaRepository implements AlarmPatternSettingRepository {

//	private static final String SELECT_BASE = "SELECT a, b, b1 FROM KfnmtAlarmPatternSet a"
//			+ " JOIN KfnmtAlarmPerSet b ON a.pk.companyID = b.pk.companyID AND a.pk.alarmPatternCD = b.pk.alarmPatternCD"
//			+ " JOIN KfnmtAlarmPerSetItem b1 ON b.pk.companyID = b1.pk.companyID AND b.pk.alarmPatternCD = b1.pk.alarmPatternCD"
//			+ " JOIN KfnmtCheckCondition c ON a.pk.companyID = c.pk.companyID AND a.pk.alarmPatternCD = c.pk.alarmPatternCD"
//			+ " JOIN KfnmtCheckConItem c1 ON c.pk.companyID = c1.pk.companyID AND c.pk.alarmPatternCD = c1.pk.alarmPatternCD AND c.pk.alarmCategory = c1.pk.alarmCategory"
//			+ " JOIN KfnmtExtractionPeriodDaily d ON c.extractionId = d.kfnmtExtractionPeriodDailyPK.extractionId AND c.extractionRange = d.kfnmtExtractionPeriodDailyPK.extractionRange";
//
	/**
	 * The query Select all.
	 */
	private static final String SELECT_ALL = "SELECT a FROM KfnmtAlarmPatternSet a ";

	/**
	 * The query Select by company id.
	 */
	private static final String SELECT_BY_COMPANY = SELECT_ALL + "WHERE a.pk.companyID = :companyId";

	/**
	 * The query Select by alarm pattern code.
	 */
	private static final String SELECT_BY_ALARM_PATTERN_CD = SELECT_ALL
			+ "WHERE a.pk.companyID = :companyId AND a.pk.alarmPatternCD = :alarmPatternCode";

	/**
	 * Finds by company id.
	 *
	 * @param companyId the company id
	 * @return the <code>AlarmPatternSetting</code> list
	 */
	@Override
	public List<AlarmPatternSetting> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_COMPANY, KfnmtAlarmPatternSet.class)
								.setParameter("companyId", companyId)
								.getList(entity -> AlarmPatternSetting.createFromMemento(entity.getCompanyID(), entity));
	}

	@Override
	public Optional<AlarmPatternSetting> findByAlarmPatternCode(String companyId, String alarmPatternCode) {
		return this.queryProxy().query(SELECT_BY_ALARM_PATTERN_CD, KfnmtAlarmPatternSet.class)
								.setParameter("companyId", companyId)
								.setParameter("alarmPatternCode", alarmPatternCode)
								.getSingle(entity -> AlarmPatternSetting.createFromMemento(entity.getCompanyID(), entity));
	}

	@Override
	public void create(AlarmPatternSetting domain) {
		this.commandProxy().insert(new KfnmtAlarmPatternSet(domain));
	}

	@Override
	public void update(AlarmPatternSetting domain) {
		KfnmtAlarmPatternSet newEntity = new KfnmtAlarmPatternSet(domain);
		Optional<KfnmtAlarmPatternSet> currentEntity = this.queryProxy().query(SELECT_BY_ALARM_PATTERN_CD, KfnmtAlarmPatternSet.class)
																		.setParameter("companyId", domain.getCompanyID())
																		.setParameter("alarmPatternCode", domain.getAlarmPatternCD().v())
																		.getSingle();
		if (currentEntity.isPresent()) {
			currentEntity.get().updateEntity(newEntity);
			this.commandProxy().update(currentEntity);
		}
	}

	@Override
	public void delete(String companyId, String alarmPatternCode) {
		KfnmtAlarmPatternSetPK pk = new KfnmtAlarmPatternSetPK(companyId, alarmPatternCode);
		this.commandProxy().remove(KfnmtAlarmPatternSet.class, pk);
	}

	@Override
	public List<AlarmPatternSettingSimple> findByCompanyIdAndUser(String companyId) {
		return this.queryProxy().query(SELECT_BY_COMPANY, KfnmtAlarmPatternSet.class)
								.setParameter("companyId", companyId)
								.getList(KfnmtAlarmPatternSet::toSimpleDomain);
	}

	@Override
	public List<CheckCondition> getCheckCondition(String companyId, String alarmPatternCode) {
		return this.queryProxy().query(SELECT_BY_ALARM_PATTERN_CD, KfnmtAlarmPatternSet.class)
								.setParameter("companyId", companyId)
								.setParameter("alarmPatternCode", alarmPatternCode)
								.getSingle(KfnmtAlarmPatternSet::getCheckConList)
								.orElse(Collections.emptyList());
	}

	private static final String SELECT_AVER = "SELECT a FROM KfnmtAlstPtnDeftmbsmon a WHERE a.pk.extractionId = :extractionId";

	@Override
	public Optional<AverageMonth> findAverageMonth(String extractionId) {
		return this.queryProxy().query(SELECT_AVER, KfnmtAlstPtnDeftmbsmon.class)
								.setParameter("extractionId", extractionId)
								.getSingle(KfnmtAlstPtnDeftmbsmon::toDomain);
	}

	@Override
	public void createAver(AverageMonth domain) {
		this.commandProxy().insert(KfnmtAlstPtnDeftmbsmon.toEntity(domain));
	}

}
