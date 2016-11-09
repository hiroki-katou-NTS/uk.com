package nts.uk.ctx.pr.proto.infra.entity.layoutmaster;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class QstmtStmtLayoutDetailPk implements Serializable{
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
	
	@Column(name ="CTG_ATR")
	public int ctgAtr;
	
	@Column(name ="ITEM_CD")
	public String itemCd;
}
