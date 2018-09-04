package nts.uk.ctx.at.request.infra.entity.application.appabsence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRQDT_APP_FOR_LEAVE")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppForLeave extends UkJpaEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected KrqdtAppForLeavePK krqdtAppForLeavePK;
	 /**
     * 排他バージョン
     */
    @Version
	@Column(name="EXCLUS_VER")
	public Long version;
    
    /**
     * 休暇種類
     */
    @Column(name = "HOLIDAY_APP_TYPE")
    private Integer holidayAppType;
    
    /**
     * 勤務種類コード
     */
    @Column(name = "WORKTYPE_CD")
    private String workTypeCode;
    
    /**
     * 就業時間帯
     */
    @Column(name = "WORKTIME_CD")
    private String workTimeCode;
    
    /**
     * 開始時刻1
     */
    @Column(name = "START_TIME1")
    private Integer startTime1;
    
    /**
     * 終了時刻1
     */
    @Column(name = "END_TIME1")
    private Integer endTime1;
    /**
     * 開始時刻2
     */
    @Column(name = "START_TIME2")
    private Integer startTime2;
    
    /**
     * 終了時刻2
     */
    @Column(name = "END_TIME2")
    private Integer endTime2;
    /**
     * 半日の組み合わせを表示する
     */
    @Column(name = "HALF_DAY_FLG")
    private boolean halfDayFlg;
    
    /**
     * 就業時間帯変更する
     */
    @Column(name = "CHANGE_WORK_HOUR")
    private boolean changeWorkHour;
    
    /**
     * 終日半日休暇区分
     */
    @Column(name = "ALLDAY_HALFDAY_LEAVE_ATR")
    private Integer allDayHalfDayLeaveAtr;

	@Override
	protected Object getKey() {
		return krqdtAppForLeavePK;
	}
	
	public AppAbsence toDomain(){
		AppAbsence appAbsene =  new AppAbsence(this.krqdtAppForLeavePK.getCid(),
				this.krqdtAppForLeavePK.getAppId(),
				this.getHolidayAppType(),
				this.getWorkTypeCode(),
				this.getWorkTimeCode(),
				this.isHalfDayFlg(),
				this.isChangeWorkHour(),
				this.getAllDayHalfDayLeaveAtr(),
				this.getStartTime1(),
				this.getEndTime1(), 
				this.getStartTime2(), 
				this.getEndTime2(),
				null);
		appAbsene.setVersion(this.getVersion());
		return appAbsene;
	}

}
