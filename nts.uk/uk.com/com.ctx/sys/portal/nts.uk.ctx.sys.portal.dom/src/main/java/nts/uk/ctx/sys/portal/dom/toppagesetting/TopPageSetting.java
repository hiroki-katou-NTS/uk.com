package nts.uk.ctx.sys.portal.dom.toppagesetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class TopPageSetting
 * 
 * @author sonnh1
 * 
 * 
 */
@Getter
public class TopPageSetting extends AggregateRoot {

	private String companyId;

	private CatelogySetting ctgSet;

	public TopPageSetting(String companyId, CatelogySetting ctgSet) {
		super();
		this.companyId = companyId;
		this.ctgSet = ctgSet;
	}

	/**
	 * Convert to type of TopPageSetting
	 * 
	 * @param companyId
	 * @param ctgSet
	 * @return
	 */
	public static TopPageSetting createFromJavaType(String companyId, int ctgSet) {
		return new TopPageSetting(companyId, EnumAdaptor.valueOf(ctgSet, CatelogySetting.class));
	}
}
