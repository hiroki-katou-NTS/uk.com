package nts.uk.ctx.exio.dom.input.canonicalize.domains.organization.workplace;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.Value;
import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceConfiguration;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;

/**
 * 職場マスタの正準化
 */
public class WorkplaceCanonicalization implements DomainCanonicalization {

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 開始日 = 1;
		public static final int 終了日 = 2;
		public static final int 職場コード = 3;
		public static final int 職場外部コード = 4;
		public static final int 職場名称 = 5;
		public static final int 職場表示名 = 6;
		public static final int 職場総称 = 7;
		public static final int 職場階層コード = 8;
		public static final int 職場階層コード1 = 9;
		public static final int 職場階層コード2 = 10;
		public static final int 職場階層コード3 = 11;
		public static final int 職場階層コード4 = 12;
		public static final int 職場階層コード5 = 13;
		public static final int 職場階層コード6 = 14;
		public static final int 職場階層コード7 = 15;
		public static final int 職場階層コード8 = 16;
		public static final int 職場階層コード9 = 17;
		public static final int 職場階層コード10 = 18;
		public static final int 削除フラグ = 19;
		public static final int HIST_ID = 100;
		public static final int 職場ID = 101;
	}

	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		
		// 暫定仕様として、既存データはすべて削除する
		require.save(context, new CompanyTarget(context).toDelete());
		
		val records = require.getAllRevisedDataRecords(context);
		
		if (records.isEmpty()) {
			return;
		}
		
		// 履歴の組み立て
		val histories = buildHistories(require, context, records);
		
		val idMap = WorkplaceIdMap.create(histories.stream()
				.flatMap(h -> h.records.stream())
				.map(r -> r.revised.getItemByNo(Items.職場コード).get().getString())
				.collect(toList()));
		
		histories.forEach(h -> {
			h.canonicalize(require, context, idMap);
		});
	}

	/**
	 * 編集済みデータを履歴の形に組み立てる
	 * @param require
	 * @param context
	 * @param records
	 * @return
	 */
	private static List<HistoryRevisedData> buildHistories(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			List<RevisedDataRecord> records) {
		
		val periods = records.stream()
				.map(r -> Util.getPeriod(r))
				.distinct()
				.sorted(Comparator.comparing(p -> p.start()))
				.collect(toList());
		
		// 期間の重複をチェック
		Set<DatePeriod> duplicatedPeriods = Util.collectDuplicated(periods);

		List<RevisedDataRecord> acceptableRecords = new ArrayList<>();
		for (val record : records) {
			
			val period = Util.getPeriod(record);
			
			if (duplicatedPeriods.contains(period)) {
				require.add(context, ExternalImportError.record(record.getRowNo(), "他の履歴と期間が重複しています。"));
			} else {
				acceptableRecords.add(record);
			}
		}
		
		return HistoryRevisedData.build(require, context, acceptableRecords);
	}
	
	public static interface RequireCanonicalize {
		
		Optional<WorkplaceConfiguration> getWorkplaceConfigurations(String companyId);
		
		List<WorkplaceInformation> getAllWorkplaceInformations(String companyId);
	}

	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return source
				.addItem(this.getItemNameByNo(Items.HIST_ID))
				.addItem(this.getItemNameByNo(Items.職場ID));
	}

	@Override
	public AtomTask adjust(
			DomainCanonicalization.RequireAdjsut require,
			List<AnyRecordToChange> recordsToChange,
			List<AnyRecordToDelete> recordsToDelete) {

		return AtomTask.of(() -> {
			for (val toDelete : recordsToDelete) {
				
				if (CompanyTarget.isCompanyTarget(toDelete)) {
					String companyId = toDelete.getCompanyId();
					require.deleteAllWorkplaceConfigurations(companyId);
					require.deleteAllWorkplaceInformations(companyId);
				}
			}
		});
	}
	
	@Value
	private static class CompanyTarget {
		ExecutionContext context;
		
		static boolean isCompanyTarget(AnyRecordToDelete toDelete) {
			return toDelete.getTargetName().equals("company");
		}
		
		AnyRecordToDelete toDelete() {
			return AnyRecordToDelete.create(context, "company")
					.addKey(0, StringifiedValue.of(context.getCompanyId()));
		}
	}
	
	public static interface RequireAdjust {
		
		void deleteAllWorkplaceConfigurations(String companyId);
		
		void deleteAllWorkplaceInformations(String companyId);
	}

}
