package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.tempabsemce.smile;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.tempabsemce.TempAbsenceHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.tempabsemce.smile.pv.SmileTempAbsenceDataEndDate;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.tempabsemce.smile.pv.SmileTempAbsenceDataReasonCode;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;

public class SmileTempAbsenceHistoryCanonicalization extends TempAbsenceHistoryCanonicalization{
	
	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 社員コード = 1;
		public static final int 開始日 = 2;
		public static final int 終了日 = 3;
		public static final int 休職休業区分 = 4;
		public static final int 備考 = 5;
		public static final int SID = 101;
		public static final int HIST_ID = 102;
		public static final int 社会保険支給対象区分 = 103;
		public static final int 家族メンバーID = 104;
		public static final int 多胎妊娠区分 = 105;
		public static final int 同一家族の休業有無 = 106;
		public static final int 子の区分 = 107;
		public static final int 縁組成立の年月日 = 108;
		public static final int 配偶者が休業しているか = 109;
		public static final int 同一家族の短時間勤務日数 = 110;
		public static final int 開始年月日 = 200;
		public static final int 終了年月日 = 201;
		public static final int 理由コード = 202;
		public static final int 休職理由 = 203;
	}
	
	@Override
	protected List<IntermediateResult> preCanonicalize(List<IntermediateResult> interms) {
		return interms.stream()
				.map(interm -> canonicalize(interm))
				.collect(Collectors.toList());
	}
	
	
	private static IntermediateResult canonicalize(IntermediateResult interm) {

		val items = new CanonicalItemList()
				.add(Items.開始日, getStartDate(interm))
				.add(Items.終了日, getEndDate(interm))
				.add(Items.休職休業区分, getReasonCode(interm));

		if(interm.isImporting(Items.休職理由)) {
			items.add(Items.備考, interm.getItemByNo(Items.休職理由).get().getString());
		}

		return interm.addCanonicalized(items);
	}


	private static long getReasonCode(IntermediateResult interm) {
		SmileTempAbsenceDataReasonCode reasonCode = new SmileTempAbsenceDataReasonCode(interm.getItemByNo(Items.理由コード).get().getString());
		return Long.parseLong(reasonCode.v());
	}

	private static GeneralDate getStartDate(IntermediateResult interm) {
		return interm.getItemByNo(Items.開始年月日).get().getDate();
	}
	
	private static GeneralDate getEndDate(IntermediateResult interm) {
		return new SmileTempAbsenceDataEndDate(interm.getItemByNo(Items.終了年月日).get().getString())
						.getGeneralDate();
	}
	
	@Override
	public ImportingDomainId getTransferDomainId(ExecutionContext context) {
		return ImportingDomainId.TEMP_ABSENCE_HISTORY;
	}

}
