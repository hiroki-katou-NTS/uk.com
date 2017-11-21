package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 残業申請
 * @author loivt
 */
@Entity
@Table(name = "KRQDT_APP_OVERTIME")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppOvertime extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrqdtAppOvertimePK krqdtAppOvertimePK;
    
    @Version
	@Column(name="EXCLUS_VER")
	public Long version;
   
    @Column(name = "OVERTIME_ATR")
    private int overtimeAtr;
    
    @Column(name = "WORK_TYPE_CODE")
    private String workTypeCode;
    
    @Column(name = "SIFT_CODE")
    private String siftCode;
    
    @Column(name = "WORK_CLOCK_FROM1")
    private int workClockFrom1;
    
    @Column(name = "WORK_CLOCK_TO1")
    private int workClockTo1;
    
    @Column(name = "WORK_CLOCK_FROM2")
    private int workClockFrom2;
    
    @Column(name = "WORK_CLOCK_TO2")
    private int workClockTo2;
    
    @Column(name = "DIVERGENCE_REASON")
    private String divergenceReason;
    
    @Column(name = "FLEX_EXCESS_TIME")
    private int flexExcessTime;
    
    @Column(name = "OVERTIME_SHIFT_NIGHT")
    private int overtimeShiftNight;
    
    @OneToMany(mappedBy="appOvertime", cascade = CascadeType.ALL)
	public List<KrqdtOvertimeInput> overtimeInputs;
    
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return krqdtAppOvertimePK;
	}

	public KrqdtAppOvertime fromDomainValue(AppOverTime appOverTime){
		this.version = appOverTime.getVersion();
		this.setWorkTypeCode(appOverTime.getWorkTypeCode().v());
		this.setSiftCode(appOverTime.getSiftCode().v());
		this.setWorkClockFrom1(appOverTime.getWorkClockFrom1());
		this.setWorkClockTo1(appOverTime.getWorkClockTo1());
		this.setWorkClockFrom2(appOverTime.getWorkClockFrom2());
		this.setWorkClockTo2(appOverTime.getWorkClockTo2());
		// krqdtAppOvertime.setOvertimeInputs(krqdtAppOvertime.getOvertimeInputs().stream().);
		this.setOvertimeAtr(appOverTime.getOverTimeAtr().value);
		this.setOvertimeShiftNight(appOverTime.getOverTimeShiftNight());
		this.setFlexExcessTime(appOverTime.getFlexExessTime());
		this.setDivergenceReason(appOverTime.getDivergenceReason());
		for(int i = 0; i<this.getOvertimeInputs().size(); i++){
			this.getOvertimeInputs().get(i).fromDomainValue(appOverTime.getOverTimeInput().get(i));
		}
		return this;
	}
	
	public AppOverTime toDomain(){
		return new AppOverTime(
				this.krqdtAppOvertimePK.getCid(), 
				this.krqdtAppOvertimePK.getAppId(), 
				this.getOvertimeAtr(), 
				this.getWorkTypeCode(), 
				this.getSiftCode(), 
				this.getWorkClockFrom1(), 
				this.getWorkClockTo1(), 
				this.getWorkClockFrom2(), 
				this.getWorkClockTo2(), 
				this.getDivergenceReason(), 
				this.getFlexExcessTime(), 
				this.getOvertimeShiftNight());
	}
    
}

