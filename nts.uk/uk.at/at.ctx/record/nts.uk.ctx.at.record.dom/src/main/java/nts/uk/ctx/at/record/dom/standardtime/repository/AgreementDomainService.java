package nts.uk.ctx.at.record.dom.standardtime.repository;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * ドメインサービス：36協定
 * @author shuichu_ishida
 */
public interface AgreementDomainService {

	/**
	 * 36協定基本設定を取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param workingSystem 労働制
	 * @return 36協定基本設定
	 */
	BasicAgreementSetting getBasicSet(String companyId, String employeeId, GeneralDate criteriaDate,
			WorkingSystem workingSystem);
}
