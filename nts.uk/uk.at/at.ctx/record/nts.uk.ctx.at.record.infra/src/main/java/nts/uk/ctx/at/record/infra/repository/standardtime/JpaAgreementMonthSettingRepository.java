package nts.uk.ctx.at.record.infra.repository.standardtime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.record.infra.entity.standardtime.Krcmt36agrMonth;
import nts.uk.ctx.at.record.infra.entity.standardtime.Krcmt36agrMonthPK;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;

@Stateless
public class JpaAgreementMonthSettingRepository extends JpaRepository implements AgreementMonthSettingRepository {

	private static final String FIND;

	private static final String FIND_BY_KEY;

	private static final String FIND_BY_KEYS;

	private static final String DEL_BY_KEY;

	private static final String IS_EXIST_DATA;
	
	private static final String FIND_BY_ID;

	private static final String FIND_BY_SID;

	private static final String FIND_BY_LIST_SID;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM Krcmt36agrMonth a ");
		builderString.append("WHERE a.krcmt36agrMonthPK.employeeId = :employeeId ");
		builderString.append("ORDER BY a.krcmt36agrMonthPK.yearmonthValue DESC ");
		FIND = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM Krcmt36agrMonth a ");
		builderString.append("WHERE a.krcmt36agrMonthPK.employeeId = :employeeId ");
		builderString.append("AND a.krcmt36agrMonthPK.yearmonthValue = :yearmonthValue ");
		FIND_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM Krcmt36agrMonth a ");
		builderString.append("WHERE a.krcmt36agrMonthPK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcmt36agrMonthPK.yearmonthValue IN :yearmonthValues ");
		FIND_BY_KEYS = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM Krcmt36agrMonth a ");
		builderString.append("WHERE a.krcmt36agrMonthPK.employeeId = :employeeId ");
		builderString.append("AND a.krcmt36agrMonthPK.yearmonthValue = :yearmonthValue ");
		DEL_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(a) ");
		builderString.append("FROM Krcmt36agrMonth a ");
		builderString.append("WHERE a.krcmt36agrMonthPK.employeeId = :employeeId ");
		builderString.append("AND a.krcmt36agrMonthPK.yearmonthValue = :yearmonthValue ");
		IS_EXIST_DATA = builderString.toString();
		
