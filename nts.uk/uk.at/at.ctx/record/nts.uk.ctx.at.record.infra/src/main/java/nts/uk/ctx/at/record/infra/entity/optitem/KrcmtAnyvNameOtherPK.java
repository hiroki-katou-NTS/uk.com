package nts.uk.ctx.at.record.infra.entity.optitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrcmtAnyvNameOtherPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	public String cid;

	/** The optional item no. */
	@Column(name = "OPTIONAL_ITEM_NO")
	public Integer optionalItemNo;

	/* 言語ID */
	@Column(name = "LANG_ID")
	public String langId;

}
