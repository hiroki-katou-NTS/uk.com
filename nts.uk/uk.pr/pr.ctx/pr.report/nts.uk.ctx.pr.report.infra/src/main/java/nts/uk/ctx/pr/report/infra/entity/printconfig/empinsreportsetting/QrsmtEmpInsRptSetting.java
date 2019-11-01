package nts.uk.ctx.pr.report.infra.entity.printconfig.empinsreportsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.report.infra.entity.printconfig.empinsreportsetting.QrsmtEmpInsRptSettingPk;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 雇用保険届作成設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QRSMT_EMP_INS_RPT_SETTING")
public class QrsmtEmpInsRptSetting extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QrsmtEmpInsRptSettingPk empInsRptSettingPk;
    
    /**
    * 提出氏名
    */
    @Basic(optional = false)
    @Column(name = "SUBMIT_NAME_ATR")
    public int submitNameAtr;
    
    /**
    * 出力順
    */
    @Basic(optional = false)
    @Column(name = "OUTPUT_ORDER_ATR")
    public int outputOrderAtr;
    
    /**
    * 事業所区分
    */
    @Basic(optional = false)
    @Column(name = "OFFICE_CLS_ATR")
    public int officeClsAtr;
    
    /**
    * マイナンバー印字区分
    */
    @Basic(optional = false)
    @Column(name = "MY_NUMBER_CLS_ATR")
    public int myNumberClsAtr;
    
    /**
    * 変更後氏名印字区分
    */
    @Basic(optional = false)
    @Column(name = "NAME_CHANGE_CLS_ATR")
    public int nameChangeClsAtr;
    
    @Override
    protected Object getKey()
    {
        return empInsRptSettingPk;
    }

    public EmpInsReportSetting toDomain() {
        return new EmpInsReportSetting(this.empInsRptSettingPk.cid, this.empInsRptSettingPk.userId, this.submitNameAtr, this.outputOrderAtr, this.officeClsAtr, this.myNumberClsAtr, this.nameChangeClsAtr);
    }
    public static QrsmtEmpInsRptSetting toEntity(EmpInsReportSetting domain) {
        return new QrsmtEmpInsRptSetting(new QrsmtEmpInsRptSettingPk(domain.getCid(), domain.getUserId()),domain.getSubmitNameAtr().value, domain.getOutputOrderAtr().value, domain.getOfficeClsAtr().value, domain.getMyNumberClsAtr().value, domain.getNameChangeClsAtr().value);
    }

}
