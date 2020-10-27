package nts.uk.ctx.sys.portal.infra.entity.toppage.widget;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SptmtOptionalWidgetPK implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** The cid. 会社ID*/
	@Column(name = "CID")
	public String companyID;

	/** TopPage Part GUID */
	@Column(name = "TOPPAGE_PART_ID")
	public String topPagePartID;
}