package nts.uk.ctx.exio.infra.entity.input.workspace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class XimctWorkspaceItemPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/* 受入グループID */
	@Column(name = "DOMAIN_ID")
	public int domainId;
	
	/* 受入項目NO */
	@Column(name = "ITEM_NO")
	public int itemNo;
}
