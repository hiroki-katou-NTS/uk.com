package nts.uk.ctx.pr.report.infra.entity.printconfig.empinsreportsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 雇用保険届テキスト出力設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QRSMT_EMP_INS_RPT_TXT_STG")
public class QrsmtEmpInsRptTxtSetting extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QrsmtEmpInsRptTxtSettingPk qrsmtEmpInsRptTxtSettingPk;

    /**
     * 事業所区分
     */
    @Basic(optional = false)
    @Column(name = "OFFICE_ATR")
    private int officeAtr;

    /**
     * FD番号
     */
    @Basic(optional = false)
    @Column(name = "FD_NUMBER")
    private String fdNumber;

    /**
     * 改行コード区分
     */
    @Basic(optional = false)
    @Column(name = "LINE_FEED_CODE_ATR")
    private int lineFeedCodeAtr;

    @Override
    protected Object getKey() {
        return qrsmtEmpInsRptTxtSettingPk;
    }

    public EmpInsReportTxtSetting toDomain() {
        return new EmpInsReportTxtSetting(this.qrsmtEmpInsRptTxtSettingPk.cid, this.qrsmtEmpInsRptTxtSettingPk.userId, this.officeAtr, this.fdNumber, this.lineFeedCodeAtr);
    }

    public static QrsmtEmpInsRptTxtSetting toEntity(EmpInsReportTxtSetting domain) {
        return new QrsmtEmpInsRptTxtSetting(new QrsmtEmpInsRptTxtSettingPk(domain.getCid(), domain.getUserId()), domain.getOfficeAtr().value, domain.getFdNumber().v(), domain.getLineFeedCodeAtr().value);
    }
}
