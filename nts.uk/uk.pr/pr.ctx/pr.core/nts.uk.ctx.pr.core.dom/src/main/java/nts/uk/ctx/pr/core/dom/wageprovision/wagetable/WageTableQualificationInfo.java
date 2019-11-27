package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WageTableQualificationInfo {
    /**
     * ID
     */
    private String id;

    /**
     * 資格コード
     */
    private String qualificationCode;

    /**
     * 資格名称
     */
    private String qualificationName;

    /**
     * 賃金テーブル支給金額
     */
    private Long wageTablePaymentAmount;
}