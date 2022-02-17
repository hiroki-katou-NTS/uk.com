package nts.uk.ctx.sys.portal.dom.toppage;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

/**
 * The Class TopPageNewDto.
 */
@Data
@Builder
public class TopPageDto implements Toppage.MementoSetter, Toppage.MementoGetter {
	
	private String cid;
	
	/** The top page code. */
	private String topPageCode;

	/** The top page name. */
	private String topPageName;

	/** The layout display id. */
	private int layoutDisp;

	/** 枠レイアウト1 */
	private Integer frameLayout1;
	/** 枠レイアウト2 */
	private Integer frameLayout2;
	/** 枠レイアウト3 */
	private Integer frameLayout3;
	

	@Override
	public BigDecimal getLayoutDisp() {
		return BigDecimal.valueOf(this.layoutDisp);
	}

	@Override
	public String getCid() {
		return this.cid;
	}

	@Override
	public void setLayoutDisp(BigDecimal layoutDisp) {
		this.layoutDisp = layoutDisp.intValue();
	}

	@Override
	public void setCid(String cid) {
		this.cid = cid;
	}
}
