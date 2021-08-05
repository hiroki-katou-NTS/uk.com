package nts.uk.ctx.exio.app.input.setting.assembly.revise;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItem;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.codeconvert.CodeConvertDetail;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.codeconvert.ExternalImportCodeConvert;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.date.DateRevise;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.date.ExternalImportDateFormat;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.integer.IntegerRevise;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string.Padding;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string.PaddingLength;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string.PaddingMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string.StringRevise;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time.HourlySegment;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time.TimeBase10Rounding;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time.TimeBase60Delimiter;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time.TimeBaseNumber;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time.TimeRevise;
import nts.uk.shr.com.context.AppContexts;

/**
 * 項目の編集を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReviseItemFinder {
	
	public Optional<ReviseItemDto> find(ExternalImportCode settingCode, int itemNo) {
		
		String companyId = AppContexts.user().companyId();
		
		val dummyValue = dummyValue(itemNo);
		
		if (dummyValue == null) {
			return Optional.empty();
		}
		
		val dummy = new ReviseItem(companyId, settingCode, itemNo, dummyValue(itemNo));
		return Optional.of(ReviseItemDto.of(dummy));
	}
	
	private static ReviseValue dummyValue(int itemNo) {
		
		switch (itemNo) {
		case 1:
			return new StringRevise(
					true,
					Optional.of(new Padding(new PaddingLength(12), PaddingMethod.ZERO_BEFORE)),
					Optional.empty());
		case 2:
			return new DateRevise(ExternalImportDateFormat.YYYY_MM_DD);
		case 3:
			return new TimeRevise(
					HourlySegment.HOUR_MINUTE,
					Optional.of(TimeBaseNumber.SEXAGESIMAL),
					Optional.of(TimeBase60Delimiter.COLON),
					Optional.of(TimeBase10Rounding.ROUND));
		case 4:
			return new IntegerRevise(Optional.of(new ExternalImportCodeConvert(true, Arrays.asList(
					new CodeConvertDetail("A", "001"),
					new CodeConvertDetail("B", "002")
					))));
		default:
			return null;
		}
		
	}
}
