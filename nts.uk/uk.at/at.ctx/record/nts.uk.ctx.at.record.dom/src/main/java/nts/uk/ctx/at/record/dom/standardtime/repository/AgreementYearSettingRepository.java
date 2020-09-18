package nts.uk.ctx.at.record.dom.standardtime.repository;

import nts.uk.ctx.at.record.dom.standardtime.AgreementYearSetting;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

//import nts.arc.time.YearMonth;
//import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;

public interface AgreementYearSettingRepository {
	
	List<AgreementYearSetting> find(String employeeId);
	
	void add(AgreementYearSetting agreementYearSetting);
	
	void update(AgreementYearSetting agreementYearSetting);
	
	void delete(String employeeId, int yearvalue);
	
	boolean checkExistData(String employeeId, BigDecimal yearValue);

	void updateById(AgreementYearSetting agreementYearSetting, Integer yearMonthValueOld);

	Optional<AgreementYearSetting> findByKey(String employeeId, int yearMonth);

	List<AgreementYearSetting> findByKey(List<String> employeeIds, int yearMonth);
//
//	/**
//	 * [4] get
//	 * 指定社員の全ての３６協定年月設定を取得する
//	 */
//	List<AgreementYearSetting> getByEmployeeId(String employeeId);
//
//	/**
//	 * [4] get
//	 * 指定社員の全ての３６協定年月設定を取得する
//	 */
//	Optional<AgreementYearSetting> getByEmployeeIdAndYm(String employeeId, Year year);
}
