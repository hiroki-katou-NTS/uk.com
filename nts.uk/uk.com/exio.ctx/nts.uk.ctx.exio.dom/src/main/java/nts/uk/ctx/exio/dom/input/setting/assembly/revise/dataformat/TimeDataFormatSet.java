package nts.uk.ctx.exio.dom.input.setting.assembly.revise.dataformat;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.dataformat.value.AcceptedDigit;
import nts.uk.ctx.exio.dom.dataformat.value.DecimalSelection;
import nts.uk.ctx.exio.dom.dataformat.value.DelimiterSetting;
import nts.uk.ctx.exio.dom.dataformat.value.HourlySegment;
import nts.uk.ctx.exio.dom.dataformat.value.TimeRounding;
import nts.uk.ctx.exio.dom.input.validation.user.type.time.ImportingConditionTime;
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
	private Optional<ImportingConditionTime> valueOfFixedValue;

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
			int effectiveDigitLength, int roundProc, int decimalSelect, Integer valueOfFixedValue, Integer startDigit,
			Integer endDigit, Integer roundProcCls) {
		super(itemType);
		this.delimiterSet = EnumAdaptor.valueOf(delimiterSet, DelimiterSetting.class);
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.effectiveDigitLength = EnumAdaptor.valueOf(effectiveDigitLength, NotUseAtr.class);
		this.hourMinSelect = EnumAdaptor.valueOf(hourMinSelect, HourlySegment.class);
		this.roundProc = EnumAdaptor.valueOf(roundProc, NotUseAtr.class);
		this.decimalSelect = EnumAdaptor.valueOf(decimalSelect, DecimalSelection.class);
		this.valueOfFixedValue = Optional.ofNullable(valueOfFixedValue == null ? null : new ImportingConditionTime(valueOfFixedValue));
		this.startDigit = Optional.ofNullable(startDigit == null ? null : new AcceptedDigit(startDigit));
		this.endDigit =  Optional.ofNullable(endDigit == null ? null : new AcceptedDigit(endDigit));
		this.roundProcCls =  Optional.ofNullable(roundProcCls == null ? null : EnumAdaptor.valueOf(roundProcCls, TimeRounding.class));
	}

	/**
	 * 時刻型編集
	 * @param timeValue
	 * @return
	 */
	public Double editTimeValue(String timeValue) {
		Double result = null;
		//固定値使用する/しないを判別
		if(this.fixedValue == NotUseAtr.USE) {
			result = Double.valueOf(this.valueOfFixedValue.get().v());
		}
		//有効桁長あり/なしを判別
		if(this.effectiveDigitLength == NotUseAtr.USE) {
			//「値」から有効桁を切り出し「編集値」とする
			timeValue = timeValue.substring(this.startDigit.get().v(), this.endDigit.get().v());
		}
		//数値のみまたは数値と区切り文字:「.」 or 「：」
		Pattern pattern = Pattern.compile("-?\\d+(\\:\\d+)?\\-?\\d+(\\.\\d+)?");
		Matcher matcher = pattern.matcher(timeValue);  
		boolean matchFound = matcher.matches(); 
		if(!matchFound) {
			return result;
		}
		//60進数/10進数を判別する
		if(this.decimalSelect == DecimalSelection.DECIMAL) {
			//区切り文字を判別する
			Pattern patternColon = Pattern.compile(":");
		    Matcher matcherColon = patternColon.matcher(timeValue);
		    boolean matchFoundColon = matcherColon.find();
		    if(matchFoundColon) {
		    	return result;
		    }
		    //10進数→分に変換（端数処理行わず端数含めてセットする）「編集値」とする
		    result = Double.valueOf(timeValue)*60;
		    //端数処理を使用する/使用しないを判別
		    if(this.roundProc == NotUseAtr.USE) {
		    	if(this.roundProcCls.get() == TimeRounding.DOWN_LESS_1_MINUTE) {
		    		//10進→分に変換（1分未満切り捨て）「編集値」とする
		    		result = Math.floor(result);
		    	} else if (this.roundProcCls.get() == TimeRounding.LESS_1_MINUTE){
		    		result = Math.ceil(result);
		    	} else {
		    		result = Double.valueOf(Math.round(result));
		    	}
		    }
		} else {
			//時分/分を判別する
			if(this.hourMinSelect == HourlySegment.HOUR_MINUTE) {
				//区切り文字（ピリオド、コロン）で上位＝時、下位＝分として分に変換し」「編集値」とする
				String[] stringSepa = timeValue.split(":");
				if(stringSepa.length == 1) {
					stringSepa = timeValue.split(".");
				}
				int hour = Integer.valueOf(stringSepa[0]) * 60;
				result = Double.valueOf(hour + Integer.valueOf(stringSepa[1]));					
			} else {
				try {
					//そのまま分として受け入れる　「編集値」とする
					result = Double.valueOf(timeValue);
				} catch (Exception e) {
					return null;
				}
			}
		}
		return result;
		
	}
}
