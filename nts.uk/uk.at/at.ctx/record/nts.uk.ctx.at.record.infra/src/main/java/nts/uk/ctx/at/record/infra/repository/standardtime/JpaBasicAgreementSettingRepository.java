package nts.uk.ctx.at.record.infra.repository.standardtime;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.BasicAgreementSettingRepository;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkstBasicAgreementSetting;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkstBasicAgreementSettingPK;

public class JpaBasicAgreementSettingRepository extends JpaRepository implements BasicAgreementSettingRepository{

	@Override
	public void add(BasicAgreementSetting basicAgreementSetting) {
		this.commandProxy().insert(toEntity(basicAgreementSetting));
	}

	@Override
	public void update(BasicAgreementSetting basicAgreementSetting) {
		this.commandProxy().update(toEntity(basicAgreementSetting));
	}

	@Override
	public void remove(String basicSettingId) {
		this.commandProxy().remove(KmkstBasicAgreementSetting.class,new KmkstBasicAgreementSettingPK(basicSettingId));
	}

	private KmkstBasicAgreementSetting toEntity(BasicAgreementSetting basicAgreementSetting){
		val entity = new KmkstBasicAgreementSetting();
		
		entity.kmkstBasicAgreementSettingPK = new KmkstBasicAgreementSettingPK();
		entity.kmkstBasicAgreementSettingPK.basicSettingId = basicAgreementSetting.getBasicSettingId();
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
}
