package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import static nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;

/**
 * 日別実績の正準化
 * 社員ごとに処理する
 */
@RequiredArgsConstructor
@Getter
@ToString
public class DailyPerformanceCanonicalization implements GroupCanonicalization {

	/** 社員コードの正準化 */
	final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	/** 年月日の項目No */
	final int itemNoDate;
	
	/**
	 * 正準化する
	 */
	@Override
	public ImportingDataMeta canonicalize(
			GroupCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			ImportingDataMeta meta) {

		List<String> employeeCodes = require.getStringsOfRevisedData(
				context,
				employeeCodeCanonicalization.getItemNoEmployeeCode());
		
		for (String employeeCode : employeeCodes) {
			
			// 重複チェック用のセット
			Set<GeneralDate> importingDates = new HashSet<>();
			
			employeeCodeCanonicalization.canonicalize(require, context, employeeCode).forEach(intermResult -> {

				UniqueKey key = new UniqueKey(intermResult);
				if (importingDates.contains(key.date)) {
					throw new RuntimeException("重複データ" + key);
				}
				importingDates.add(key.date);
				
				canonicalize(require, context, intermResult);
			});
			
		}
		
		return meta.addItem("SID");
	}
	
	private void canonicalize(
			GroupCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			IntermediateResult intermResult) {
		
		UniqueKey key = new UniqueKey(intermResult);
		
		// 既存データの有無チェックは勤務情報だけ見れば良いはず
		val existing = require.getWorkInfoOfDailyPerformance(key.employeeId, key.date);
		
		// 受け入れず無視するケース
		if (context.getMode().canImport(existing.isPresent())) {
			return;
		}
		
		if (context.getMode() == DELETE_RECORD_BEFOREHAND) {
			// 既存データがあれば削除する（DELETE文になるので、実際にデータがあるかどうかのチェックは不要）
			require.save(context, key.toDelete(context));
		}
		
		require.save(context, intermResult.complete());
	}
	
	public static interface RequireCanonicalize {
		
		Optional<WorkInfoOfDailyPerformance> getWorkInfoOfDailyPerformance(String employeeId, GeneralDate date);
		
	}

	/**
	 * 既存データを補正する
	 */
	@Override
	public AtomTask adjust(
			GroupCanonicalization.RequireAdjsut require,
			List<AnyRecordToChange> recordsToChange,
			List<AnyRecordToDelete> recordsToDelete) {

		if (!recordsToChange.isEmpty()) {
			throw new RuntimeException("既存データの変更はありえない");
		}
		
		return AtomTask.of(() -> {
			recordsToDelete.forEach(toDelete -> {
				
				UniqueKey key = new UniqueKey(toDelete);
				require.deleteDailyPerformance(key.employeeId, key.date);
			});
		});
	}

	public static interface RequireAdjust {
		
		/** 日別実績の全Aggregateを削除する */
		void deleteDailyPerformance(String employeeId, GeneralDate date);
	}
	
	private int itemNoEmployeeId() {
		return employeeCodeCanonicalization.getItemNoEmployeeId();
	}
	
	@RequiredArgsConstructor
	private class UniqueKey {
		
		/** 社員ID */
		final String employeeId;
		
		/** 年月日 */
		final GeneralDate date;
		
		UniqueKey(IntermediateResult source) {
			this(
					source.getItemByNo(itemNoEmployeeId()).get().getString(),
					source.getItemByNo(itemNoDate).get().getDate());
		}
		
		UniqueKey(AnyRecordToDelete source) {
			this(
					source.getKey(itemNoEmployeeId()).asString(),
					source.getKey(itemNoDate).asGeneralDate());
		}
		
		AnyRecordToDelete toDelete(ExecutionContext context) {
			return AnyRecordToDelete.create(context)
					.addKey(itemNoEmployeeId(), StringifiedValue.of(employeeId))
					.addKey(itemNoDate, StringifiedValue.of(date));
		}
	}

	@Override
	public int getItemNoOfEmployeeId() {
		return employeeCodeCanonicalization.getItemNoEmployeeId();
	}
}
