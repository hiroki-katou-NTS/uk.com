package nts.uk.ctx.pr.proto.infra.entity.paymentdata;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.layer.infra.data.entity.AggregateTableEntity;

@Entity
@Table(name = "QSTMT_STMT_ALLOT_CP")
public class QstmtStmtAllotCp extends AggregateTableEntity {
	
	@Id
	@Basic(optional = false)
	@Column(name = "CCD")
	public String companyCode;
	
	@Id
	@Basic(optional = false)
	@Column(name = "STR_YM")
	public int startDate;
	
	@Basic(optional = false)
	@Column(name = "END_YM")
	public int endDate;
	
	@Basic(optional = false)
	@Column(name = "PAY_STMT_CD")
	public String paymentDetailCode;
	
	@Basic(optional = false)
	@Column(name = "BONUS_STMT_CD")
	public String bonusDetailCode;
}
