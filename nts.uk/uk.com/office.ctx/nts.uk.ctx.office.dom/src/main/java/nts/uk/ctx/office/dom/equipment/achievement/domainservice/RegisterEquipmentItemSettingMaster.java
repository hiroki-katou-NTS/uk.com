package nts.uk.ctx.office.dom.equipment.achievement.domainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentFormSetting;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentPerformInputFormatSetting;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentUsageRecordItemSetting;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.実績項目設定.設備項目設定マスタを登録する
 * @author NWS-DungDV
 *
 */
public class RegisterEquipmentItemSettingMaster {
	
	private RegisterEquipmentItemSettingMaster() {}
	
	/**
	 * [1] 登録する
	 * @param require
	 * @param cid 会社ID
	 * @param itemSettings 項目設定List
	 * @param format フォーマット設定
	 * @param formSetting 設備帳票設定
	 * @return 永続化処理
	 */
	public static PersistenceProcess register(
		Require require,
		String cid,
		List<EquipmentUsageRecordItemSetting> items,
		Optional<EquipmentPerformInputFormatSetting> format,
		Optional<EquipmentFormSetting> formSetting
	) {
		// 設備利用実績の項目設定 - Start
		List<AtomTask> itemSettingTasks = new ArrayList<AtomTask>();
		List<EquipmentUsageRecordItemSetting> itemSettings = require.getEURItemSettings(cid);
		List<EquipmentItemNo> delItemNo = itemSettings.stream()
			.map(x -> x.getItemNo())
			.collect(Collectors.toList());
		if (!delItemNo.isEmpty()) {
			 itemSettingTasks.add(AtomTask.of(() -> require.deleteAllEURItemSettings(cid, delItemNo)));
		}
		if (!items.isEmpty()) {
			itemSettingTasks.add(AtomTask.of(() -> require.insertAllEURItemSettings(items)));
		}
		// 設備利用実績の項目設定 - End
		
		// 設備の実績入力フォーマット設定 - Start
		List<AtomTask> inputFormatSettingTasks = new ArrayList<AtomTask>();
		Optional<EquipmentPerformInputFormatSetting> inputFormatSettings = require.getEURInputFormatSetting(cid);
		if (inputFormatSettings.isPresent()) {
			inputFormatSettingTasks.add(AtomTask.of(() -> require.deleteEURInputFormatSetting(cid)));
		}
		if (format.isPresent()) {
			inputFormatSettingTasks.add(AtomTask.of(() -> require.insertEURInputFormatSetting(format.get())));
		}
		// 設備の実績入力フォーマット設定 - End
		
		// 設備帳票設定 - Start
		List<AtomTask> formSettingTasks = new ArrayList<AtomTask>();
		Optional<EquipmentFormSetting> formSettingOpt = require.getEquipmentFormSettings(cid);
		if (formSettingOpt.isPresent()) {
			formSettingTasks.add(AtomTask.of(() -> require.deleteEquipmentFormSettings(cid)));
		}
		if (formSetting.isPresent()) {
			formSettingTasks.add(AtomTask.of(() -> require.insertEuipmentFormSettings(formSetting.get())));
		}
		// 設備帳票設定 - End
		return new PersistenceProcess(inputFormatSettingTasks, itemSettingTasks, formSettingTasks);
	}
	
	// Require
	public static interface Require {
		// [R-1]全て設備利用実績の項目設定を取得する
		List<EquipmentUsageRecordItemSetting> getEURItemSettings(String cid);
		
		// [R-2] 設備利用実績の項目設定をInsertAllする
		void insertAllEURItemSettings(List<EquipmentUsageRecordItemSetting> items);
		
		// [R-3] 設備利用実績の項目設定をDeleteAllする
		void deleteAllEURItemSettings(String cid, List<EquipmentItemNo> itemNos);
		
		// [R-4] 設備の実績入力フォーマット設定を取得する
		Optional<EquipmentPerformInputFormatSetting> getEURInputFormatSetting(String cid);
		
		// [R-5]設備の実績入力フォーマット設定をInsert
		void insertEURInputFormatSetting(EquipmentPerformInputFormatSetting item);
		
		// [R-6] 設備の実績入力フォーマット設定をDeleteする
		void deleteEURInputFormatSetting(String cid);
		
		// [R-7]設備帳票設定を取得する
		Optional<EquipmentFormSetting> getEquipmentFormSettings(String cid);
		
		// [R-8] 設備帳票設定をInsertする
		void insertEuipmentFormSettings(EquipmentFormSetting item);
		
		// [R-9] 設備帳票設定をDeleteする
		void deleteEquipmentFormSettings(String cid);
	}
}


