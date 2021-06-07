package nts.uk.ctx.exio.dom.input.revise.type.real;

import java.math.BigDecimal;
import java.util.Optional;

import nts.uk.ctx.exio.dom.input.revise.RevisedValueResult;
import nts.uk.ctx.exio.dom.dataformat.value.DecimalDigitNumber;
import nts.uk.ctx.exio.dom.input.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.revise.type.RangeOfValue;

public class RealRevise implements ReviseValue {
	
	/** 値の有効範囲を指定する */
	private boolean useSpecifyRange;
	
	/** 値の有効範囲 */
	private Optional<RangeOfValue> rangeOfValue;
	
	/** 整数値を小数として受け入れる */
	private boolean isDecimalization;
	
	/** 桁数 */
	private Optional<DecimalDigitNumber> length;
	
	
	@Override
	public RevisedValueResult revise(String target) {
		String resultStr = target;
		if(useSpecifyRange) {
			// 値の有効範囲を指定する場合
			resultStr = this.rangeOfValue.get().extract(target);
		}
		
		if(isDecimalization) {
			// 整数　→　小数編集する場合
			return stringToIntToDecimal(resultStr);
		}
		else {
			// 整数　→　小数編集しない場合
			return stringToDecimal(resultStr);
		}
	}
	
	// 文字列　→　整数　→　実数変換
	private RevisedValueResult stringToIntToDecimal(String resultStr) {
		Long resultInt;
		try {
			resultInt = Long.parseLong(resultStr);
		}
		catch(Exception e){
			return RevisedValueResult.failed("Msg_1017");
		}
		BigDecimal resultDecimal = decimalize(resultInt);
		return RevisedValueResult.succeeded(resultDecimal);
	}
	
	//　文字列　→　実数変換
	private RevisedValueResult stringToDecimal(String resultStr) {
		try {
			BigDecimal resultDecimal = new BigDecimal(resultStr);
			return RevisedValueResult.succeeded(resultDecimal);
		}
		catch(Exception e){
			return RevisedValueResult.failed("Msg_1017");
		}
	}
	
	// 小数化
	public BigDecimal decimalize(Long target) {
		BigDecimal result = BigDecimal.valueOf(target);
		// 10のLength乗することで小数化する		
		return result.divide(BigDecimal.valueOf(Math.pow(10, Double.valueOf(length.toString()))));
	}
}
