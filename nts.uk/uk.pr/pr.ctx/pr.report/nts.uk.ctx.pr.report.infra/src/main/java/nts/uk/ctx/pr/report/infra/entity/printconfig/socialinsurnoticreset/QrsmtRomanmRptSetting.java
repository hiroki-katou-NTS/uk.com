package nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * ローマ字氏名届作成設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QRSMT_ROMANM_RPT_SETTING")
public class QrsmtRomanmRptSetting extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QrsmtRomanmRptSettingPk romanmRptSettingPk;

    /**
     * 住所出力区分
     */
    @Basic(optional = false)
    @Column(name = "ADD_OUTPUT_ATR")
    public int addOutputAtr;

    @Override
    protected Object getKey()
    {
        return romanmRptSettingPk;
    }

    public RomajiNameNotiCreSetting toDomain() {
        return new RomajiNameNotiCreSetting(this.romanmRptSettingPk.cid, this.romanmRptSettingPk.userId, this.addOutputAtr);
    }
    public static QrsmtRomanmRptSetting toEntity(RomajiNameNotiCreSetting domain) {
        return new QrsmtRomanmRptSetting(new QrsmtRomanmRptSettingPk(domain.getCid(), domain.getUserId()),domain.getAddressOutputClass().value);
    }

}
