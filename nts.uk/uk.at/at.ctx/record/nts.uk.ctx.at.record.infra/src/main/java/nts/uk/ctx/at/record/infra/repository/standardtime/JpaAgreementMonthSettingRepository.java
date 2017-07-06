package nts.uk.ctx.at.record.infra.repository.standardtime;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgreementMonthSet;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgreementMonthSetPK;

@Stateless
public class JpaAgreementMonthSettingRepository extends JpaRepository implements AgreementMonthSettingRepository {

	private static final String FIND;
	
	private static final String UPDATE_BY_KEY;
	
	private static final String DEL_BY_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KmkmtAgreementMonthSet a ");
		builderString.append("WHERE a.kmkmtAgreementMonthSetPK.employeeId = :employeeId ");
		FIND = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KmkmtAgreementMonthSet a ");
		builderString.append("SET a.errorOneMonth = :errorOneMonth , a.alarmOneMonth = :alarmOneMonth ");
		builderString.append("WHERE a.kmkmtAgreementMonthSetPK.employeeId = :employeeId ");
		builderString.append("AND a.kmkmtAgreementMonthSetPK.yearmonthValue = :yearmonthValue ");
		UPDATE_BY_KEY = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KmkmtAgreementMonthSet a ");
		builderString.append("WHERE a.kmkmtAgreementMonthSetPK.employeeId = :employeeId ");
		builderString.append("AND a.kmkmtAgreementMonthSetPK.yearmonthValue = :yearmonthValue ");
		DEL_BY_KEY = builderString.toString();
	}

	@Override
	public List<AgreementMonthSetting> find(String employeeId) {
		return this.queryProxy().query(FIND, KmkmtAgreementMonthSet.class).setParameter("employeeId", employeeId)
				.getList(f -> toDomain(f));
	}

	@Override
	public void add(AgreementMonthSetting agreementMonthSetting) {
		this.commandProxy().insert(toEntity(agreementMonthSetting));
	}

	@Override
	public void delete(String employeeId, BigDecimal yearMonthValue) {
		this.getEntityManager().createQuery(DEL_BY_KEY)
				.setParameter("employeeId", employeeId)
				.setParameter("yearmonthValue", yearMonthValue)
				.executeUpdate();
	}

	@Override
	public void update(AgreementMonthSetting agreementMonthSetting) {
		this.getEntityManager().createQuery(UPDATE_BY_KEY)
				.setParameter("employeeId", agreementMonthSetting.getEmployeeId())
				.setParameter("yearmonthValue", agreementMonthSetting.getYearMonthValue().v())
				.setParameter("errorOneMonth", agreementMonthSetting.getErrorOneMonth().v())
				.setParameter("alarmOneMonth", agreementMonthSetting.getAlarmOneMonth().v()).executeUpdate();
	}

	private static AgreementMonthSetting toDomain(KmkmtAgreementMonthSet kmkmtAgreementMonthSet) {
		AgreementMonthSetting agreementMonthSetting = AgreementMonthSetting.createFromJavaType(
				kmkmtAgreementMonthSet.kmkmtAgreementMonthSetPK.employeeId,
				kmkmtAgreementMonthSet.kmkmtAgreementMonthSetPK.yearmonthValue,
				kmkmtAgreementMonthSet.errorOneMonth,
				kmkmtAgreementMonthSet.alarmOneMonth);
		return agreementMonthSetting;
	}

	private KmkmtAgreementMonthSet toEntity(AgreementMonthSetting agreementMonthSetting){
		val entity = new KmkmtAgreementMonthSet();
		
		entity.kmkmtAgreementMonthSetPK = new KmkmtAgreementMonthSetPK();
		entity.kmkmtAgreementMonthSetPK.employeeId = agreementMonthSetting.getEmployeeId();
		entity.kmkmtAgreementMonthSetPK.yearmonthValue = new BigDecimal(agreementMonthSetting.getYearMonthValue().v());
		entity.alarmOneMonth = agreementMonthSetting.getAlarmOneMonth().v();
		entity.errorOneMonth = agreementMonthSetting.getErrorOneMonth().v();

		return entity;
	}
}
