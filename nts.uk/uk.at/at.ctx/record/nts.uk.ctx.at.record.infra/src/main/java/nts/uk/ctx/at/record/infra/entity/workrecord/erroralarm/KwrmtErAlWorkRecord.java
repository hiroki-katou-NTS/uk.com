/**
 * 5:09:42 PM Jul 24, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KRCMT_ERAL_SET")
public class KwrmtErAlWorkRecord extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public KwrmtErAlWorkRecordPK kwrmtErAlWorkRecordPK;
	
	@Column(name ="ERROR_ALARM_NAME")
	public String errorAlarmName;
	
	@Column(name ="FIXED_ATR")
	public BigDecimal fixedAtr;
	
	@Column(name ="USE_ATR")
	public BigDecimal useAtr;
	
	@Column(name ="ERAR_ATR")
	public BigDecimal typeAtr;
	
	@Column(name ="MESSAGE_DISPLAY")
	public String messageDisplay;
	
	@Column(name ="BOLD_ATR")
	public BigDecimal boldAtr;
	
	@Column(name ="MESSAGE_COLOR")
	public String messageColor;
	
	@Column(name ="CANCELABLE_ATR")
	public BigDecimal cancelableAtr;
	
	@Column(name ="ERROR_DISPLAY_ITEM")
	public BigDecimal errorDisplayItem;
	
	@Override
	protected Object getKey() {
		return this.kwrmtErAlWorkRecordPK;
	}
	
}
