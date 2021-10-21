package nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.お気に入り作業.お気に入り作業項目.お気に入り作業を登録する
 * DS: お気に入り作業を登録する
 * 
 * @author tutt
 *
 */
public class RegisterFavoriteTaskService {

	// ■Public
	/**
	 * [1] 追加する
	 * 
	 * @param require    @Require
	 * @param employeeId 社員ID
	 * @param taskName   お気に入り作業名称
	 * @param contents   お気に入り内容
	 * @return
	 */
	public static AtomTask add(Require require, String employeeId, FavoriteTaskName taskName,
			List<TaskContent> contents) {
		List<AtomTask> atomTasks = new ArrayList<>();

		// $既存データ = require.お気に入り作業項目を取得する(社員ID,お気に入り内容)
		List<FavoriteTaskItem> displayOrders = require.getBySameSetting(employeeId, contents);

		// if $既存データ.isPresent
		// BusinessException: Msg_2245 {0}：「$既存データ.名称」を代入する。複数ある場合は「,」繋げる

		if (displayOrders.size() > 0) {

			String nameString = "";
			throw new BusinessException("Msg_2245", nameString);
		}

		// $追加お気に入り = お気に入り作業項目#新規追加(社員ID, 名称, お気に入り内容)
		

		// $表示順 = require.表示順を取得する(社員ID)
		Optional<FavoriteDisplayOrder> displayOrder = require.get(employeeId);

		if (displayOrder.isPresent()) {
			// $表示順.新しいお気に入りの表示順を追加する($追加お気に入り.お気に入りID)

		} else {
			// $新規表示順 = お気に入り作業の表示順#新規追加(社員ID, $追加お気に入り.お気に入りID)

		}

		// return AtomTask:
		return AtomTask.of(() -> {
			// $登録対象.add(require.お気に入りを追加する($追加お気に入り)
//			atomTasks.add(AtomTask.of(() -> require.insert()));
			
			if(true) {
				//	$登録対象.add(require.表示順を追加する($新規表示順)
//				atomTasks.add(AtomTask.of(() -> require.insert()));
				
			}else {
				//	$登録対象.add(require.表示順を更新する($表示順)
//				atomTasks.add(AtomTask.of(() -> require.update(displayOrder.get())));
			}

		});

	}

	// ■Require
	public static interface Require {

		// [R-1] 表示順を取得する
		// お気に入り作業の表示順Repository.Get(社員ID)
		Optional<FavoriteDisplayOrder> get(String employeeId);

		// [R-2] お気に入りを追加する
		// お気に入り作業項目Repository.Insert(お気に入り作業項目)
		void insert(FavoriteTaskItem item);

		// [R-3] 表示順を追加する
		// お気に入り作業の表示順Repository.Insert(お気に入り作業の表示順)
		void insert(FavoriteTaskDisplayOrder order);

		// [R-4] 表示順を更新する
		// お気に入り作業の表示順Repository.Update(お気に入り作業の表示順)
		void update(FavoriteTaskDisplayOrder order);

		// [R-5] お気に入り作業項目を取得する
		// お気に入り作業項目Repository.指定内容が同じお気に入り作業を取得する(社員ID,お気に入り内容)
		List<FavoriteTaskItem> getBySameSetting(String employeeId, List<TaskContent> contents);
	}
}
