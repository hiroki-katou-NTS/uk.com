package nts.uk.ctx.exio.dom.input.revise.type.real;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.exio.dom.input.revise.RevisedValueResult;
import nts.uk.ctx.exio.dom.input.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.revise.type.RangeOfValue;

@AllArgsConstructor
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
		String strResult = target;
		if(useSpecifyRange) {
			// 値の有効範囲を指定する場合
			strResult = this.rangeOfValue.get().extract(strResult);
		}
		
		if(isDecimalization) {
			// 整数　→　小数編集する場合
			return stringToIntToDecimal(strResult);
		}
		else {
			// 整数　→　小数編集しない場合
			return stringToDecimal(strResult);
		}
	}
	
	// 文字列　→　整数　→　実数変換
	private RevisedValueResult stringToIntToDecimal(String resultStr) {
		Long intResult;
		try {
			intResult = Long.parseLong(resultStr);
		}
		catch(Exception e){
			return RevisedValueResult.failed("Msg_1017");
		}
		BigDecimal resultDecimal = decimalize(intResult);
		return RevisedValueResult.succeeded(resultDecimal);
	}
	
	//　文字列　→　実数変換
	private RevisedValueResult stringToDecimal(String resultStr) {
		try {
			BigDecimal decimalResult = new BigDecimal(resultStr);
			return RevisedValueResult.succeeded(decimalResult);
		}
		catch(Exception e){
			return RevisedValueResult.failed("Msg_1017");
		}
	}
	
	// 小数化
	public BigDecimal decimalize(Long target) {
		BigDecimal result = BigDecimal.valueOf(target);
		// 10のLength乗で割ることで小数化する		
		return result.divide(BigDecimal.valueOf(Math.pow(10, Double.valueOf(length.toString()))));
	}
}
