package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WageTableQualification {
    /**
     * 資格グループコード
     */
    private String qualificationGroupCode;

    /**
     * 資格グループ名
     */
    private String qualificationGroupName;

    /**
     * 支払方法
     */
    private int paymentMethod;

    /**
     * 対象資格コード
     */
    private List<WageTableQualificationInfo> eligibleQualificationCode;
}