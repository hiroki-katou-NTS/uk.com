package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.overtimerestappcommon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *
 * @author loivt
 */
@Entity
@Table(name = "KRQST_OT_REST_APP_COM_SET")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqstOtRestAppComSet extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrqstOtRestAppComSetPK krqstOtRestAppComSetPK;
 
    @Column(name = "DIVERGENCE_REASON_INPUT_ATR")
    private int divergenceReasonInputAtr;
    
    @Column(name = "DIVERGENCE_REASON_FORM_ATR")
    private int divergenceReasonFormAtr;
    
    @Column(name = "DIVERGENCE_REASON_REQUIRED")
    private int divergenceReasonRequired;
    
    @Column(name = "PRE_DISPLAY_ATR")
    private int preDisplayAtr;
    
    @Column(name = "PRE_EXCESS_DISPLAY_SETTING")
    private int preExcessDisplaySetting;
    
    @Column(name = "BONUS_TIME_DISPLAY_ATR")
    private int bonusTimeDisplayAtr;
    
    @Column(name = "OUTING_SETTING_ATR")
    private int outingSettingAtr;
    
    @Column(name = "PERFORMANCE_DISPLAY_ATR")
    private int performanceDisplayAtr;
    
    @Column(name = "PERFORMANCE_EXCESS_ATR")
    private int performanceExcessAtr;
    
    @Column(name = "INTRUCT_DISPLAY_ATR")
    private int intructDisplayAtr;
    
    @Column(name = "EXTRATIME_DISPLAY_ATR")
    private int extratimeDisplayAtr;
    
    @Column(name = "EXTRATIME_EXCESS_ATR")
    private int extratimeExcessAtr;
    
    @Column(name = "APP_DATE_CONTRADICTION_ATR")
    private int appDateContradictionAtr;
    
    @Column(name = "CAL_OVERTIME_DISPLAY_ATR")
    private int calculationOvertimeDisplayAtr;

	@Override
	protected Object getKey() {
		return krqstOtRestAppComSetPK;
	}
  
}
