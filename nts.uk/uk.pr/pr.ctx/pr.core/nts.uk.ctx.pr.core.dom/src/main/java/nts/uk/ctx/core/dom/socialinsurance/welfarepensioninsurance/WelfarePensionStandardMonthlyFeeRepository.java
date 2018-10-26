package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import java.util.Optional;

/**
 * 厚生年金標準月額
 */
public interface WelfarePensionStandardMonthlyFeeRepository {
    /**
     * ドメインモデル「厚生年金標準月額」を全て取得する
     * 条件：
     * 対象期間.開始年月 <= C2_2 <= 対象期間.終了年月
     *
     * @param startYearMonth startYearMonth
     * @return 厚生年金標準月額
     */
    Optional<WelfarePensionStandardMonthlyFee> getWelfarePensionStandardMonthlyFeeByStartYearMonth(int startYearMonth);
    Optional<MonthlyScopeOfWelfarePensionCompensation> getWelfarePensionStandardMonthlyFeeByStartYearMonthCom(int startYearMonth);

    }
