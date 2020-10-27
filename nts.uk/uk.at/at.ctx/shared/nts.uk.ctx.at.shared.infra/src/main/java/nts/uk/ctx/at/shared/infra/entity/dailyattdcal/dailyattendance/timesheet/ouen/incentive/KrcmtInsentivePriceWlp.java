package nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/** 場所のインセンティブ単価の設定 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KRCMT_INSENTIVE_PRICE_WLP")
public class KrcmtInsentivePriceWlp extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtInsentivePriceWlpPK pk;
	
	/** 作業CD1 */
	@Column(name = "WORK_CD1")
	public String workCd1;
	
	/** 作業CD2 */
	@Column(name = "WORK_CD2")
	public String workCd2;
	
	/** 作業CD3 */
	@Column(name = "WORK_CD3")
	public String workCd3;
	
	/** 作業CD4 */
	@Column(name = "WORK_CD4")
	public String workCd4;
	
	/** 作業CD5 */
	@Column(name = "WORK_CD5")
	public String workCd5;

	@Override
	protected Object getKey() {
		return pk;
	}

}
