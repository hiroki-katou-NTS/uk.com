package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    
}

