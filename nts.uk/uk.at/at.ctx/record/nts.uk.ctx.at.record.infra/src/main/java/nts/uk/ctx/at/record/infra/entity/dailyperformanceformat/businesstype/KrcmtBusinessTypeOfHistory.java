package nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.businesstype;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 社員の勤務種別の履歴
 * 
 * @author Trung Tran
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_BUS_TYPE_HIST")
public class KrcmtBusinessTypeOfHistory extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtBusinessTypeOfHistoryPK krcmtBusinessTypeOfHistoryPK;

	/** 会社ID */
	@Column(name = "CID")
	public String cID;

	/** 社員ID */
	@Column(name = "SID")
	public String sId;

	/** 開始日 */
	@Column(name = "START_DATE")
	public GeneralDate startDate;

	/** 終了日 */
	@Column(name = "END_DATE")
	public GeneralDate endDate;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return krcmtBusinessTypeOfHistoryPK;
	}

}
