package nts.uk.ctx.pr.file.app.core.wageprovision.unitpricename;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.IntegrationItemCode;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.*;
import nts.uk.shr.com.enumcommon.Abolition;
import nts.uk.shr.com.primitive.Memo;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryPerUnitSetExportData {
    // Name
    /**
     * 会社ID
     */
    private String cid;

    /**
     * コード
     */
    private PerUnitPriceCode code;

    /**
     * 名称
     */
    private PerUnitPriceName name;

    /**
     * 廃止区分
     */
    private Abolition abolition;

    /**
     * 略名
     */
    private PerUnitPriceShortName shortName;

    /**
     * 統合コード
     */
    private Optional<IntegrationItemCode> integrationCode;

    /**
     * メモ
     */
    private Optional<Memo> note;

     // setting

    /**
     * 単価種類
     */
    private PerUnitPriceType unitPriceType;

    /**
     * 固定的賃金
     */
    /**
     * 設定区分
     */
    private SettingClassification settingAtr;

    /**
     * 全員一律
     */
    private Optional<DesignateByAllMember> everyoneEqualSet;

    /**
     * 給与契約形態ごと
     */
    private Optional<PerSalaryContractType> perSalaryContractType;
}