		// fix bug 100605
		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM Krcmt36agrMonth a ");
		builderString.append("WHERE a.krcmt36agrMonthPK.employeeId = :employeeId ");
		builderString.append("AND a.krcmt36agrMonthPK.yearmonthValue = :yearmonthValue ");
		builderString.append("ORDER BY a.krcmt36agrMonthPK.yearmonthValue DESC ");
		FIND_BY_ID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM Krcmt36agrMonth a ");
		builderString.append("WHERE a.krcmt36agrMonthPK.employeeId = :employeeId ");
		builderString.append("ORDER BY a.krcmt36agrMonthPK.yearmonthValue DESC ");
		FIND_BY_SID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM Krcmt36agrMonth a ");
		builderString.append("WHERE a.krcmt36agrMonthPK.employeeId IN :employeeIds ");
		FIND_BY_LIST_SID = builderString.toString();
	}

	@Override
	public List<AgreementMonthSetting> find(String employeeId) {
		return this.queryProxy().query(FIND, Krcmt36agrMonth.class).setParameter("employeeId", employeeId)
				.getList(f -> toDomain(f));
	}

	@Override
	public Optional<AgreementMonthSetting> findByKey(String employeeId, YearMonth yearMonth) {
		return this.queryProxy().query(FIND_BY_KEY, Krcmt36agrMonth.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearmonthValue", yearMonth.v())
				.getSingle(f -> toDomain(f));
	}

    @Override
    public List<AgreementMonthSetting> findByKey(List<String> employeeIds, List<YearMonth> yearMonths) {
		if (employeeIds == null || employeeIds.isEmpty())
			return Collections.emptyList();
		if (yearMonths == null || yearMonths.isEmpty())
			return Collections.emptyList();
		List<Integer> yearmonthValues = yearMonths.stream().map(x -> x.v()).collect(Collectors.toList());
		List<AgreementMonthSetting> result = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			result.addAll(this.queryProxy().query(FIND_BY_KEYS, Krcmt36agrMonth.class)
					.setParameter("employeeIds", splitData).setParameter("yearmonthValues", yearmonthValues)
					.getList(f -> toDomain(f)));
		});
		return result;
    }
	
	@Override
	public void add(AgreementMonthSetting agreementMonthSetting) {
		this.commandProxy().insert(toEntity(agreementMonthSetting));
	}

	@Override
	public void delete(String employeeId, BigDecimal yearMonthValue) {
		this.getEntityManager().createQuery(DEL_BY_KEY).setParameter("employeeId", employeeId)
				.setParameter("yearmonthValue", yearMonthValue).executeUpdate();
	}

	@Override
	public void update(AgreementMonthSetting agreementMonthSetting) {

		Optional<Krcmt36agrMonth> entity = this.queryProxy().query(FIND_BY_KEY, Krcmt36agrMonth.class)
				.setParameter("employeeId", agreementMonthSetting.getEmployeeId())
				.setParameter("yearmonthValue", agreementMonthSetting.getYearMonthValue().v()).getSingle();
		
		if (entity.isPresent()) {
			Krcmt36agrMonth data = entity.get();
			data.alarmOneMonth = agreementMonthSetting.getOneMonthTime().getAlarm().valueAsMinutes();
			data.errorOneMonth = agreementMonthSetting.getOneMonthTime().getError().valueAsMinutes();
			this.commandProxy().update(data);
		}

	}
	
	// fix bug 100605
	@Override
	public void updateById(AgreementMonthSetting agreementMonthSetting, Integer yearMonthValueOld) {

		Optional<Krcmt36agrMonth> entity = this.queryProxy().query(FIND_BY_ID, Krcmt36agrMonth.class)
				.setParameter("employeeId", agreementMonthSetting.getEmployeeId())
				.setParameter("yearmonthValue", yearMonthValueOld).getSingle();
		if (entity.isPresent()) {
			Krcmt36agrMonth data = entity.get();
			this.delete(data.krcmt36agrMonthPK.employeeId, new BigDecimal(yearMonthValueOld));
			this.add(agreementMonthSetting);
		}

	}

	@Override
	public void delete(AgreementMonthSetting agreementMonthSetting) {
		this.commandProxy().remove(toEntity(agreementMonthSetting));
	}

	@Override
	public boolean checkExistData(String employeeId, BigDecimal yearMonthValue) {
		return this.queryProxy().query(IS_EXIST_DATA, long.class).setParameter("employeeId", employeeId)
				.setParameter("yearmonthValue", yearMonthValue).getSingle().get() > 0;
	}

	@Override
	public List<AgreementMonthSetting> getByEmployeeId(String employeeId) {
		return this.queryProxy()
				.query(FIND_BY_SID, Krcmt36agrMonth.class)
				.setParameter("employeeId", employeeId).getList(x -> toDomain(x));
	}

	@Override
	public Optional<AgreementMonthSetting> getByEmployeeIdAndYm(String employeeId, YearMonth yearMonth) {
		return this.queryProxy()
				.query(FIND_BY_KEY, Krcmt36agrMonth.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearmonthValue", yearMonth.v()).getSingle(x -> toDomain(x));
	}

	@Override
	public List<AgreementMonthSetting> findByListEmployee(List<String> employeeIds) {
		if (employeeIds == null || employeeIds.isEmpty())
			return Collections.emptyList();
		List<AgreementMonthSetting> result = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			result.addAll(this.queryProxy().query(FIND_BY_LIST_SID, Krcmt36agrMonth.class)
					.setParameter("employeeIds", splitData)
					.getList(f -> toDomain(f)));
		});
		return result;
	}

	private static AgreementMonthSetting toDomain(Krcmt36agrMonth krcmt36agrMonth) {
		return AgreementMonthSetting.createFromJavaType(
				krcmt36agrMonth.krcmt36agrMonthPK.employeeId,
				krcmt36agrMonth.krcmt36agrMonthPK.yearmonthValue,
				krcmt36agrMonth.errorOneMonth, krcmt36agrMonth.alarmOneMonth);
	}

	private Krcmt36agrMonth toEntity(AgreementMonthSetting agreementMonthSetting) {
		val entity = new Krcmt36agrMonth();

		entity.krcmt36agrMonthPK = new Krcmt36agrMonthPK();
		entity.krcmt36agrMonthPK.employeeId = agreementMonthSetting.getEmployeeId();
		entity.krcmt36agrMonthPK.yearmonthValue = new BigDecimal(agreementMonthSetting.getYearMonthValue().v());
		entity.alarmOneMonth = agreementMonthSetting.getOneMonthTime().getAlarm().valueAsMinutes();
		entity.errorOneMonth = agreementMonthSetting.getOneMonthTime().getError().valueAsMinutes();
		return entity;
	}
}
