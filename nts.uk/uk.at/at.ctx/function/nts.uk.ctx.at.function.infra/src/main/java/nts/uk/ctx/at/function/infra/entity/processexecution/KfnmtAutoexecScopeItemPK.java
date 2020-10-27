package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtAutoexecScopeItemPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/* コード */
	@Column(name = "EXEC_ITEM_CD")
	public String execItemCd;
	
	/* 職場実行範囲 */
	@Column(name = "WKP_ID")
	public String wkpId;
}
