package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.Comment;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFun;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* 月別実績の修正の機能
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_MON_CORRECTION_FUN")
public class KrcmtMonPerformanceFun extends ContractUkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KrcmtMonPerformanceFunPk monPerformanceFunPk;
    
    /**
    * コメント
    */
    @Basic(optional = true)
    @Column(name = "COMMENT")
    public String comment;
    
    /**
    * 日別の本人確認を表示する
    */
    @Basic(optional = false)
    @Column(name = "DAILY_SELF_CHK_DISP_ATR")
    public int dailySelfChkDispAtr;
    
    @Override
    protected Object getKey()
    {
        return monPerformanceFunPk;
    }

    public MonPerformanceFun toDomain() {
        return new MonPerformanceFun(this.monPerformanceFunPk.cid, new Comment(comment), this.dailySelfChkDispAtr);
    }
    public static KrcmtMonPerformanceFun toEntity(MonPerformanceFun domain) {
        return new KrcmtMonPerformanceFun(new KrcmtMonPerformanceFunPk(domain.getCid()), domain.getComment().toString(), domain.getDailySelfChkDispAtr());
    }

}
