package nts.uk.ctx.exio.dom.input.canonicalize;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.ExecutionContext;

/**
 * 正準化データのメタ情報
 */
@Value
public class CanonicalizedDataMeta {
	
	/** 受入実行コンテキスト */
	ExecutionContext context;

	/** 受入項目の一覧 */
	List<String> importingItemNames;
}
