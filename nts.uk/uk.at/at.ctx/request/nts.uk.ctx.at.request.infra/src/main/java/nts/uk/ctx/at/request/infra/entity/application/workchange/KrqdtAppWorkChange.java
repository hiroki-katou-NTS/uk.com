package nts.uk.ctx.at.request.infra.entity.application.workchange;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplication;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 勤務変更申請
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQDT_APP_WORK_CHANGE")
public class KrqdtAppWorkChange extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Version
	@Column(name = "EXCLUS_VER")
	public Long version;
    
    /**
    * ID
    */
    @EmbeddedId
    public KrqdtAppWorkChangePk appWorkChangePk;
        
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
    public int excludeHolidayAtr;
    
    /**
    * 勤務を変更する
    */
    @Basic(optional = false)
    @Column(name = "WORK_CHANGE_ATR")
    public int workChangeAtr;
    
    /**
    * 勤務直行1
    */
    @Basic(optional = false)
    @Column(name = "GO_WORK_ATR1")
    public int goWorkAtr1;
    
    /**
    * 勤務直帰1
    */
    @Basic(optional = false)
    @Column(name = "BACK_HOME_ATR1")
    public int backHomeAtr1;
    
    /**
    * 休憩時間開始1
    */
    @Basic(optional = true)
    @Column(name = "BREAK_TIME_START1")
    public int breakTimeStart1;
    
    /**
    * 休憩時間終了1
    */
    @Basic(optional = true)
    @Column(name = "BREAK_TIME_END1")
    public int breakTimeEnd1;
    
    /**
    * 勤務時間開始1
    */
    @Basic(optional = true)
    @Column(name = "WORK_TIME_START1")
    public int workTimeStart1;
    
    /**
    * 勤務時間終了1
    */
    @Basic(optional = true)
    @Column(name = "WORK_TIME_END1")
    public int workTimeEnd1;
    
    /**
    * 勤務時間開始2
    */
    @Basic(optional = true)
    @Column(name = "WORK_TIME_START2")
    public int workTimeStart2;
    
    /**
    * 勤務時間終了2
    */
    @Basic(optional = true)
    @Column(name = "WORK_TIME_END2")
    public int workTimeEnd2;
    
    /**
    * 勤務直行2
    */
    @Basic(optional = false)
    @Column(name = "GO_WORK_ATR2")
    public int goWorkAtr2;
    
    /**
    * 勤務直帰2
    */
    @Basic(optional = true)
    @Column(name = "BACK_HOME_ATR2")
    public int backHomeAtr2;
    
    @OneToOne(targetEntity=KafdtApplication.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="CID",referencedColumnName="CID"),
		@PrimaryKeyJoinColumn(name="APP_ID",referencedColumnName="APP_ID")
	})
	public KafdtApplication kafdtApplication;
    
    @Override
    protected Object getKey()
    {
        return appWorkChangePk;
    }
}
