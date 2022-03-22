package nts.uk.screen.com.app.cmf.cmf001.b.get;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;

@Value
public class ImportDomainDto {

	/** 受入ドメインID */
	private int domainId;

	/** レイアウト項目リスト */
	private List<Integer> itemNoList;
	
	public ImportingDomainId getImportingDomainId() {
		return ImportingDomainId.valueOf(domainId);
	}
	
}
