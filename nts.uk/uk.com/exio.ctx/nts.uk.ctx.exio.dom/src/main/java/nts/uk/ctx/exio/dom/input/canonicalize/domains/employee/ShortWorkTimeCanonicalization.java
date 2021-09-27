package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.shortworktime.SChildCareFrame;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryKeyColumnNames;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.util.Either;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 短時間勤務履歴の正準化
 */
public class ShortWorkTimeCanonicalization extends EmployeeHistoryCanonicalization {
	
	public ShortWorkTimeCanonicalization(DomainWorkspace w) {
		super(w, HistoryType.UNDUPLICATABLE);
	}
	
	@Override
	protected List<Container> canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			String employeeId,
			List<Container> targetContainers) {
		
		List<Container> canonicalizeds = new ArrayList<>();
		
		for (val container : targetContainers) {
			
			List<SChildCareFrame> slotsToValidate = new ArrayList<>();
			
			Either.<ErrorMessage, IntermediateResult>right(container.getInterm())
				.mapEither(itm -> canonicalizeSlot(1, itm, slotsToValidate)) // 1枠目
				.mapEither(itm -> canonicalizeSlot(2, itm, slotsToValidate)) // 2枠目
				.mapEither(itm -> validate(slotsToValidate, itm))
				.ifRight(itm -> {
					canonicalizeds.add(new Container(itm, container.getAddingHistoryItem()));
				})
				.ifLeft(e -> {
					require.add(ExternalImportError.record(container.getInterm().getRowNo(), e.getText()));
				});
		}
		
		return canonicalizeds;
	}
	
	/**
	 * 1枠分の正準化
	 * @param timeSlot
	 * @param source
	 * @param slotsToValidate
	 * @return
	 */
	private static Either<ErrorMessage, IntermediateResult> canonicalizeSlot(
			int timeSlot,
			IntermediateResult source,
			List<SChildCareFrame> slotsToValidate) {
		
		// 103 or 104
		int itemNoSlot = 102 + timeSlot;
		
		return getSlot(timeSlot, source).map(slotOpt -> slotOpt
				.map(slot -> {
					slotsToValidate.add(slot);
					return source.addCanonicalized(CanonicalItem.of(itemNoSlot, timeSlot));
				})
				.orElse(source));
	}
	
	/**
	 * 指定された枠のSChildCareFrameを中間データから取得する（2枠目は受け入れてないかもしれない）
	 * @param timeSlot
	 * @param source
	 * @return
	 */
	private static Either<ErrorMessage, Optional<SChildCareFrame>> getSlot(int timeSlot, IntermediateResult source) {
		
		Integer startTime;
		Integer endTime;
		
		if (timeSlot == 1) {
			startTime = source.getItemByNo(5).get().getJavaInt();
			endTime = source.getItemByNo(6).get().getJavaInt();
		} else if (timeSlot == 2) {
			startTime = source.getItemByNo(7).map(e -> e.getJavaInt()).orElse(null);
			endTime = source.getItemByNo(8).map(e -> e.getJavaInt()).orElse(null);
		} else {
			throw new RuntimeException("unknown: " + timeSlot);
		}
		
		if (startTime == null && endTime == null) {
			if (timeSlot == 1) {
				throw new RuntimeException("1枠目は必須");
			}
			
			// 受け入れてない
			return Either.right(Optional.empty());
		}

		// SChildCareFrameのドメイン制約チェック
		return Either.tryCatch(
					() -> new SChildCareFrame(timeSlot, new TimeWithDayAttr(startTime), new TimeWithDayAttr(endTime)),
					BusinessException.class)
				.map(ErrorMessage::of, Optional::of);
	}

	/**
	 * ShortWorkTimeHistoryItemのドメイン制約チェック
	 * @param slotsToValidate
	 * @param interm
	 * @return
	 */
	private static Either<ErrorMessage, IntermediateResult> validate(
			List<SChildCareFrame> slotsToValidate,
			IntermediateResult interm) {
		
		try {
			// エラーチェック
			new ShortWorkTimeHistoryItem(null, null, null, slotsToValidate);
			return Either.right(interm);
			
		} catch (BusinessException bex) {
			return Either.left(new ErrorMessage(bex.getMessage()));
		}
	}
	
	@Override
	protected String getParentTableName() {
		return "KSHMT_SHORTTIME_HIST";
	}
	
	@Override
	protected HistoryKeyColumnNames getKeyColumnNames() {
		return new HistoryKeyColumnNames("STR_YMD", "END_YMD");
	}
	
	@Override
	protected List<String> getChildTableNames() {
		return Arrays.asList("KSHMT_SHORTTIME_HIST_ITEM", "KSHMT_SHORTTIME_TS");
	}
	
	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				DomainDataColumn.SID,
				DomainDataColumn.HIST_ID);
	}
}
