package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.MasterCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StatementCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 明細書紐付け設定（マスタ）
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_LINK_SET_MAS")
public class QpbmtStateLinkSetMas extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtStateLinkSetMasPk stateLinkSetMasPk;
    
    /**
    * 給与明細書
    */
    @Basic(optional = true)
    @Column(name = "SALARY")
    public String salary;
    
    /**
    * 賞与明細書
    */
    @Basic(optional = true)
    @Column(name = "BONUS")
    public String bonus;
    
    @Override
    protected Object getKey()
    {
        return stateLinkSetMasPk;
    }

    public StateLinkSettingMaster toDomain() {
        return new StateLinkSettingMaster(this.stateLinkSetMasPk.hisId, new MasterCode(this.stateLinkSetMasPk.masterCode), new StatementCode(this.salary), new StatementCode(this.bonus));
    }
    public static QpbmtStateLinkSetMas toEntity(StateLinkSettingMaster domain) {
        return new QpbmtStateLinkSetMas(new QpbmtStateLinkSetMasPk(domain.getHistoryID(), domain.getMasterCode().v()),domain.getSalaryCode().get().v(), domain.getBonusCode().get().v());
    }

    public static List<QpbmtStateLinkSetMas> toEntity(List<StateLinkSettingMaster> domain) {
        List<QpbmtStateLinkSetMas> listStateLinkSettingMaster = new ArrayList<>();
        if(domain.size() > 0){
            listStateLinkSettingMaster = domain.stream().map(item ->{
                return new QpbmtStateLinkSetMas(new QpbmtStateLinkSetMasPk(item.getHistoryID(),
                        item.getMasterCode().v()),
                        item.getSalaryCode().isPresent() ? item.getSalaryCode().get().v() : null,
                        item.getBonusCode().isPresent() ? item.getBonusCode().get().v() : null);
            }).collect(Collectors.toList());
        }
        return listStateLinkSettingMaster;
    }

}
