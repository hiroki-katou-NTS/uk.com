package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;

import static nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode.*;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;

/**
 * 作業の正準化
 * レコード間の整合性制約が無いので、レコード単体で処理を完結できる
 */
@RequiredArgsConstructor
@Getter
@ToString
public class TaskCanonicalization implements GroupCanonicalization {

	/** 作業枠Noの項目No */
	final int itemNoFrameNo;
	
	/** 作業コードの項目No */
	final int itemNoTaskCode;
	
	/**
	 * 正準化する
	 * レコード数が多いことが予想されるので、1行ずつ処理する
	 */
	@Override
	public void canonicalize(GroupCanonicalization.Require require, ExecutionContext context) {
		
		// 重複チェック用のセット
		Set<UniqueKey> importingKeys = new HashSet<>();
		
		int rowsCount = require.getNumberOfRowsRevisedData();
		for (int rowNo = 0; rowNo < rowsCount; rowNo++) {
			
			val revisedData = require.getRevisedDataRecordByRowNo(context, rowNo);
			
			val uniqueKey = createUniqueKey(revisedData);
			if (importingKeys.contains(uniqueKey)) {
				throw new RuntimeException("重複データ" + uniqueKey);
			}
			
			canonicalize(require, context, revisedData);
		}
	}

	private UniqueKey createUniqueKey(RevisedDataRecord revisedData) {
		
		int taskFrameNo = (int) (long) (revisedData.getItemByNo(itemNoFrameNo).get().getInt());
		String taskCode = revisedData.getItemByNo(itemNoTaskCode).get().getString();
		
		return new UniqueKey(taskFrameNo, taskCode);
	}
	
	/**
	 * 作業のユニークキー
	 */
	@Value
	private static class UniqueKey {
		
		/** 作業枠NO */
		int frameNo;
		
		/** 作業コード */
		String code;
		
		public AnyRecordToDelete toDelete(ExecutionContext context) {
			return new AnyRecordToDelete(context, Arrays.asList(frameNo, code));
		}
	}
	
	private void canonicalize(GroupCanonicalization.Require require, ExecutionContext context, RevisedDataRecord revisedData) {
		
		val key = createUniqueKey(revisedData);
		Optional<Task> existing = require.getTask(context.getCompanyId(), key.frameNo, key.code);
		
		if (context.getMode() == INSERT_ONLY && existing.isPresent()) {
			// 既存データがあるなら受け入れない
			return;
		}
		
		if (context.getMode() == UPDATE_ONLY && !existing.isPresent()) {
			// 既存データが無いなら受け入れない
			return;
		}
		
		if (context.getMode() == DELETE_RECORD_BEFOREHAND) {
			// 既存データがあれば削除する（DELETE文になるので、実際にデータがあるかどうかのチェックは不要）
			require.save(key.toDelete(context));
		}
		
		// データ自体を正準化する必要は無い
		val result = CanonicalizedDataRecord.noChange(context, revisedData);
		require.save(result);
	}

	public static interface Require {
		
		Optional<Task> getTask(String companyId, int taskFrameNo, String taskCode);
		
	}
}
