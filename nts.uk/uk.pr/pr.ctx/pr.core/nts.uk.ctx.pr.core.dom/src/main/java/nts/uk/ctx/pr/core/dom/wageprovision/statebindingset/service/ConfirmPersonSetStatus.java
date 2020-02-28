package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ConfirmPersonSetStatus {

    private String sid;

    /**
     * 給与明細書コード
     */
    private String salaryCode;

    /**
     * 給与明細書名称
     */
    private String salaryName;

    /**
     * 賞与明細書コード
     */
    private String bonusCode;

    /**
     * 賞与明細書名称
     */
    private String bonusName;

    /**
     * 適用設定区分
     */
    private Integer settingCtg;

    /**
     * 適用マスタコード
     */
    private String masterCode;

    /**
     * 適用マスタ名称
     */
    private String masterName;

}
