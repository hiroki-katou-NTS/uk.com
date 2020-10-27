package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQDT_APP_OVERTIME_INPUT")
public class KrqdtAppOvertimeInput extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    protected KrqdtAppOvertimeInputPK krqdtAppOvertimeInputPK;
	
	@Version
	@Column(name="EXCLUS_VER")
	public Long version;
	
	@Column(name = "START_TIME")
    private Integer startTime;
	
    @Column(name = "END_TIME")
    private Integer endTime;
    
    @Column(name = "APPLICATION_TIME")
    private Integer applicationTime;

    @ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name="CID", referencedColumnName="CID"),
    	@PrimaryKeyJoinColumn(name="APP_ID", referencedColumnName="APP_ID")
    })
	public KrqdtAppOvertime appOvertime;
    
    public KrqdtAppOvertimeInput(KrqdtAppOvertimeInputPK pk , Integer startTime, Integer endTime, Integer appTime){
    	this.krqdtAppOvertimeInputPK = pk;
    	this.startTime = startTime;
    	this.endTime = endTime;
    	this.applicationTime = appTime;
    }
	@Override
	protected Object getKey() {
		return krqdtAppOvertimeInputPK;
	}
	
	public KrqdtAppOvertimeInput fromDomainValue(OverTimeInput overTimeInput){
		this.startTime = overTimeInput.getStartTime() == null ? null : overTimeInput.getStartTime().v();
		this.endTime = overTimeInput.getEndTime() == null ? null : overTimeInput.getEndTime().v();
		this.applicationTime = overTimeInput.getApplicationTime() == null ? null : overTimeInput.getApplicationTime().v();
		return this;
	}
	
	public OverTimeInput toDomain(){
		return OverTimeInput.createSimpleFromJavaType(
				this.krqdtAppOvertimeInputPK.getCid(), 
				this.krqdtAppOvertimeInputPK.getAppId(), 
				this.krqdtAppOvertimeInputPK.getAttendanceId(), 
				this.krqdtAppOvertimeInputPK.getFrameNo(), 
				this.startTime, 
				this.endTime, 
				this.applicationTime, 
				this.krqdtAppOvertimeInputPK.getTimeItemTypeAtr());
	} 

}
