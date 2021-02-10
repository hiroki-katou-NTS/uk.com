package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnitType;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* 実績修正画面で利用するフォーマット
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_ATD_USE_FORMAT")
public class KrcmtFormatPerformance extends ContractUkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KrcmtFormatPerformancePk formatPerformancePk;
    
    /**
    * フォーマット種類
    */
    @Basic(optional = false)
    @Column(name = "USE_FORMAT_TYPE")
    public int settingUnitType;
    
    @Override
    protected Object getKey()
    {
        return formatPerformancePk;
    }

    public FormatPerformance toDomain() {
        return new FormatPerformance(this.formatPerformancePk.cid, EnumAdaptor.valueOf(this.settingUnitType, SettingUnitType.class));
    }
    public static KrcmtFormatPerformance toEntity(FormatPerformance domain) {
        return new KrcmtFormatPerformance(new KrcmtFormatPerformancePk(domain.getCid()), domain.getSettingUnitType().value);
    }

}
