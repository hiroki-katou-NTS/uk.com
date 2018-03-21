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
@Table(name = "KRCMT_DAI_PERFORM_ED_FUN")
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
    @Column(name = "IS_COMPLETE_CONFIRM_ONE_MONTH")
    public int isCompleteConfirmOneMonth;
    
    /**
    * 36協定情報を表示する
    */
    @Basic(optional = false)
    @Column(name = "IS_DISPLAY_AGREEMENT_THIRTY_SIX")
    public int isDisplayAgreementThirtySix;
    
    /**
    * クリアした内容は手修正にする
    */
    @Basic(optional = false)
    @Column(name = "IS_FIX_CLEARED_CONTENT")
    public int isFixClearedContent;
    
    /**
    * フレックス勤務者のフレックス不足情報を表示する
    */
    @Basic(optional = false)
    @Column(name = "IS_DISPLAY_FLEX_WORKER")
    public int isDisplayFlexWorker;
    
    /**
    * 休出計算区分を変更する場合、休出深夜計算区分を変更する
    */
    @Basic(optional = false)
    @Column(name = "IS_UPDATE_BREAK")
    public int isUpdateBreak;
    
    /**
    * 休憩時刻を自動で設定する
    */
    @Basic(optional = false)
    @Column(name = "IS_SETTING_TIME_BREAK")
    public int isSettingTimeBreak;
    
    /**
    * 休日の場合、出勤/退勤時刻をクリアにする
    */
    @Basic(optional = false)
    @Column(name = "IS_DAY_BREAK")
    public int isDayBreak;
    
    /**
    * 出勤/退勤時刻を自動で設定する
    */
    @Basic(optional = false)
    @Column(name = "IS_SETTING_AUTO_TIME")
    public int isSettingAutoTime;
    
    /**
    * 早出計算区分を変更する場合、早出残業深夜計算区分を変更する
    */
    @Basic(optional = false)
    @Column(name = "IS_UPDATE_EARLY")
    public int isUpdateEarly;
    
    /**
    * 残業計算区分を変更する場合、残業深夜区分を変更する
    */
    @Basic(optional = false)
    @Column(name = "IS_UPDATE_OVERTIME")
    public int isUpdateOvertime;
    
    /**
    * 法定内残業計算区分を変更する場合、法定内深夜残業計算区分を変更する
    */
    @Basic(optional = false)
    @Column(name = "IS_UPDATE_OVERTIME_WITHIN_LEGAL")
    public int isUpdateOvertimeWithinLegal;
    
    /**
    * 自動で設定した内容は手修正にする
    */
    @Basic(optional = false)
    @Column(name = "IS_FIX_CONTENT_AUTO")
    public int isFixContentAuto;
    
    @Override
    protected Object getKey()
    {
        return daiPerformanceFunPk;
    }

    public DaiPerformanceFun toDomain() {
        return new DaiPerformanceFun(this.daiPerformanceFunPk.cid, new Comment(this.comment), this.isCompleteConfirmOneMonth, this.isDisplayAgreementThirtySix, this.isFixClearedContent, this.isDisplayFlexWorker, this.isUpdateBreak, this.isSettingTimeBreak, this.isDayBreak, this.isSettingAutoTime, this.isUpdateEarly, this.isUpdateOvertime, this.isUpdateOvertimeWithinLegal, this.isFixContentAuto);
    }
    public static KrcmtDaiPerformEdFun toEntity(DaiPerformanceFun domain) {
        return new KrcmtDaiPerformEdFun(new KrcmtDaiPerformEdFunPk(domain.getCid()), domain.getComment().toString(), domain.getIsCompleteConfirmOneMonth(), domain.getIsDisplayAgreementThirtySix(), domain.getIsFixClearedContent(), domain.getIsDisplayFlexWorker(), domain.getIsUpdateBreak(), domain.getIsSettingTimeBreak(), domain.getIsDayBreak(), domain.getIsSettingAutoTime(), domain.getIsUpdateEarly(), domain.getIsUpdateOvertime(), domain.getIsUpdateOvertimeWithinLegal(), domain.getIsFixContentAuto());
    }

}
