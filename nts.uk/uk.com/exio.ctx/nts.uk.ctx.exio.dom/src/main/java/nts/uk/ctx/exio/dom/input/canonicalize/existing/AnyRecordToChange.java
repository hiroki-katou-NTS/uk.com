package nts.uk.ctx.exio.dom.input.canonicalize.existing;

import java.util.HashMap;
import java.util.Map;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.ExecutionContext;

@Value
public class AnyRecordToChange implements AnyRecordTo {

	/** 会社ID */
	String companyId;

	/**
	 * 変更対象レコードの主キー
	 * Key: 受入項目NO
	 * Value: 主キー値を文字列化したもの
	 */
	Map<Integer, StringifiedValue> primaryKeys;
	
	/**
	 * 変更対象の項目と値
	 * Key: 受入項目NO
	 * Value: 変更後の値を文字列化したもの
	 */
	Map<Integer, StringifiedValue> changes;
	
	public static AnyRecordToChange create(ExecutionContext context) {
		return new AnyRecordToChange(context.getCompanyId(), new HashMap<>(), new HashMap<>());
	}
	
	public AnyRecordToChange addKey(int itemNo, StringifiedValue value) {
		primaryKeys.put(itemNo, value);
		return this;
	}
	
	public AnyRecordToChange addChange(int itemNo, StringifiedValue value) {
		changes.put(itemNo, value);
		return this;
	}
	
	@Override
	public StringifiedValue getKey(int itemNo) {
		return primaryKeys.get(itemNo);
	}
	
	public StringifiedValue getChange(int itemNo) {
		return changes.get(itemNo);
	}
}
