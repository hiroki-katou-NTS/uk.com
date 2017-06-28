package nts.uk.ctx.at.record.infra.repository.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.BasicAgreementSettingRepository;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementOperationSetting;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtBasicAgreementSetting;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtBasicAgreementSettingPK;

@Stateless
public class JpaBasicAgreementSettingRepository extends JpaRepository implements BasicAgreementSettingRepository {

	private static final String UPDATE_BY_KEY;

	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("UPDATE KmkmtBasicAgreementSetting a ");
		builderString
				.append("SET a.alarmWeek = :alarmWeek , a.alarmTwoWeeks = :alarmTwoWeeks , a.alarmFourWeeks = :alarmFourWeeks , a.alarmOneMonth = :alarmOneMonth ,"
						+ " a.alarmTwoMonths = :alarmTwoMonths , a.alarmThreeMonths = :alarmThreeMonths , a.alarmOneYear = :alarmOneYear , a.errorWeek = :errorWeek ,"
						+ " a.errorTwoWeeks = :errorTwoWeeks , a.errorFourWeeks = :errorFourWeeks , a.errorOneMonth = :errorOneMonth , a.errorTwoMonths = :errorTwoMonths , "
						+ " a.errorThreeMonths = :errorThreeMonths , a.errorOneYear = :errorOneYear , a.limitWeek = :limitWeek , a.limitTwoWeeks = :limitTwoWeeks , "
						+ " a.limitFourWeeks = :limitFourWeeks , a.limitOneMonth = :limitOneMonth , a.limitTwoMonths = :limitTwoMonths ,"
						+ " a.limitThreeMonths = :limitThreeMonths , a.limitOneYear = :limitOneYear ");
		builderString.append("WHERE a.kmkmtBasicAgreementSettingPK.basicSettingId = :basicSettingId ");
		UPDATE_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KmkmtBasicAgreementSetting a ");
		builderString.append("WHERE a.kmkmtBasicAgreementSettingPK.basicSettingId = :basicSettingId ");
		FIND = builderString.toString();
	}

	@Override
	public void add(BasicAgreementSetting basicAgreementSetting) {
		this.commandProxy().insert(toEntity(basicAgreementSetting));
	}

	@Override
	public void update(BasicAgreementSetting basicAgreementSetting) {
		this.getEntityManager().createQuery(UPDATE_BY_KEY)
				.setParameter("basicSettingId", basicAgreementSetting.getBasicSettingId())
				.setParameter("alarmWeek", basicAgreementSetting.getAlarmWeek().v())
				.setParameter("alarmTwoWeeks", basicAgreementSetting.getAlarmTwoWeeks().v())
				.setParameter("alarmFourWeeks", basicAgreementSetting.getAlarmFourWeeks().v())
				.setParameter("alarmOneMonth", basicAgreementSetting.getAlarmOneMonth().v())
				.setParameter("alarmTwoMonths", basicAgreementSetting.getAlarmTwoMonths().v())
				.setParameter("alarmThreeMonths", basicAgreementSetting.getAlarmThreeMonths().v())
				.setParameter("alarmOneYear", basicAgreementSetting.getAlarmOneYear().v())
				.setParameter("errorWeek", basicAgreementSetting.getErrorWeek().v())
				.setParameter("errorTwoWeeks", basicAgreementSetting.getErrorTwoWeeks().v())
				.setParameter("errorFourWeeks", basicAgreementSetting.getErrorFourWeeks().v())
				.setParameter("errorOneMonth", basicAgreementSetting.getErrorOneMonth().v())
				.setParameter("errorTwoMonths", basicAgreementSetting.getErrorTwoMonths().v())
				.setParameter("errorThreeMonths", basicAgreementSetting.getErrorThreeMonths().v())
				.setParameter("errorOneYear", basicAgreementSetting.getErrorOneYear().v())
				.setParameter("limitWeek", basicAgreementSetting.getLimitWeek().v())
				.setParameter("limitTwoWeeks", basicAgreementSetting.getLimitTwoWeeks().v())
				.setParameter("limitFourWeeks", basicAgreementSetting.getLimitFourWeeks().v())
				.setParameter("limitOneMonth", basicAgreementSetting.getLimitOneMonth().v())
				.setParameter("limitTwoMonths", basicAgreementSetting.getLimitTwoMonths().v())
				.setParameter("limitThreeMonths", basicAgreementSetting.getLimitThreeMonths().v())
				.setParameter("limitOneYear", basicAgreementSetting.getLimitOneYear().v()).executeUpdate();
	}

	@Override
	public void remove(String basicSettingId) {
		this.commandProxy().remove(KmkmtBasicAgreementSetting.class, new KmkmtBasicAgreementSettingPK(basicSettingId));
	}

	@Override
	public Optional<BasicAgreementSetting> find(String basicSettingId) {
		return this.queryProxy().query(FIND, KmkmtBasicAgreementSetting.class)
				.setParameter("basicSettingId", basicSettingId).getSingle(f -> toDomain(f));
	}

	private KmkmtBasicAgreementSetting toEntity(BasicAgreementSetting basicAgreementSetting) {
		val entity = new KmkmtBasicAgreementSetting();

		entity.kmkmtBasicAgreementSettingPK = new KmkmtBasicAgreementSettingPK();
		entity.kmkmtBasicAgreementSettingPK.basicSettingId = basicAgreementSetting.getBasicSettingId();
		entity.alarmWeek = basicAgreementSetting.getAlarmWeek().v();
		entity.alarmTwoWeeks = basicAgreementSetting.getAlarmTwoWeeks().v();
		entity.alarmFourWeeks = basicAgreementSetting.getAlarmFourWeeks().v();
		entity.alarmOneMonth = basicAgreementSetting.getAlarmOneMonth().v();
		entity.alarmTwoMonths = basicAgreementSetting.getAlarmTwoMonths().v();
		entity.alarmThreeMonths = basicAgreementSetting.getAlarmThreeMonths().v();
		entity.alarmOneYear = basicAgreementSetting.getAlarmOneYear().v();
		entity.errorWeek = basicAgreementSetting.getErrorWeek().v();
		entity.errorTwoWeeks = basicAgreementSetting.getErrorTwoWeeks().v();
		entity.errorFourWeeks = basicAgreementSetting.getErrorFourWeeks().v();
		entity.errorOneMonth = basicAgreementSetting.getErrorOneMonth().v();
		entity.errorTwoMonths = basicAgreementSetting.getErrorTwoMonths().v();
		entity.errorThreeMonths = basicAgreementSetting.getErrorThreeMonths().v();
		entity.errorOneYear = basicAgreementSetting.getErrorOneYear().v();
		entity.limitWeek = basicAgreementSetting.getLimitWeek().v();
		entity.limitTwoWeeks = basicAgreementSetting.getLimitTwoWeeks().v();
		entity.limitFourWeeks = basicAgreementSetting.getLimitFourWeeks().v();
		entity.limitOneMonth = basicAgreementSetting.getLimitOneMonth().v();
		entity.limitTwoMonths = basicAgreementSetting.getLimitTwoMonths().v();
		entity.limitThreeMonths = basicAgreementSetting.getLimitThreeMonths().v();
		entity.limitOneYear = basicAgreementSetting.getLimitOneYear().v();

		return entity;
	}

	private static BasicAgreementSetting toDomain(KmkmtBasicAgreementSetting kmkmtBasicAgreementSetting) {
		BasicAgreementSetting basicAgreementSetting = BasicAgreementSetting.createFromJavaType(kmkmtBasicAgreementSetting.kmkmtBasicAgreementSettingPK.basicSettingId,
				kmkmtBasicAgreementSetting.alarmWeek, kmkmtBasicAgreementSetting.errorWeek, kmkmtBasicAgreementSetting.limitWeek,
				kmkmtBasicAgreementSetting.alarmTwoWeeks, kmkmtBasicAgreementSetting.errorTwoWeeks, kmkmtBasicAgreementSetting.limitTwoWeeks,
				kmkmtBasicAgreementSetting.alarmFourWeeks, kmkmtBasicAgreementSetting.errorFourWeeks, kmkmtBasicAgreementSetting.limitFourWeeks, 
				kmkmtBasicAgreementSetting.alarmOneMonth, kmkmtBasicAgreementSetting.errorOneMonth, kmkmtBasicAgreementSetting.limitOneMonth, kmkmtBasicAgreementSetting.alarmTwoMonths,
				kmkmtBasicAgreementSetting.errorTwoMonths, kmkmtBasicAgreementSetting.limitTwoMonths, kmkmtBasicAgreementSetting.alarmThreeMonths, 
				kmkmtBasicAgreementSetting.errorThreeMonths, kmkmtBasicAgreementSetting.limitThreeMonths, kmkmtBasicAgreementSetting.alarmOneYear,
				kmkmtBasicAgreementSetting.errorOneYear, kmkmtBasicAgreementSetting.limitOneYear);
		return basicAgreementSetting; 
	}
}
