package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.Comment;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFun;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* 日別実績の修正の機能
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_DAY_CORRECTION_FUN")
public class KrcmtDaiPerformEdFun extends ContractUkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KrcmtDaiPerformEdFunPk daiPerformanceFunPk;
    
    /**
    * コメント
    */
    @Basic(optional = true)
    @Column(name = "COMMENT")
    public String comment;
    
    /**
    * 36協定情報を表示する
    */
    @Basic(optional = false)
    @Column(name = "DISP_36_ATR")
    public int disp36Atr;
    
    /**
    * フレックス勤務者のフレックス不足情報を表示する
    */
    @Basic(optional = false)
    @Column(name = "FLEX_DISP_ATR")
    public int flexDispAtr;
    
    @Basic(optional = false)
    @Column(name = "CHECK_ERR_REF_DISP")
    public int checkErrRefDisp;
    
    @Override
    protected Object getKey()
    {
        return daiPerformanceFunPk;
    }

    public DaiPerformanceFun toDomain() {
        return new DaiPerformanceFun(this.daiPerformanceFunPk.cid, 
				Optional.ofNullable(this.comment == null ? null : new Comment(this.comment)),
        		this.disp36Atr, 
        		this.flexDispAtr, 
        		this.checkErrRefDisp);
    }
    public static KrcmtDaiPerformEdFun toEntity(DaiPerformanceFun domain) {
        return new KrcmtDaiPerformEdFun(new KrcmtDaiPerformEdFunPk(domain.getCid()), 
        		domain.getComment().isPresent() ? domain.getComment().get().toString() : null,
	    		domain.getDisp36Atr(), 
	    		domain.getFlexDispAtr(), 
	    		domain.getCheckErrRefDisp());
    }

}
