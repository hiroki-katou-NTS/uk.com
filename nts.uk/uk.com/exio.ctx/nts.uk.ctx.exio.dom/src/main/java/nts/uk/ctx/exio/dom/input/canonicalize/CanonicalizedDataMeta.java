package nts.uk.ctx.exio.dom.input.canonicalize;

import java.util.List;

import lombok.Value;

/**
 * 正準化データのメタ情報
 */
@Value
public class CanonicalizedDataMeta {
	
	/** 会社ID */
	String companyId;

	/** 受入項目の一覧 */
	List<Integer> importingItemNos;
}
