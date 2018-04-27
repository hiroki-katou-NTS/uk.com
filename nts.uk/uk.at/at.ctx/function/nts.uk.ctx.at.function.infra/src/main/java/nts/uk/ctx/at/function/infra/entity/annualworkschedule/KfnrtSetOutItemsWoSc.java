package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScCode;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScName;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 年間勤務表（36チェックリスト）の出力項目設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNRT_SET_OUT_ITEMS_WO_SC")
public class KfnrtSetOutItemsWoSc extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KfnrtSetOutItemsWoScPk setOutItemsWoScPk;
    
    /**
    * 名称
    */
    @Basic(optional = false)
    @Column(name = "NAME")
    public String name;
    
    /**
    * 36協定時間を超過した月数を出力する
    */
    @Basic(optional = false)
    @Column(name = "OUT_NUM_EXCEED_TIME_36_AGR")
    public int outNumExceedTime36Agr;
    
    /**
    * 表示形式
    */
    @Basic(optional = false)
    @Column(name = "DISPLAY_FORMAT")
    public int displayFormat;
    
    @Override
    protected Object getKey()
    {
        return setOutItemsWoScPk;
    }

    public SetOutItemsWoSc toDomain() {
        return new SetOutItemsWoSc(this.setOutItemsWoScPk.cid, new OutItemsWoScCode(this.setOutItemsWoScPk.cd),
                                   new OutItemsWoScName(this.name), this.outNumExceedTime36Agr, this.displayFormat);
    }
    public static KfnrtSetOutItemsWoSc toEntity(SetOutItemsWoSc domain) {
        return new KfnrtSetOutItemsWoSc(new KfnrtSetOutItemsWoScPk(domain.getCid(), domain.getCd().v()),
                                        domain.getName().v(), domain.getOutNumExceedTime36Agr(),
                                        domain.getDisplayFormat());
    }

}
