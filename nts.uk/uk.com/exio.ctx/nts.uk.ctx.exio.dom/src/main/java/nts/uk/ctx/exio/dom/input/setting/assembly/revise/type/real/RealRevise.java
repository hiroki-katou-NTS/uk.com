package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.real;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.FetchingPosition;

@AllArgsConstructor
public class RealRevise implements ReviseValue {
	
	/** 値の有効範囲を指定する */
	private boolean useSpecifyRange;
	
	/** 値の有効範囲 */
	private Optional<FetchingPosition> rangeOfValue;
	
	/** 整数値を小数として受け入れる */
	private boolean isDecimalization;
	
	/** 桁数 */
	private Optional<DecimalDigitNumber> length;
	
	
	@Override
	public Object revise(String target) {
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
	private BigDecimal stringToIntToDecimal(String resultStr) {
		return decimalize(Long.parseLong(resultStr));
	}
	
	//　文字列　→　実数変換
	private BigDecimal stringToDecimal(String resultStr) {
		return new BigDecimal(resultStr);
	}
	
	// 小数化
	public BigDecimal decimalize(Long target) {
		BigDecimal result = BigDecimal.valueOf(target);
		// 10のLength乗で割ることで小数化する		
		return result.divide(BigDecimal.valueOf(Math.pow(10, Double.valueOf(length.toString()))));
	}
}
