package nts.uk.ctx.at.function.infra.entity.outputitemsofworkstatustable;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity: 	勤務状況表の設定
 *
 * @author chinh.hm
 */
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_RPT_WK_REC_SETTING")
@AllArgsConstructor
public class KfnmtRptWkRecSetting extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    // 	設定ID
    @Id
    @Column(name = "ID")
    public String iD;

    //	契約コード
    @Column(name = "CONTRACT_CD")
    public String contractCode;

    // 	会社ID
    @Column(name = "CID")
    public String companyId;

    //	設定表示コード -> 勤務状況の出力設定.設定表示コード
    @Column(name = "DISPLAY_CODE")
    public int displayCode;

    //	設定名称 -> 勤務状況の出力設定.設定名称
    @Column(name = "NAME")
    public String name;

    // 	社員ID-> 勤務状況の出力設定.社員ID
    @Column(name = "EMPLOYEE_ID")
    public String employeeId;

    // 	定型自由区分	-> 勤務状況の出力設定.定型自由区分
    @Column(name = "SETTING_TYPE")
    public int settingType;

    @Override
    protected Object getKey() {
        return iD;
    }

    public static KfnmtRptWkRecSetting fromDomain(WorkStatusOutputSettings domain, String cid) {
        return new KfnmtRptWkRecSetting(
                domain.getSettingId(),
                AppContexts.user().contractCode(),
                cid,
                Integer.parseInt(domain.getSettingDisplayCode().v()),
                domain.getSettingName().v(),
                domain.getEmployeeId(),
                domain.getStandardFreeDivision().value
        );
    }

}
