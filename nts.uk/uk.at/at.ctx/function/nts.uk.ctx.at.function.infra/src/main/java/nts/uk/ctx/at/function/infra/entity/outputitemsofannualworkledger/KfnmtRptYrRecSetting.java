package nts.uk.ctx.at.function.infra.entity.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * entity: 	年間勤務台帳の設定
 *
 * @author : chinh.hm
 */
@Entity
@Table(name = "KFNMT_RPT_YR_REC_SETTING")
@AllArgsConstructor
@Getter
@Setter
public class KfnmtRptYrRecSetting extends UkJpaEntity implements Serializable {

    @EmbeddedId
    public KfnmtRptYrRecSettingPk pk;

    //	契約コード
    @Column(name = "CONTRACT_CD")
    public String contractCode;

    // 	会社ID
    @Column(name = "CID")
    public String companyId;

    //	表示コード -> 年間勤務台帳の出力設定.コード
    @Column(name = "DISPLAY_CODE")
    public int displayCode;

    //	名称 -> 年間勤務台帳の出力設定.名称
    @Column(name = "NAME")
    public String name;

    // 	社員ID-> 年間勤務台帳の出力設定.社員ID
    @Column(name = "EMPLOYEE_ID")
    public String employeeId;

    // 	定型自由区分-> 年間勤務台帳の出力設定.	定型自由区分
    @Column(name = "SETTING_TYPE")
    public int settingType;

    @Override
    protected Object getKey() {
        return null;
    }

    public static KfnmtRptYrRecSetting fromDomain(String cid,AnnualWorkLedgerOutputSetting outputSetting){
        return  new KfnmtRptYrRecSetting(
                new KfnmtRptYrRecSettingPk(outputSetting.getID()),
                AppContexts.user().contractCode(),
                cid,
                Integer.parseInt(outputSetting.getCode().v()),
                outputSetting.getName().v(),
                outputSetting.getEmployeeId(),
                outputSetting.getStandardFreeDivision().value
        );
    }
}
