package nts.uk.ctx.sys.portal.app.find.toppage;

import java.math.BigDecimal;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.toppage.Toppage;

/**
 * The Class TopPageNewDto.
 */
@Data
public class TopPageNewDto implements Toppage.MementoSetter, Toppage.MementoGetter {
	
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

	/**
	 * From domain.
	 *
	 * @param topPage the top page
	 * @param lstPlacement the lst placement
	 * @param lstTopPagePart the lst top page part
	 * @return the top page dto
	 */
	public static TopPageNewDto fromDomain(Toppage topPage) {
		TopPageNewDto topPageDto = new TopPageNewDto();
		topPage.setMemento(topPageDto);
		return topPageDto;
	}

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
