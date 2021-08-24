package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.date;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.error.BusinessException;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.util.Either;

/**
 * 日付型編集
 */
@AllArgsConstructor
@Value
public class DateRevise implements ReviseValue {
	
	/** 日付形式 */
	private ExternalImportDateFormat dateFormat;
	
	@Override
	public Either<ErrorMessage, ?> revise(String target) {
		return Either.tryCatch(() -> dateFormat.fromString(target), BusinessException.class)
				.mapLeft(ErrorMessage::of);
	}
}
