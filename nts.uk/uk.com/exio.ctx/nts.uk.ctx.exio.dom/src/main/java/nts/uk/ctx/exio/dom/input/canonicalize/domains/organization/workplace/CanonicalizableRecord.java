package nts.uk.ctx.exio.dom.input.canonicalize.domains.organization.workplace;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.organization.workplace.WorkplaceCanonicalization.Items;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.util.Either;

/**
 * 各レコードの最終的な正準化を担当するやつ
 */
@AllArgsConstructor
class CanonicalizableRecord {
	
	final IntermediateResult revised;
	
	static CanonicalizableRecord of(RevisedDataRecord record) {
		return new CanonicalizableRecord(IntermediateResult.noChange(record));
	}
	
	int getRowNo() {
		return revised.getRowNo();
	}
	
	Either<ErrorMessage, IntermediateResult> canonicalize(
			String historyId,
			DatePeriod period,
			WorkplaceIdMap idMap) {
		
		String workplaceCode = revised.getItemByNo(Items.職場コード).get().getString();
		String workplaceId = idMap.getId(workplaceCode);
		
		val interm = revised
				.addCanonicalized(CanonicalItem.of(Items.職場ID, workplaceId))
				.addCanonicalized(CanonicalItem.of(Items.HIST_ID, historyId))
				.addCanonicalized(CanonicalItem.of(Items.開始日, period.start()))
				.addCanonicalized(CanonicalItem.of(Items.終了日, period.end()))
				.optionalItem(CanonicalItem.of(Items.削除フラグ, 0));
		
		return this.canonicalizeHierarchyCode()
			.map(hierarCode -> interm.addCanonicalized(CanonicalItem.of(Items.職場階層コード, hierarCode)));
		
	}
	
	private Either<ErrorMessage, String> canonicalizeHierarchyCode() {
		
		List<String> parts = Arrays.asList(
				getCode(Items.職場階層コード1),
				getCode(Items.職場階層コード2),
				getCode(Items.職場階層コード3),
				getCode(Items.職場階層コード4),
				getCode(Items.職場階層コード5),
				getCode(Items.職場階層コード6),
				getCode(Items.職場階層コード7),
				getCode(Items.職場階層コード8),
				getCode(Items.職場階層コード9),
				getCode(Items.職場階層コード10));

		String fullCode = getCode(Items.職場階層コード);
		boolean existsFull = fullCode.length() > 0;
		boolean existsParts = parts.stream().anyMatch(s -> s.length() > 0);

		if (existsParts && existsFull) {
			return Either.left(new ErrorMessage("「階層コード」と「階層コード1～10」は同時に受け入れできません。"));
		}

		if (!existsParts && !existsFull) {
			return Either.left(new ErrorMessage("「階層コード」と「階層コード1～10」のいずれかを受け入れてください。"));
		}
		
		if (existsParts) {
			if (!validateParts(parts)) {
				return Either.left(new ErrorMessage("「階層コード1～10」は、必ず1から順番に受け入れてください。"));
			}
			
			return Either.right(parts.stream().collect(Collectors.joining()));
		}
		
		return Either.right(fullCode);
	}
	
	private static boolean validateParts(List<String> parts) {
		
		if (parts.get(0).equals("")) {
			return false;
		}
		
		int i = 1;
		for (; i < 10; i++) {
			if (parts.get(i).equals("")) break;
		}
		
		for (; i < 10; i++) {
			if (!parts.get(i).equals("")) return false;
		}
		
		return true;
	}

	private String getCode(int itemNo) {
		return revised.getItemByNo(itemNo)
				.map(d -> d.getString())
				.filter(s -> s != null) // 多分文字列でnullは無いと思うけど、念の為チェックしてすべて "" に揃える
				.orElse("");
	}
}
