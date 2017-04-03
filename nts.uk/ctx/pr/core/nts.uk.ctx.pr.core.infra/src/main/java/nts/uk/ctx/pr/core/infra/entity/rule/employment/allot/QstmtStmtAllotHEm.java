package nts.uk.ctx.pr.core.infra.entity.rule.employment.allot;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.TableEntity;

@Entity
@Table(name = "QSTMT_STMT_ALLOT_H_EM")
@AllArgsConstructor
@NoArgsConstructor
public class QstmtStmtAllotHEm extends TableEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public QstmtStmtAllotHEmPK QstmtStmtAllotHEmPK;
	
	@Basic(optional = false)
	@Column(name = "STR_YM")
	public int startDate;
	
	@Basic(optional = false)
	@Column(name = "END_YM")
	public int endDate;

}
