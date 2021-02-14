package nts.uk.ctx.at.function.infra.entity.arbitraryperiodsummarytable;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 任意期間集計表の出力勤怠項目
 */
@Entity
@Table(name = "KFNMT_RPT_WK_ANP_OUT")
@NoArgsConstructor
@AllArgsConstructor
public class KfnmtRptWkAnpOut extends UkJpaEntity implements Serializable {

    public static final long serialVersionUID = 1L;

    /**
     * 設定ID -> 	任意期間集計表の出力設定.設定ID
     */
    @Id
    @Column(name = "LAYOUT_ID")
    public String layOutId;

    /**
     * 契約コード
     */
    @Column(name = "CONTRACT_CD")
    public String contractCd;

    /**
     * 会社コード
     */
    @Column(name = "CID")
    public String companyId;

    /**
     * 設定コード : 任意期間集計表の出力設定.設定表示コード
     */
    @Column(name = "EXPORT_CD")
    public int exportCd;

    /**
     * 名称 : 任意期間集計表の出力設定.	設定名称
     */
    @Column(name = "NAME")
    public String name;


    /**
     * 社員ID : 任意期間集計表の出力設定.社員ID
     */
    @Column(name = "SID")
    public String sid;

    /**
     * 定型自由区分: 任意期間集計表の出力設定.定型自由区分
     */
    @Column(name = "SETTING_TYPE")
    public int settingType;

    @Override
    protected Object getKey() {
        return layOutId;
    }

    public static KfnmtRptWkAnpOut fromDomain(OutputSettingOfArbitrary domain, String cid) {
        String sid = domain.getEmployeeId().isPresent() ? domain.getEmployeeId().get() : null;
        return new KfnmtRptWkAnpOut(
                domain.getSettingId(),
                AppContexts.user().contractCode(),
                cid,
                Integer.parseInt(domain.getCode().v()),
                domain.getName().v(),
                sid,
                domain.getStandardFreeClassification().value
        );
    }
}
