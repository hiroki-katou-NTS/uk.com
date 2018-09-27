package nts.uk.ctx.at.request.infra.entity.application.holidaywork;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertimeDetail;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author loivt
 * 休日出勤申請
 */
@Entity
@Table(name = "KRQDT_APP_HOLIDAY_WORK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppHolidayWork extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrqdtAppHolidayWorkPK krqdtAppHolidayWorkPK;
    
    /**
     * 排他バージョン
     */
    @Version
	@Column(name="EXCLUS_VER")
	public Long version;
    
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
     * 勤務開始時刻
     */
    @Column(name = "WORK_CLOCK_START1")
    private Integer workClockStart1;
    
    /**
     * 勤務終了時刻
     */
    @Column(name = "WORK_CLOCK_END1")
    private Integer workClockEnd1;
    
    /**
     * 直行区分
     */
    @Column(name = "GO_ATR_1")
    private int goAtr1;
    
    /**
     * 直帰区分
     */
    @Column(name = "BACK_ATR_1")
    private int backAtr1;
    
    /**
     * 勤務開始時刻2
     */
    @Column(name = "WORK_CLOCK_START2")
    private Integer workClockStart2;
    
    /**
     * 勤務終了時刻2
     */
    @Column(name = "WORK_CLOCK_END2")
    private Integer workClockEnd2;
    
    /**
     * 直行区分2
     */
    @Column(name = "GO_ATR_2")
    private int goAtr2;
    
    /**
     * 直帰区分2
     */
    @Column(name = "BACK_ATR_2")
    private int backAtr2;
    
    /**
     * 乖離定型理由
     */
    @Column(name = "DIVERGENCE_REASON")
    private String divergenceReason;
    
    /**
     * 就業時間外深夜時間
     */
    @Column(name = "HOLIDAY_SHIFT_NIGHT")
    private Integer holidayShiftNight;
    
    @OneToMany(targetEntity=KrqdtHolidayWorkInput.class, mappedBy="appHolidayWork", cascade = CascadeType.ALL)
    @JoinTable(name = "KRQDT_HOLIDAY_WORK_INPUT")
	public List<KrqdtHolidayWorkInput> holidayWorkInputs;

	@OneToOne(targetEntity = KrqdtAppOvertimeDetail.class, mappedBy = "appHolidayWork", cascade = CascadeType.ALL)
	@JoinTable(name = "KRQDT_APP_OVERTIME_DETAIL")
	public KrqdtAppOvertimeDetail appOvertimeDetail;

	@Override
	protected Object getKey() {
		return krqdtAppHolidayWorkPK;
	}

	public KrqdtAppHolidayWork fromDomainValue(AppHolidayWork appHolidayWork){
		this.version = appHolidayWork.getVersion();
		this.setWorkTypeCode(appHolidayWork.getWorkTypeCode() == null ? null : appHolidayWork.getWorkTypeCode().v());
		this.setWorkTimeCode(appHolidayWork.getWorkTimeCode() == null ? null : appHolidayWork.getWorkTimeCode().v());
		this.setWorkClockStart1(appHolidayWork.getWorkClock1().getStartTime() == null ? null : appHolidayWork.getWorkClock1().getStartTime().v());
		this.setWorkClockEnd1(appHolidayWork.getWorkClock1().getEndTime() == null ? null : appHolidayWork.getWorkClock1().getEndTime().v());
		this.setGoAtr1(appHolidayWork.getWorkClock1().getGoAtr().value);
		this.setBackAtr1(appHolidayWork.getWorkClock1().getBackAtr().value);
		this.setWorkClockStart2(appHolidayWork.getWorkClock2().getStartTime() == null ? null : appHolidayWork.getWorkClock2().getStartTime().v());
		this.setWorkClockEnd2(appHolidayWork.getWorkClock2().getEndTime()== null ? null : appHolidayWork.getWorkClock2().getEndTime().v());
		this.setGoAtr2(appHolidayWork.getWorkClock2().getGoAtr().value);
		this.setBackAtr2(appHolidayWork.getWorkClock2().getBackAtr().value);
		this.setHolidayShiftNight(appHolidayWork.getHolidayShiftNight());
		this.setDivergenceReason(appHolidayWork.getDivergenceReason());
		for(int i = 0; i< appHolidayWork.getHolidayWorkInputs().size(); i++){
			HolidayWorkInput holidayWorkInputInput = appHolidayWork.getHolidayWorkInputs().get(i);
			this.getHolidayWorkInputs().stream().filter(
					x -> x.krqdtHolidayWorkInputPK.getAttendanceType()== holidayWorkInputInput.getAttendanceType().value 
					&& x.krqdtHolidayWorkInputPK.getFrameNo()==holidayWorkInputInput.getFrameNo())
			.findAny()
			.map(x -> {
				x.fromDomainValue(holidayWorkInputInput);
				return Optional.ofNullable(null);
			}).orElseGet(()->{
				KrqdtHolidayWorkInput krqdtOvertimeInput = new KrqdtHolidayWorkInput(
						new KrqdtHolidayWorkInputPK(
								appHolidayWork.getCompanyID(),
								appHolidayWork.getAppID(),
								holidayWorkInputInput.getAttendanceType().value,
								holidayWorkInputInput.getFrameNo()
						), 
						holidayWorkInputInput.getStartTime() == null ? null : holidayWorkInputInput.getStartTime().v(), 
						holidayWorkInputInput.getEndTime() == null ? null : holidayWorkInputInput.getEndTime().v(), 
						holidayWorkInputInput.getApplicationTime().v());
				this.holidayWorkInputs.add(krqdtOvertimeInput);
				return null;
			});
		}
		this.appOvertimeDetail = KrqdtAppOvertimeDetail.toEntity(appHolidayWork.getAppOvertimeDetail());
		return this;
	}
	
	public AppHolidayWork toDomain(){
		AppHolidayWork appHolidayWork = new AppHolidayWork(
				this.krqdtAppHolidayWorkPK.getCid(), 
				this.krqdtAppHolidayWorkPK.getAppId(), 
				this.getWorkTypeCode(), 
				this.getWorkTimeCode(), 
				this.getWorkClockStart1(), 
				this.getWorkClockEnd1(), 
				this.getWorkClockStart2(), 
				this.getWorkClockEnd2(),
				this.goAtr1,
				this.backAtr1,
				this.goAtr2,
				this.backAtr2,
				this.getDivergenceReason(), 
				this.getHolidayShiftNight());
		appHolidayWork.setHolidayWorkInputs(this.holidayWorkInputs.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
		appHolidayWork.setVersion(this.version);
		return appHolidayWork;
	}
    
}
