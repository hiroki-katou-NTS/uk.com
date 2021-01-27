package nts.uk.ctx.exio.dom.exi.dataformat;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 時間型データ形式設定
 */
@Getter
public class TimeDataFormatSet extends DataFormatSetting {

	/**
	 * 区切り文字設定
	 */
	private DelimiterSetting delimiterSet;

	/**
	 * 固定値
	 */
	private NotUseAtr fixedValue;

	/**
	 * 時分/分選択
	 */
	private HourlySegment hourMinSelect;

	/**
	 * 有効桁長
	 */
	private NotUseAtr effectiveDigitLength;

	/**
	 * 端数処理
	 */
	private NotUseAtr roundProc;

	/**
	 * 進数選択
	 */
	private DecimalSelection decimalSelect;

	/**
	 * 固定値の値
	 */
	private Optional<DataSettingFixedValue> valueOfFixedValue;

	/**
	 * 有効桁数開始桁
	 */
	private Optional<AcceptedDigit> startDigit;

	/**
	 * 有効桁数終了桁
	 */
	private Optional<AcceptedDigit> endDigit;

	/**
	 * 端数処理区分
	 */
	private Optional<TimeRounding> roundProcCls;

	public TimeDataFormatSet(int itemType, int delimiterSet, int fixedValue, int hourMinSelect,
			int effectiveDigitLength, int roundProc, int decimalSelect, String valueOfFixedValue, Integer startDigit,
			Integer endDigit, Integer roundProcCls) {
		super(itemType);
		this.delimiterSet = EnumAdaptor.valueOf(delimiterSet, DelimiterSetting.class);
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.effectiveDigitLength = EnumAdaptor.valueOf(effectiveDigitLength, NotUseAtr.class);
		this.hourMinSelect = EnumAdaptor.valueOf(hourMinSelect, HourlySegment.class);
		this.roundProc = EnumAdaptor.valueOf(roundProc, NotUseAtr.class);
		this.decimalSelect = EnumAdaptor.valueOf(decimalSelect, DecimalSelection.class);
		if (valueOfFixedValue == null)
			this.valueOfFixedValue = Optional.empty();
		else
			this.valueOfFixedValue = Optional.of(new DataSettingFixedValue(valueOfFixedValue));
		if (startDigit == null)
			this.startDigit = Optional.empty();
		else
			this.startDigit = Optional.of(new AcceptedDigit(startDigit));
		if (endDigit == null)
			this.endDigit = Optional.empty();
		else
			this.endDigit = Optional.of(new AcceptedDigit(endDigit));
		if (roundProcCls == null)
			this.roundProcCls = Optional.empty();
		else
			this.roundProcCls = Optional.of(EnumAdaptor.valueOf(roundProcCls, TimeRounding.class));
	}

	/**
	 * 時刻型編集
	 * @param timeValue
	 * @return
	 */
	public Integer editTimeValue(String timeValue) {
		Integer result = null;
		//固定値使用する/しないを判別
		if(this.fixedValue == NotUseAtr.NOT_USE) {
			//有効桁長あり/なしを判別
			if(this.effectiveDigitLength == NotUseAtr.USE) {
				//「値」から有効桁を切り出し「編集値」とする
				timeValue = timeValue.substring(this.startDigit.get().v(), this.endDigit.get().v());
				try {
					//数値のみまたは数値と区切り文字:「.」 or 「：」
					Pattern pattern = Pattern.compile("-?\\d+(\\:\\d+)?\\-?\\d+(\\.\\d+)?");
					Matcher matcher = pattern.matcher(timeValue);  
					boolean matchFound = matcher.matches(); 
					if(!matchFound) {
						return result;
					}
					/*//区切り文字設定を判別する
					if(this.delimiterSet == DelimiterSetting.NO_DELIMITER) {
					    Pattern patternInt = Pattern.compile("[^0-9]");
					    Matcher matcherInt = patternInt.matcher("3012");
					    boolean matchFoundInt = matcherInt.matches();
					    if(matchFoundInt) {
					    	return result;
					    }
					} else (this.delimiterSet == DelimiterSetting.CUT_BY_COLON){
						
					}*/
					
					//60進数/10進数を判別する
					if(this.decimalSelect == DecimalSelection.DECIMAL) {
						//区切り文字を判別する
						
					}
					
				} catch (Exception e) {
					return result;
				}
			}
		}
		
		return result;
	}
}
