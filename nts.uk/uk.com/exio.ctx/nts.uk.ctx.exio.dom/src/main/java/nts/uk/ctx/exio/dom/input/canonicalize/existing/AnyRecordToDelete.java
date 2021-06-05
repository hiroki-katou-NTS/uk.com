package nts.uk.ctx.exio.dom.input.canonicalize.existing;

import java.util.HashMap;
import java.util.Map;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.ExecutionContext;

/**
 * 任意の既存レコードの単純な削除
 */
@Value
public class AnyRecordToDelete {

	/** 会社ID */
	String companyId;
	
	/**
	 * 削除対象レコードの主キー
	 * Key: 受入項目NO
	 * Value: 主キー値を文字列化したもの
	 */
	Map<Integer, StringifiedValue> primaryKeys;
	
	public static AnyRecordToDelete create(ExecutionContext context) {
		return new AnyRecordToDelete(context.getCompanyId(), new HashMap<>());
	}
	
	public AnyRecordToDelete addKey(int itemNo, StringifiedValue value) {
		primaryKeys.put(itemNo, value);
		return this;
	}
	
	public StringifiedValue getKey(int itemNo) {
		return primaryKeys.get(itemNo);
	}
}
