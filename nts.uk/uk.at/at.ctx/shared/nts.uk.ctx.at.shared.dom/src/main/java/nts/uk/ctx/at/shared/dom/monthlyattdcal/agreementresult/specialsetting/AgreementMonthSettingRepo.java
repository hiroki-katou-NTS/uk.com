package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.specialsetting;

import nts.arc.time.YearMonth;

import java.util.List;
import java.util.Optional;

/**
 * ３６協定年月設定Repository
 * @author quang.nh1
 */
public interface AgreementMonthSettingRepo {

    /**
     * [1] Insert(３６協定年月設定)
     */
    void insert(AgreementMonthSetting domain);

    /**
     * [2] Update(３６協定年月設定)
     */
    void update(AgreementMonthSetting domain);

    /**
     * [3] Delete(３６協定年月設定)
     */
    void delete(AgreementMonthSetting domain);

    /**
     * [4] get
     * 指定社員の全ての３６協定年月設定を取得する
     */
    List<AgreementMonthSetting> getByEmployeeId(String employeeId);

    /**
     * [4] get
     * 指定社員の全ての３６協定年月設定を取得する
     */
    Optional<AgreementMonthSetting> getByEmployeeIdAndYm(String employeeId, YearMonth yearMonth);
}
