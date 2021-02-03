package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

//import org.eclipse.persistence.exceptions.IntegrityException;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;

public interface AgreementMonthSettingRepository {

	List<AgreementMonthSetting> find(String employeeId);
	
	Optional<AgreementMonthSetting> findByKey(String employeeId, YearMonth yearMonth);

	List<AgreementMonthSetting> findByKey(List<String> employeeIds, List<YearMonth> yearMonths);
	
	void add(AgreementMonthSetting agreementMonthSetting);
	
	void update(AgreementMonthSetting agreementMonthSetting);
	
	void delete(String employeeId, BigDecimal yearMonthValue);
	
	boolean checkExistData(String employeeId, BigDecimal yearMonthValue);
	
	// fix bug 100605
	void updateById(AgreementMonthSetting agreementMonthSetting, Integer yearMonthValueOld);

	/**
	 * 	[3] Delete(３６協定年月設定)
	 */
	void delete(AgreementMonthSetting agreementMonthSetting);

	/**
	 * [4] get
	 * 指定社員の全ての３６協定年月設定を取得する
	 */
	List<AgreementMonthSetting> getByEmployeeId(String employeeId);

	/**
	 * [5] get
	 * 指定社員の全ての３６協定年月設定を取得する
	 */
	Optional<AgreementMonthSetting> getByEmployeeIdAndYm(String employeeId, YearMonth yearMonth);

	List<AgreementMonthSetting> findByListEmployee(List<String> employeeIds);
	
}
