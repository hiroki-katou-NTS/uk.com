package nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 任意期間集計実行ログ
 * @author phongtq
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_ANP_PERIOD_EXEC")
public class KrcmtAggrPeriodExcution extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtAggrPeriodExcutionPK krcmtAggrPeriodExcutionPK;

	
	@Column(name = "AGGR_FRAME_CODE")
	public String aggrFrameCode;
	
	@Column(name = "START_DATE_TIME")
	public GeneralDateTime startDateTime;
	
	@Column(name = "END_DATE_TIME")
	public GeneralDateTime endDateTime;
	
	@Column(name = "EXECUTION_ATR")
	public int executionAtr;
	
	@Column(name = "EXECUTION_STATUS")
	public int executionStatus;
	
	@Column(name = "PRESENCE_OF_ERROR")
	public int presenceOfError;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return krcmtAggrPeriodExcutionPK;
	}

}
