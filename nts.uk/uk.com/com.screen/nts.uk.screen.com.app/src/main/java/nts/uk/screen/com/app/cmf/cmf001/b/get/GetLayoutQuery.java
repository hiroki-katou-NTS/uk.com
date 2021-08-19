package nts.uk.screen.com.app.cmf.cmf001.b.get;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

public class GetLayoutQuery {
	
	/** 受入設定コード */
	private String settingCode;
	
	/** 受入グループID */
	private int importingDomainId;
	
	/** 項目NO一覧 */
	@Getter
	private List<Integer> itemNoList;
	
	public ExternalImportCode getSettingCode() {
		return new ExternalImportCode(settingCode);
	}
	
	public ImportingDomainId getImportingDomainId() {
		return EnumAdaptor.valueOf(importingDomainId, ImportingDomainId.class);
	}
	
	public boolean isAllItem() {
		return this.itemNoList.isEmpty();
	}
	
}