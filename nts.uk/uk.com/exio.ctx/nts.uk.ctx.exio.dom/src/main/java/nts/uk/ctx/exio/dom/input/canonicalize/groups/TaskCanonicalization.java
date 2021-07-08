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
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;

/**
 * 作業の正準化
 * レコード間の整合性制約が無いので、レコード単体で処理を完結できる
 */
@Getter
@ToString
public class TaskCanonicalization implements GroupCanonicalization {

	/** 作業枠Noの項目No */
	final int itemNoTaskFrameNo;
	
	/** 作業コードの項目No */
	final int itemNoTaskCode;
	
	public TaskCanonicalization(GroupWorkspace group) {
		itemNoTaskFrameNo = group.getItemByName("作業枠NO").getItemNo();
		itemNoTaskCode = group.getItemByName("作業コード").getItemNo();
	}
	
	/**
	 * 正準化する
	 * レコード数が多いことが予想されるので、1行ずつ処理する
	 */
	@Override
	public ImportingDataMeta canonicalize(
			GroupCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			ImportingDataMeta meta) {
		
		// 重複チェック用のセット
		Set<UniqueKey> importingKeys = new HashSet<>();
		
		int rowsCount = require.getMaxRowNumberOfRevisedData(context);
		for (int rowNo = 1; rowNo <= rowsCount; rowNo++) {
			
			val revisedDataOpt = require.getRevisedDataRecordByRowNo(context, rowNo);
			if (!revisedDataOpt.isPresent()) {
				continue;
			}
			
			val revisedData = revisedDataOpt.get();
			
			val uniqueKey = new UniqueKey(revisedData);
			if (importingKeys.contains(uniqueKey)) {
				throw new RuntimeException("重複データ" + uniqueKey);
			}
			importingKeys.add(uniqueKey);
			
			canonicalize(require, context, revisedData);
		}
		
		return meta;
	}
	
	private void canonicalize(
			GroupCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			RevisedDataRecord revisedData) {
		
		val key = new UniqueKey(revisedData);
		Optional<Task> existing = require.getTask(context.getCompanyId(), key.frameNo, key.code);
		
		// 受け入れず無視するケース
		if (!context.getMode().canImport(existing.isPresent())) {
			return;
		}
		
		if (context.getMode() == DELETE_RECORD_BEFOREHAND) {
			// 既存データがあれば削除する（DELETE文になるので、実際にデータがあるかどうかのチェックは不要）
			require.save(context, key.toDelete(context));
		}
		
		// データ自体を正準化する必要は無い
		val result = CanonicalizedDataRecord.noChange(revisedData);
		require.save(context, result);
	}

	public static interface RequireCanonicalize {
		
		Optional<Task> getTask(String companyId, int taskFrameNo, String taskCode);
		
	}

	@Override
	public AtomTask adjust(
			RequireAdjsut require,
			List<AnyRecordToChange> recordsToChange,
			List<AnyRecordToDelete> recordsToDelete) {
		
		if (!recordsToChange.isEmpty()) {
			throw new RuntimeException("既存データの変更はありえない");
		}
		
		return AtomTask.of(() -> {
			for (val record : recordsToDelete) {
				val key = new UniqueKey(record);
				require.deleteTask(key.frameNo, key.code);
			}
		});
	}
	
	public static interface RequireAdjust {
		
		void deleteTask(int taskFrameNo, String taskCode);
	}
	
	/**
	 * 作業のユニークキー
	 */
	@RequiredArgsConstructor
	private class UniqueKey {
		
		/** 作業枠NO */
		final int frameNo;
		
		/** 作業コード */
		final String code;
		
		public UniqueKey(RevisedDataRecord revisedData) {
			this(
					(int) (long) (revisedData.getItemByNo(itemNoTaskFrameNo).get().getInt()),
					revisedData.getItemByNo(itemNoTaskCode).get().getString());
		}
		
		public UniqueKey(AnyRecordToDelete record) {
			this(
					record.getKey(itemNoTaskFrameNo).asInteger(),
					record.getKey(itemNoTaskCode).asString());
		}
		
		public AnyRecordToDelete toDelete(ExecutionContext context) {
			return AnyRecordToDelete.create(context)
					.addKey(itemNoTaskFrameNo, StringifiedValue.of(frameNo))
					.addKey(itemNoTaskCode, StringifiedValue.of(code));
		}
	}

	@Override
	public int getItemNoOfEmployeeId() {
		throw new UnsupportedOperationException();
	}
}
