package nts.uk.ctx.hr.notice.infra.entity.report.registration.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class JhndtReportItemPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "CATEGORY_CD")
	public String ctgCode; // カテゴリコード

	@NotNull
	@Column(name = "ITEM_CD")
	public String itemCd; // 項目コード 

	@NotNull
	@Column(name = "CID")
	public String cid; // 会社ID

	@NotNull
	@Column(name = "RPTID")
	public int reportID; // 届出ID
	
	

}
