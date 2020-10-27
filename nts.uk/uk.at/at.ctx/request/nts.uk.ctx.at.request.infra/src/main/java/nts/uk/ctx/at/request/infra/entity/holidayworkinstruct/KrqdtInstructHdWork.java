package nts.uk.ctx.at.request.infra.entity.holidayworkinstruct;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
* 休出指示
* @author loivt
*/
@Entity
@Table(name = "KRQDT_INSTRUCT_HD_WORK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtInstructHdWork extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrqdtInstructHdWorkPK krqdtInstructHdWorkPK;
    
    /**
     * 作業内容
     */
    @Column(name = "WORK_CONTENT")
    private String workContent;
    
    /**
     * 入力日付
     */
    @Column(name = "INPUT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inputDate;
    
    /**
     * 指示者
     */
    @Column(name = "INSTRUCTOR")
    private String instructor;
    
    /**
     * 休出指示理由
     */
    @Column(name = "HOLIDAY_INSTRUCT_REASON")
    private String holidayInstructReason;
    
    /**
     * 休出時間
     */
    @Column(name = "HOLIDAY_WORK_HOUR")
    private int holidayWorkHour;
    
    /**
     * 開始時刻
     */
    @Column(name = "START_CLOCK")
    private int startClock;
    
    /**
     * 終了時刻
     */
    @Column(name = "END_CLOCK")
    private int endClock;

	@Override
	protected Object getKey() {
		return krqdtInstructHdWorkPK;
	}

}
