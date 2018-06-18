package nts.uk.ctx.at.record.infra.entity.monthly.performance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 月別実績の編集状態
 * @author nampt
 *
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KRCDT_EDIT_STATE_MONTH")
public class KrcdtEditStateOfMothlyPer extends UkJpaEntity implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtEditStateOfMothlyPerPK krcdtEditStateOfMothlyPerPK;
	
	// 処理年月
	@Column(name = "YM")
	public int processDate;
	
	// 締めID
	@Column(name = "CLOSURE_ID")
	public int closureID;
	
	/** 締め日 The close day. */
	@Column(name = "CLOSURE_DAY")
	public Integer closeDay;
	
	/** 締め日 The is last day. */
	@Column(name = "IS_LAST_DAY")
	public Integer isLastDay;
	
	/** 編集状態 **/
	@Column(name = "STATE_OF_EDIT")
	public Integer stateOfEdit;
	
	@Override
	protected Object getKey() {
		return this.krcdtEditStateOfMothlyPerPK;
	}

}
