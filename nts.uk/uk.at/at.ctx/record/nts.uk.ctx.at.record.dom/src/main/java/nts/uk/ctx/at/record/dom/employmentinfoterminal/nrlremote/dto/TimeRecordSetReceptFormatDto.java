package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ThanhNX
 *
 *         タイムレコード設定受信フォーマットリスト
 */
@Getter
@Setter
public class TimeRecordSetReceptFormatDto {

	// 大項目名称
	private String majorClassification;

	// 小項目名称
	private String smallClassification;

	// 変数名
	private String variableName;

	// 入力タイプ
	private String type;

	// 入力桁数
	private String numberOfDigits;

	// 設定値
	private String settingValue;

	// 入力範囲
	private String inputRange;

	// 再起動フラグ
	private String rebootFlg;

	public TimeRecordSetReceptFormatDto(TimeRecordSetFormatDtoBuilder builder) {
		this.majorClassification = builder.getMajorClassification();
		this.smallClassification = builder.getSmallClassification();
		this.variableName = builder.getVariableName();
		this.type = builder.getType();
		this.numberOfDigits = builder.getNumberOfDigits();
		this.settingValue = builder.getSettingValue();
		this.inputRange = builder.getInputRange();
		this.rebootFlg = builder.getRebootFlg();

	}

	@Getter
	public static class TimeRecordSetFormatDtoBuilder {

		// 大項目名称
		private String majorClassification;

		// 小項目名称
		private String smallClassification;

		// 変数名
		private String variableName;

		// 入力タイプ
		private String type;

		// 入力桁数
		private String numberOfDigits;

		// 設定値
		private String settingValue;

		// 入力範囲
		private String inputRange;

		// 再起動フラグ
		private String rebootFlg;

		public TimeRecordSetFormatDtoBuilder(String majorClassification, String smallClassification,
				String variableName, String type, String numberOfDigits) {
			this.majorClassification = majorClassification;
			this.smallClassification = smallClassification;
			this.variableName = variableName;
			this.type = type;
			this.numberOfDigits = numberOfDigits;
		}

		public TimeRecordSetFormatDtoBuilder settingValue(String settingValue) {
			this.settingValue = settingValue;
			return this;
		}

		public TimeRecordSetFormatDtoBuilder inputRange(String inputRange) {
			this.inputRange = inputRange;
			return this;
		}

		public TimeRecordSetFormatDtoBuilder rebootFlg(String rebootFlg) {
			this.rebootFlg = rebootFlg;
			return this;
		}

		public TimeRecordSetReceptFormatDto build() {
			return new TimeRecordSetReceptFormatDto(this);
		}

	}

}
