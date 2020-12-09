package nts.uk.ctx.at.request.infra.entity.application.workchange;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQST_APP_WORK_CHANGE_SET")
/**
* 勤務変更申請設定
*/
public class KrqstAppWorkChangeSet_Old extends ContractUkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KrqstAppWorkChangeSetPk_Old appWorkChangeSetPk;
    
    /**
    * スケジュールが休日の場合は除くを表示する
    */
    @Basic(optional = false)
    @Column(name = "EXCLUDE_HOLIDAY")
    public int excludeHoliday;
    
    /**
    * 勤務時間を変更できる
    */
    @Basic(optional = false)
    @Column(name = "WORK_CHANGE_TIME_ATR")
    public int workChangeTimeAtr;
    
    /**
    * 実績を表示する
    */
    @Basic(optional = false)
    @Column(name = "DISPLAY_RESULT_ATR")
    public int displayResultAtr;
    
    /**
    * 勤務時間の初期表示
    */
    @Basic(optional = true)
    @Column(name = "INIT_DISPLAY_WORKTIME")
    public int initDisplayWorktime;
    
    /**
    * コメント
    */
    @Basic(optional = true)
    @Column(name = "COMMENT_CONTENT1")
    public String commentContent1;
    
    /**
    * 太字
    */
    @Basic(optional = true)
    @Column(name = "COMMENT_FONT_WEIGHT1")
    public int commentFontWeight1;
    
    /**
    * 文字色
    */
    @Basic(optional = true)
    @Column(name = "COMMENT_FONT_COLOR1")
    public String commentFontColor1;
    
    /**
    * コメント
    */
    @Basic(optional = true)
    @Column(name = "COMMENT_CONTENT2")
    public String commentContent2;
    
    /**
    * 太字
    */
    @Basic(optional = true)
    @Column(name = "COMMENT_FONT_WEIGHT2")
    public int commentFontWeight2;
    
    /**
    * 文字色
    */
    @Basic(optional = true)
    @Column(name = "COMMENT_FONT_COLOR2")
    public String commentFontColor2;
    
    @Override
    protected Object getKey()
    {
        return appWorkChangeSetPk;
    }
}
