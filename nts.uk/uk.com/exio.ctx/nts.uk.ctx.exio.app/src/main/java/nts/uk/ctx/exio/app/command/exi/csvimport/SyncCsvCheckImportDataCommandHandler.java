package nts.uk.ctx.exio.app.command.exi.csvimport;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.ejb.Stateful;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.ctx.exio.dom.exi.dataformat.DataFormatSetting;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLog;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLogRepository;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategory;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryItem;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryRepository;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateful
public class SyncCsvCheckImportDataCommandHandler extends AsyncCommandHandler<CsvImportDataCommand> {
	@Inject 
	private ExacErrorLogRepository exacErrorLogRepository;
	@Inject
	private StdAcceptCondSetRepository condSetRepos;
	@Inject
	private StdAcceptItemRepository acceptItemRepos;
	@Inject
	private ExternalAcceptCategoryRepository acceptCategoryRepos;
	
	private static final String NUMBER_OF_ERROR = "エラー件数";
	private static final String NUMBER_OF_SUCCESS = "処理カウント";
	private static final String NUMBER_OF_TOTAL = "処理トータルカウント";
	private static final String STOP_MODE = "中断するしない"; // 0 - しない　1-する
	private static final String STATUS = "動作状態"; // 0 - チェック中, 1 - チェック完了, 2 - 受入中, 3 - 完了
	
	@Override
	protected void handle(CommandHandlerContext<CsvImportDataCommand> context) {
		val asyncTask = context.asAsync();
		TaskDataSetter setter = asyncTask.getDataSetter();
		CsvImportDataCommand command = context.getCommand();
		//受入条件取得
		String cid =  AppContexts.user().companyId();
		//ドメインモデル「受入条件設定(定型)」
		Optional<StdAcceptCondSet> optCondSet = condSetRepos.getById(cid, command.getConditionSetCode());
		if(!optCondSet.isPresent() || !optCondSet.get().getCategoryId().isPresent()) return;
		StdAcceptCondSet condSet = optCondSet.get();
		int categoryId = condSet.getCategoryId().get();
		//ドメインモデル「受入項目(定型)」
		List<StdAcceptItem> lstAccSetItem = acceptItemRepos.getListStdAcceptItems(cid, command.getConditionSetCode());
		//ドメインモデル「外部受入カテゴリ項目データ」
		Optional<ExternalAcceptCategory> optAcceptCategory = acceptCategoryRepos.getByCategoryId(categoryId);
		if(!optAcceptCategory.isPresent()) return;
		ExternalAcceptCategory acceptCategory = optAcceptCategory.get();
		List<ExternalAcceptCategoryItem> lstAcceptItem = acceptCategory.getLstAcceptItem();
		
		// アルゴリズム「非同期タスクデータを保存する」を実行する 

		// TODO	Start --- Dump data for testing. they will be deleted after process at phrase 2
		setter.setData(NUMBER_OF_SUCCESS, command.getCurrentLine());
		setter.setData(NUMBER_OF_ERROR, command.getErrorCount());
		setter.setData(NUMBER_OF_TOTAL, command.getCsvLine());
		setter.setData(STOP_MODE, command.getStopMode());
		setter.setData(STATUS, command.getStateBehavior());
		
		for (int i = 1; i <= command.getCsvLine() ; i++) {
			String CSV項目名 = ""; //se gan khi thay doi vong lap
			int CSV項目番号 = 1;  //se gan khi thay doi vong lap
			List<StdAcceptItem> lstAcceptSetItem = lstAccSetItem.stream()
					.filter(x -> x.getCsvItemName().get().equals(CSV項目名) && x.getCsvItemNumber().get() == CSV項目番号)
					.collect(Collectors.toList());
			if(lstAcceptSetItem.isEmpty()) continue;
			StdAcceptItem accSetItem = lstAcceptSetItem.get(0);
			
			List<ExternalAcceptCategoryItem> accItems = lstAcceptItem.stream()
					.filter(x -> x.getCategoryId() == categoryId && x.getItemNo() == accSetItem.getAcceptItemNumber())
					.collect(Collectors.toList());
			if(accItems.isEmpty()) continue;
			ExternalAcceptCategoryItem acceptItem = accItems.get(0);			
			//①　受付条件設定チェック
			boolean condSetItem = true;
			if(accSetItem.getDataFormatSetting().get().getItemType() == ItemType.NUMERIC) {
				
			}
			
			//②　TODO Check primitive value
			
			
			//↓-----------lan truoc
			//TODO アルゴリズム「外部受入テスト本体」を実行する
			if (asyncTask.hasBeenRequestedToCancel()) {
				// 外部受入動作管理の中断するしない区分を更新する
				setter.updateData(STOP_MODE, 1);
				asyncTask.finishedAsCancelled();
				break;
			}			
			if(i %5 == 0){
				exacErrorLogRepository.add(new ExacErrorLog(i, "cid", 
						command.getProcessId(),
						"csvErrorItemName",
						"csvAcceptedValue",
						"errorContents",
						100,
						GeneralDateTime.ymdhms(2018,03,14,10,10,10),
						"ITEM_NAME",
						1));
				setter.updateData(NUMBER_OF_ERROR, i/5);
			}
			setter.updateData(NUMBER_OF_SUCCESS, i);
			setter.updateData(STATUS, command.getStateBehavior());
			
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			//↑---------lan truoc
		}
		// TODO	END --- Dump data for testing. they will be deleted after process at phrase 2
	}

}
