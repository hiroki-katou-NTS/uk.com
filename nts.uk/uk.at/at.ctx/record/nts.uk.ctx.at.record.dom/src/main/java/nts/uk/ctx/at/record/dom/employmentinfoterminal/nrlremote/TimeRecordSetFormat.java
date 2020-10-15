package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import lombok.Getter;

/**
 * @author ThanhNX
 *
 *         タイムレコード設定フォーマット
 */
@Getter
public class TimeRecordSetFormat {

	// 大項目名称
	private final MajorNameClassification majorClassification;

	// 小項目名称
	private final MajorNameClassification smallClassification;

	// 変数名
	private final VariableName variableName;

	// 入力タイプ
	private final NrlRemoteInputType type;

	// 入力桁数
	private final NumberOfDigits numberOfDigits;

	// 設定値
	private final SettingValue settingValue;

	// 入力範囲
	private final NrlRemoteInputRange inputRange;

	// 再起動フラグ
	private final boolean rebootFlg;

	// 現在の値
	private final SettingValue currentValue;

	public TimeRecordSetFormat(TimeRecordSetFormatBuilder builder) {
		this.majorClassification = builder.getMajorClassification();
		this.smallClassification = builder.getSmallClassification();
		this.variableName = builder.getVariableName();
		this.type = builder.getType();
		this.numberOfDigits = builder.getNumberOfDigits();
		this.settingValue = builder.getSettingValue();
		this.inputRange = builder.getInputRange();
		this.rebootFlg = builder.isRebootFlg();
		this.currentValue = builder.getCurrentValue();

	}

	@Getter
	public static class TimeRecordSetFormatBuilder {

		// 大項目名称
		private MajorNameClassification majorClassification;

		// 小項目名称
		private MajorNameClassification smallClassification;

		// 変数名
		private VariableName variableName;

		// 入力タイプ
		private NrlRemoteInputType type;

		// 入力桁数
		private NumberOfDigits numberOfDigits;

		// 設定値
		private SettingValue settingValue;

		// 入力範囲
		private NrlRemoteInputRange inputRange;

		// 再起動フラグ
		private boolean rebootFlg;

		// 現在の値
		private SettingValue currentValue;

		public TimeRecordSetFormatBuilder(MajorNameClassification majorClassification,
				MajorNameClassification smallClassification, VariableName variableName, NrlRemoteInputType type,
				NumberOfDigits numberOfDigits) {
			this.majorClassification = majorClassification;
			this.smallClassification = smallClassification;
			this.variableName = variableName;
			this.type = type;
			this.numberOfDigits = numberOfDigits;
		}

		public TimeRecordSetFormatBuilder settingValue(SettingValue settingValue) {
			this.settingValue = settingValue;
			return this;
		}

		public TimeRecordSetFormatBuilder inputRange(NrlRemoteInputRange inputRange) {
			this.inputRange = inputRange;
			return this;
		}

		public TimeRecordSetFormatBuilder rebootFlg(boolean rebootFlg) {
			this.rebootFlg = rebootFlg;
			return this;
		}

		public TimeRecordSetFormatBuilder value(SettingValue currentValue) {
			this.currentValue = currentValue;
			return this;
		}

		public TimeRecordSetFormat build() {
			return new TimeRecordSetFormat(this);
		}

	}

}
