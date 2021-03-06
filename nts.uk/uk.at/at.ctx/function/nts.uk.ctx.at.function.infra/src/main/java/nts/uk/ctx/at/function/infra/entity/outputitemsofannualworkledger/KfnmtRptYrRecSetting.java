package nts.uk.ctx.at.function.infra.entity.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * entity: 	年間勤務台帳の設定
 *
 * @author : chinh.hm
 */
@Entity
@Table(name = "KFNMT_RPT_YR_REC_SETTING")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KfnmtRptYrRecSetting extends UkJpaEntity implements Serializable {

    // 	設定ID
    @Id
    @Column(name = "LAYOUT_ID")
    public String iD;

    //	契約コード
    @Column(name = "CONTRACT_CD")
    public String contractCode;

    // 	会社ID
    @Column(name = "CID")
    public String companyId;

    //	表示コード -> 年間勤務台帳の出力設定.コード
    @Column(name = "EXPORT_CD")
    public int displayCode;

    //	名称 -> 年間勤務台帳の出力設定.名称
    @Column(name = "NAME")
    public String name;

    // 	社員ID-> 年間勤務台帳の出力設定.社員ID
    @Column(name = "SID")
    public String employeeId;

    // 	定型自由区分-> 年間勤務台帳の出力設定.	定型自由区分
    @Column(name = "SETTING_TYPE")
    public int settingType;

    @Override
    protected Object getKey() {
        return iD;
    }

    public static KfnmtRptYrRecSetting fromDomain(String cid,AnnualWorkLedgerOutputSetting outputSetting){
        return  new KfnmtRptYrRecSetting(
                outputSetting.getID(),
                AppContexts.user().contractCode(),
                cid,
                Integer.parseInt(outputSetting.getCode().v()),
                outputSetting.getName().v(),
                outputSetting.getEmployeeId(),
                outputSetting.getStandardFreeDivision().value
        );
    }
}
