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
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFun;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 日別実績の修正の機能
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_DAI_CORRECTION_FUN")
public class KrcmtDaiPerformEdFun extends UkJpaEntity implements Serializable
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
    * 1ヵ月の確認・承認が完了した場合、メッセージを表示する
    */
    @Basic(optional = false)
    @Column(name = "MONTH_CHK_MSG_ATR")
    public int monthChkMsgAtr;
    
    /**
    * 36協定情報を表示する
    */
    @Basic(optional = false)
    @Column(name = "DISP_36_ATR")
    public int disp36Atr;
    
    /**
    * クリアした内容は手修正にする
    */
    @Basic(optional = false)
    @Column(name = "CLEARED_MANUAL_ATR")
    public int clearManuAtr;
    
    /**
    * フレックス勤務者のフレックス不足情報を表示する
    */
    @Basic(optional = false)
    @Column(name = "FLEX_DISP_ATR")
    public int flexDispAtr;
    
    /**
    * 休出計算区分を変更する場合、休出深夜計算区分を変更する
    */
    @Basic(optional = false)
    @Column(name = "BREAK_CALC_UPD_ATR")
    public int breakCalcUpdAtr;
    
    /**
    * 休憩時刻を自動で設定する
    */
    @Basic(optional = false)
    @Column(name = "BREAK_TIME_AUTO_SET_ATR")
    public int breakTimeAutoAtr;
    
    /**
    * 早出計算区分を変更する場合、早出残業深夜計算区分を変更する
    */
    @Basic(optional = false)
    @Column(name = "EARLY_CALC_UPD_ATR")
    public int ealyCalcUpdAtr;
    
    /**
    * 残業計算区分を変更する場合、残業深夜区分を変更する
    */
    @Basic(optional = false)
    @Column(name = "OVERTIME_CALC_UPD_ATR")
    public int overtimeCalcUpdAtr;
    
    /**
    * 法定内残業計算区分を変更する場合、法定内深夜残業計算区分を変更する
    */
    @Basic(optional = false)
    @Column(name = "LAW_OVERTIME_CALC_UPD_ATR")
    public int lawOverCalcUpdAtr;
    
    /**
    * 自動で設定した内容は手修正にする
    */
    @Basic(optional = false)
    @Column(name = "MANUAL_FIX_AUTO_SET_ATR")
    public int manualFixAutoSetAtr;
    
    @Basic(optional = false)
    @Column(name = "CHECK_ERR_REF_DISP")
    public int checkErrRefDisp;
    
    @Override
    protected Object getKey()
    {
        return daiPerformanceFunPk;
    }

    public DaiPerformanceFun toDomain() {
        return new DaiPerformanceFun(this.daiPerformanceFunPk.cid, new Comment(this.comment), 
							        		this.monthChkMsgAtr, 
							        		this.disp36Atr, 
							        		this.clearManuAtr, 
							        		this.flexDispAtr, 
							        		this.breakCalcUpdAtr, 
							        		this.breakTimeAutoAtr, 
							        		this.ealyCalcUpdAtr, 
							        		this.overtimeCalcUpdAtr, 
							        		this.lawOverCalcUpdAtr, 
							        		this.manualFixAutoSetAtr,
							        		this.checkErrRefDisp);
    }
    public static KrcmtDaiPerformEdFun toEntity(DaiPerformanceFun domain) {
        return new KrcmtDaiPerformEdFun(new KrcmtDaiPerformEdFunPk(domain.getCid()), domain.getComment().toString(),
						        		domain.getMonthChkMsgAtr(), 
						        		domain.getDisp36Atr(), 
						        		domain.getClearManuAtr(), 
						        		domain.getFlexDispAtr(), 
						        		domain.getBreakCalcUpdAtr(), 
						        		domain.getBreakTimeAutoAtr(), 
						        		domain.getEalyCalcUpdAtr(), 
						        		domain.getOvertimeCalcUpdAtr(), 
						        		domain.getLawOverCalcUpdAtr(), 
						        		domain.getManualFixAutoSetAtr(),
						        		domain.getCheckErrRefDisp());
    }

}
