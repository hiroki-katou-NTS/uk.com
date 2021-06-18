package nts.uk.ctx.at.function.infra.entity.outputitemofworkledger;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * entity: 勤務台帳の設定
 */
@Entity
@Table(name = "KFNMT_RPT_REC_SETTING")
@NoArgsConstructor
@AllArgsConstructor
public class KfnmtRptRecSetting extends UkJpaEntity implements Serializable {
    public static long serialVersionUID = 1L;

    @Id
    // ID->勤務台帳の出力項目.ID
    @Column(name = "LAYOUT_ID")
    public String iD;
    //	契約コード
    @Column(name = "CONTRACT_CD")
    public String contractCode;

    // 	会社ID
    @Column(name = "CID")
    public String companyId;

    //	表示コード -> 勤務台帳の出力項目.コード
    @Column(name = "EXPORT_CD")
    public int displayCode;

    //	名称 -> 勤務台帳の出力項目.名称
    @Column(name = "NAME")
    public String name;

    // 	社員ID-> 勤務台帳の出力項目.社員ID
    @Column(name = "SID")
    public String employeeId;

    // 	定型自由区分	-> 勤務台帳の出力項目.定型自由区分
    @Column(name = "SETTING_TYPE")
    public int settingType;

    @Override
    protected Object getKey() {
        return iD;
    }
    public static KfnmtRptRecSetting fromDomain(WorkLedgerOutputItem domain, String cid){
        String sid = domain.getEmployeeId().isPresent() ? domain.getEmployeeId().get() : null;
        return new KfnmtRptRecSetting(
                domain.getId(),
                AppContexts.user().contractCode(),
                cid,
                Integer.parseInt(domain.getCode().v()),
                domain.getName().v(),
                sid,
                domain.getStandardFreeClassification().value
        );
    }
}
