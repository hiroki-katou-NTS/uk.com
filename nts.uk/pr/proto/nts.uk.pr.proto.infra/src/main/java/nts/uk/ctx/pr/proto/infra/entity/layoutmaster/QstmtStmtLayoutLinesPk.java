package nts.uk.ctx.pr.proto.infra.entity.layoutmaster;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QstmtStmtLayoutLinesPk implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name ="CCD")
	private String companyCd;

	@Basic(optional = false)
	@Column(name ="STMT_CD")
	private String stmtCd;

	@Basic(optional = false)
	@Column(name ="STR_YM")
	private int strYm;

	@Basic(optional = false)
	@Column(name ="CTG_ATR")
	private int ctgAtr;
	
	@Basic(optional = false)
	@Column(name ="AUTO_LINE_ID")
	private String autoLineId;
	

}
