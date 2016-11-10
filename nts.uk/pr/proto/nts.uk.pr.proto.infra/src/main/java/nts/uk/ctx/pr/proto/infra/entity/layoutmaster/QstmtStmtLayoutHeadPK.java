package nts.uk.ctx.pr.proto.infra.entity.layoutmaster;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class QstmtStmtLayoutHeadPK implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Column(name ="CCD")
	public String companyCd;
	
	@Column(name ="STMT_CD")
	public String stmtCd;
	
	@Column(name ="STR_YM")
	public int strYm;
}
