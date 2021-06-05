package nts.uk.ctx.exio.dom.input.revise;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.CodeConvertCode;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.ExternalImportCodeConvert;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

/**
 * 項目の編集
 */
@Getter
@AllArgsConstructor
public class ReviseItem extends AggregateRoot {
	
	/** 会社ID */
	private String companyId;
	
	/** 受入設定コード */
	private ExternalImportCode externalImportCode;
	
	/** 受入項目NO */
	private int importItemNumber;
	
	/** 項目型 */
	private ItemType itemType;
	
	/** 値の編集 */
	private ReviseValue revisingValue;
	
	/** コード変換コード */
	private Optional<CodeConvertCode> codeConvertCode;
	
	/**
	 * 編集する
	 * @param require
	 * @param context
	 * @param importItemNumber
	 * @param targetValue
	 * @return
	 */
	public static RevisedItemResult revise(Require require, ExecutionContext context, int importItemNumber, String targetValue) {
		// 項目の編集の取得
		val revisionist = require.getRevise(context, importItemNumber);
		
		val result = revisionist.revisingValue.revise(targetValue);
		
		if(revisionist.codeConvertCode.isPresent()) {
			// コード変換を実施する場合
			val convertor = require.getCodeConvert(revisionist.codeConvertCode.get());
			val cnvResult = convertor.convert(result.getRevisedvalue().get().toString());
			return new RevisedItemResult(importItemNumber, result, Optional.of(cnvResult));
		}
		return new RevisedItemResult(importItemNumber, result, Optional.empty());
	}
	
	public interface Require{
		ReviseItem getRevise(ExecutionContext context, int importItemNumber);
		ExternalImportCodeConvert getCodeConvert(CodeConvertCode code);
	}
}
