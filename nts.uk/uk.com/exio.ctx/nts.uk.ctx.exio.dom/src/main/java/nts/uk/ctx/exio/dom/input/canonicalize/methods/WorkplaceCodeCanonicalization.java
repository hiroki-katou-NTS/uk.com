package nts.uk.ctx.exio.dom.input.canonicalize.methods;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.RecordError;
import nts.gul.util.Either;

/**
 * 職場コードを職場IDに正準化
 */
@Value
@AllArgsConstructor
public class WorkplaceCodeCanonicalization {

	/**
	 * 渡された編集済みデータを正準化する
	 * @param require
	 * @param interm
	 * @param itemNoReferenceDate
	 * @param itemNoWorkplaceCode
	 * @param itemNoWorkplaceId
	 * @param csvRowNo
	 * @return
	 */
	public Either<RecordError, IntermediateResult> canonicalize(Require require,
			IntermediateResult interm,
			int csvRowNo,
			int itemNoReferenceDate,
			int itemNoWorkplaceCode,
			int itemNoWorkplaceId) {
		String workplaceCode = interm.getItemByNo(itemNoWorkplaceCode).get().getString();
		GeneralDate startDate = interm.getItemByNo(itemNoReferenceDate).get().getDate();

		return getWorkplaceId(require, workplaceCode, startDate, csvRowNo)
				.map(workplaceId -> canonicalize(interm, workplaceId, itemNoWorkplaceId));
		
	}

	private static Either<RecordError, String> getWorkplaceId(Require require, String workplaceCode, GeneralDate startDate, int csvRowNo) {
		val workplace = require.getWorkplaceByCode(workplaceCode, startDate);
		
		return Either.rightOptional(
				workplace.map(e -> e.getWorkplaceId()),
				() -> new RecordError(csvRowNo, "未登録の職場コードです。"));
	}

	private IntermediateResult canonicalize(IntermediateResult canonicalizingData, String workplaceId, int itemNoWorkplaceId) {
		return canonicalizingData.addCanonicalized(
				CanonicalItem.of(itemNoWorkplaceId, workplaceId));
	}

	public static interface Require {
		Optional<WorkplaceInformation> getWorkplaceByCode(String workplaceCode, GeneralDate startdate);
	}
}
