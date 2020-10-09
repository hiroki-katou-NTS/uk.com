package nts.uk.ctx.at.request.infra.entity.application.workchange;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 勤務変更申請
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQDT_APP_WORK_CHANGE_BK")
public class KrqdtAppWorkChange_Old extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Version
	@Column(name = "EXCLUS_VER")
	public Long version;
    
    /**
    * ID
    */
    @EmbeddedId
    public KrqdtAppWorkChangePk_Old appWorkChangePk;
        
    /**
    * 勤務種類コード
    */
    @Basic(optional = false)
    @Column(name = "WORK_TYPE_CD")
    public String workTypeCd;
    
    /**
    * 就業時間帯コード
    */
    @Basic(optional = false)
    @Column(name = "WORK_TIME_CD")
    public String workTimeCd;
    
    /**
    * 休日を除外する
    */
    @Basic(optional = false)
    @Column(name = "EXCLUDE_HOLIDAY_ATR")
    public Integer excludeHolidayAtr;
    
    /**
    * 勤務を変更する
    */
    @Basic(optional = false)
    @Column(name = "WORK_CHANGE_ATR")
    public Integer workChangeAtr;
    
    /**
    * 勤務直行1
    */
    @Basic(optional = false)
    @Column(name = "GO_WORK_ATR1")
    public Integer goWorkAtr1;
    
    /**
    * 勤務直帰1
    */
    @Basic(optional = false)
    @Column(name = "BACK_HOME_ATR1")
    public Integer backHomeAtr1;
    
    /**
    * 休憩時間開始1
    */
    @Basic(optional = true)
    @Column(name = "BREAK_TIME_START1")
    public Integer breakTimeStart1;
    
    /**
    * 休憩時間終了1
    */
    @Basic(optional = true)
    @Column(name = "BREAK_TIME_END1")
    public Integer breakTimeEnd1;
    
    /**
    * 勤務時間開始1
    */
    @Basic(optional = true)
    @Column(name = "WORK_TIME_START1")
    public Integer workTimeStart1;
    
    /**
    * 勤務時間終了1
    */
    @Basic(optional = true)
    @Column(name = "WORK_TIME_END1")
    public Integer workTimeEnd1;
    
    /**
    * 勤務時間開始2
    */
    @Basic(optional = true)
    @Column(name = "WORK_TIME_START2")
    public Integer workTimeStart2;
    
    /**
    * 勤務時間終了2
    */
    @Basic(optional = true)
    @Column(name = "WORK_TIME_END2")
    public Integer workTimeEnd2;
    
    /**
    * 勤務直行2
    */
    @Basic(optional = true)
    @Column(name = "GO_WORK_ATR2")
    public Integer goWorkAtr2;
    
    /**
    * 勤務直帰2
    */
    @Basic(optional = true)
    @Column(name = "BACK_HOME_ATR2")
    public Integer backHomeAtr2;
        
    @Override
    protected Object getKey()
    {
        return appWorkChangePk;
    }
}
