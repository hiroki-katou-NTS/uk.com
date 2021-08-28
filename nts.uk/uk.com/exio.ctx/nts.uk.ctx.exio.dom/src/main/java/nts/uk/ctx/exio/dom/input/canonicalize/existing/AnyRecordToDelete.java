package nts.uk.ctx.exio.dom.input.canonicalize.existing;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.ExecutionContext;

/**
 * 任意の既存レコードの単純な削除
 */
@Value
public class AnyRecordToDelete {

	/** 会社ID */
	String companyId;
	
	/** 対象データ名（区別不要なら省略可） */
	String targetName;
	
	/**
	 * 削除対象レコードの主キー
	 * Key: 受入項目NO
	 * Value: 主キー値を文字列化したもの
	 */
	List<AnyRecordItem> primaryKeys;
	
	public static AnyRecordToDelete create(ExecutionContext context) {
		return create(context, "");
	}
	
	public static AnyRecordToDelete create(ExecutionContext context, String targetName) {
		return new AnyRecordToDelete(context.getCompanyId(), targetName, new ArrayList<>());
	}
	
	public AnyRecordToDelete addKey(int itemNo, StringifiedValue value) {
		primaryKeys.add(new AnyRecordItem(itemNo, value));
		return this;
	}
	
	public StringifiedValue getKey(int itemNo) {
		return primaryKeys.stream()
				.filter(k -> k.getItemNo() == itemNo)
				.findFirst()
				.get()
				.getValue();
	}
}
