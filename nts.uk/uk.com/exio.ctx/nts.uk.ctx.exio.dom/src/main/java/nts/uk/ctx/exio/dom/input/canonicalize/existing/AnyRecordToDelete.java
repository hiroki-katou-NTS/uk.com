package nts.uk.ctx.exio.dom.input.canonicalize.existing;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.ExecutionContext;

/**
 * 任意の既存レコードの単純な削除
 */
@Value
public class AnyRecordToDelete {

	/** 実行コンテキスト */
	ExecutionContext context;
	
	/** 削除対象レコードの主キー値 */
	List<Object> keyValues;
}
