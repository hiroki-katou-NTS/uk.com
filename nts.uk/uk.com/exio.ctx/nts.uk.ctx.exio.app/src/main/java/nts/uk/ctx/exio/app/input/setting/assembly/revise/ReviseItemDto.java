package nts.uk.ctx.exio.app.input.setting.assembly.revise;

import lombok.Data;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItem;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.date.DateRevise;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.integer.IntegerRevise;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.real.RealRevise;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string.StringRevise;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time.TimeRevise;

@Value
public class ReviseItemDto {

	private String settingCode;
	private int itemNo;
	private RevisingValue revisingValue;

	public static ReviseItemDto of(ReviseItem domain) {

		return new ReviseItemDto(
				domain.getSettingCode().v(),
				domain.getItemNo(),
				RevisingValue.of(domain.getRevisingValue()));
	}
	
	@Data
	public static class RevisingValue {
		
		// 文字型
		private Boolean usePadding;
		private Integer paddingLength;
		private int paddingMethod = -1;
		
		// 文字型・整数型
		private boolean useCodeConvert;
		private CodeConvertDto codeConvert = CodeConvertDto.empty();
		
		// 実数型
		private Boolean isDecimalization;
		private Integer decimalizationLength;
		
		// 時刻型・時間型
		private int timeHourlySegment = -1;
		private int timeBaseNumber = -1;
		private int timeDelimiter = -1;
		private int timeRounding = -1;
		
		// 日付型
		private int dateFormat = -1;
		
		static RevisingValue of(ReviseValue revisingValue) {
			
			val dto = new RevisingValue();
			
			if (revisingValue instanceof StringRevise) {
				val rev = (StringRevise) revisingValue;
				dto.usePadding = rev.isUsePadding();
				dto.paddingLength = rev.getPadding().get().getLength().v();
				dto.paddingMethod = rev.getPadding().get().getMethod().value;
				rev.getCodeConvert().ifPresent(c -> { dto.codeConvert = CodeConvertDto.of(c); });
				return dto;
			}
			
			if (revisingValue instanceof IntegerRevise) {
				val rev = (IntegerRevise) revisingValue;
				dto.useCodeConvert = rev.getCodeConvert().isPresent();
				rev.getCodeConvert().ifPresent(c -> { dto.codeConvert = CodeConvertDto.of(c); });
				return dto;
			}
			
			if (revisingValue instanceof RealRevise) {
				val rev = (RealRevise) revisingValue;
				dto.isDecimalization = rev.isDecimalization();
				dto.decimalizationLength = rev.getLength().map(l -> l.v()).orElse(null);
				return dto;
			}
			
			if (revisingValue instanceof TimeRevise) {
				val rev = (TimeRevise) revisingValue;
				dto.timeHourlySegment = rev.getHourly().value;
				dto.timeBaseNumber = rev.getBaseNumber().value;
				dto.timeDelimiter = rev.getDelimiter().map(d -> d.value).orElse(-1);
				dto.timeRounding = rev.getRounding().value;
				return dto;
			}
			
			if (revisingValue instanceof DateRevise) {
				val rev = (DateRevise) revisingValue;
				dto.dateFormat = rev.getDateFormat().value;
				return dto;
			}
			
			throw new RuntimeException("unknown: " + revisingValue);
		}
	}
}
