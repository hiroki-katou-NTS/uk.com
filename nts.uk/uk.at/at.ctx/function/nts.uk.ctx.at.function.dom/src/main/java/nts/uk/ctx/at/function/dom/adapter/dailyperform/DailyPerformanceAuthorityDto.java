package nts.uk.ctx.at.function.dom.adapter.dailyperform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class DailyPerformanceAuthorityDto {

    private String companyId;

    /**
     * ロールID
     */
    // タイプID
    private String roleID;

    /**
     * 日別実績の機能NO
     */
    private BigDecimal functionNo;

    /**
     * 利用区分
     */
    private boolean availability;
}
