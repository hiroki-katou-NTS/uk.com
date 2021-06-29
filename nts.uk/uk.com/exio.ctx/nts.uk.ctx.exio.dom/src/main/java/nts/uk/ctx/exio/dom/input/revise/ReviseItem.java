package nts.uk.ctx.exio.dom.input.revise;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.importableitem.ItemType;
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
	public RevisedItemResult revise(Require require, ExecutionContext context, String targetValue) {
		
		val result = this.revisingValue.revise(targetValue);
		
		if(this.codeConvertCode.isPresent()) {
			// コード変換を実施する場合
			val convertor = require.getCodeConvert(this.codeConvertCode.get());
			val cnvResult = convertor.convert(result.getRevisedvalue().get().toString());
			return new RevisedItemResult(importItemNumber, result, Optional.of(cnvResult));
		}
		return new RevisedItemResult(importItemNumber, result, Optional.empty());
	}
	
	public interface Require{
		ExternalImportCodeConvert getCodeConvert(CodeConvertCode code);
	}
}
