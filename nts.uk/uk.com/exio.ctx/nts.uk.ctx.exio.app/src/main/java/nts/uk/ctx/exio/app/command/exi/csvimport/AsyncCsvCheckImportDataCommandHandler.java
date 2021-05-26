package nts.uk.ctx.exio.app.command.exi.csvimport;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateful;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvert;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvertRepository;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.ctx.exio.dom.exi.csvimport.RequiredMasterDataNotFoundException;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLog;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLogManager;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLogRepository;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLog;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository;
import nts.uk.ctx.exio.dom.exi.execlog.ExtResultStatus;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategory;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryItem;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryRepository;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialEditValue;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItemRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.SpecialExternalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.specialedit.SpecialEdit;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecordImpoter;
import nts.uk.ctx.exio.dom.input.editvalue.CsvItemImport;
import nts.uk.ctx.exio.dom.input.editvalue.ItemCheck;
import nts.uk.shr.com.context.AppContexts;

@Stateful
public class AsyncCsvCheckImportDataCommandHandler extends AsyncCommandHandler<CsvImportDataCommand> {
	@Inject 
	private ExacErrorLogRepository exacErrorLogRepository;
	@Inject
	private StdAcceptCondSetRepository condSetRepos;
	@Inject
	private StdAcceptItemRepository acceptItemRepos;
	@Inject
	private ExternalAcceptCategoryRepository acceptCategoryRepos;
	@Inject
	private AcceptCdConvertRepository cdConvertRepos;
	@Inject
	protected StoredFileStreamService fileStreamService;
	@Inject
	private ExacExeResultLogRepository exResultLog;
	
	private static final String NUMBER_OF_ERROR = "エラー件数";
	private static final String NUMBER_OF_SUCCESS = "処理カウント";
	private static final String NUMBER_OF_TOTAL = "処理トータルカウント";
	private static final String STOP_MODE = "中断するしない"; // 0 - しない　1-する
	private static final String STATUS = "動作状態"; // 0 - チェック中, 1 - チェック完了, 2 - 受入中, 3 - 完了
	
	@Override
	protected void handle(CommandHandlerContext<CsvImportDataCommand> context) {
		val asyncTask = context.asAsync();
		// アルゴリズム「非同期タスクデータを保存する」を実行する 
		TaskDataSetter setter = asyncTask.getDataSetter();
		CsvImportDataCommand command = context.getCommand();
		setter.setData(NUMBER_OF_SUCCESS, command.getCurrentLine());
		setter.setData(NUMBER_OF_ERROR, command.getErrorCount());
		setter.setData(NUMBER_OF_TOTAL, command.getCsvLine());
		setter.setData(STOP_MODE, command.getStopMode());
		setter.setData(STATUS, command.getStateBehavior());
		//受入条件取得
		String cid =  AppContexts.user().companyId();
		String conditionSetCd = command.getConditionSetCode();
		
		//ドメインモデル「受入条件設定(定型)」
		Optional<StdAcceptCondSet> optCondSet = condSetRepos.getById(cid, conditionSetCd);
		if(!optCondSet.isPresent() || !optCondSet.get().getCategoryId().isPresent()) return;
		StdAcceptCondSet condSet = optCondSet.get();
		int categoryId = condSet.getCategoryId().get();
		
		val logManager = new ExacErrorLogManager(cid, command.getProcessId());

		val require = new RequireImpl(command.getCsvFileId(), cid, conditionSetCd, categoryId, logManager);
		
		ExacExeResultLog resultLog = new ExacExeResultLog();
		int errorCount = 0;
		int successCount = 0;
		try {
			Optional<ExacExeResultLog> optResultLog = exResultLog.getExacExeResultLogById(cid, conditionSetCd, command.getProcessId());
			if(optResultLog.isPresent()) resultLog = optResultLog.get();
			
			List<CsvRecord> csvRecords;
			try {
				csvRecords = condSet.getCsvRecordImpoter().read(require);
			}
			catch (RequiredMasterDataNotFoundException ex){
				setter.updateData(NUMBER_OF_SUCCESS, command.getCsvLine());
				setter.updateData(STATUS, command.getStateBehavior());
				return;
			}
			List<Map<Integer, Object>> lstCsvContent = new ArrayList<>();
			for (CsvRecord csvRecord : csvRecords) { // line
				//アルゴリズム「受入項目チェック＆編集」を実行する
				CsvItemImport csvItemImporter = new CsvItemImport(csvRecord, csvRecords.indexOf(csvRecord) + 1,  condSet.getAcceptMode());
				ItemCheck checkAndEditItemOfLine = csvItemImporter.readLine(require);
				
				if(!checkAndEditItemOfLine.isLineError()) {
					errorCount += 1; 
					setter.updateData(NUMBER_OF_ERROR, errorCount);
				} else {
					successCount += 1;
					if(checkAndEditItemOfLine.isCond()) {
						//TODO insert vao db	
						lstCsvContent.add(checkAndEditItemOfLine.getMapLineContent());
					}			
					setter.updateData(NUMBER_OF_SUCCESS, successCount);
				}
							
				//↓-----------lan truoc
				if (asyncTask.hasBeenRequestedToCancel()) {
					// 外部受入動作管理の中断するしない区分を更新する
					setter.updateData(STOP_MODE, 1);
					asyncTask.finishedAsCancelled();
					resultLog.setResultStatus(Optional.of(ExtResultStatus.BREAK));
					break;
				}
				setter.updateData(STATUS, command.getStateBehavior());
				resultLog.setResultStatus(Optional.of(ExtResultStatus.SUCCESS));
				//↑---------lan truoc
			} // line

			logManager.save(new ExacErrorLogManager.Require(){
				@Override
				public void save(List<ExacErrorLog> logs) {
					exacErrorLogRepository.addList(logs);
				}
			});
			resultLog.setErrorCount(errorCount);
			resultLog.setProcessEndDatetime(Optional.ofNullable(GeneralDateTime.now()));
			exResultLog.update(resultLog);
		} catch (IOException e) {
			resultLog.setResultStatus(Optional.of(ExtResultStatus.FAILURE));
			resultLog.setErrorCount(errorCount);
			exResultLog.update(resultLog);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public class RequireImpl implements CsvRecordImpoter.Require {
		private String fileId;
		private Map<SpecialExternalItem, SpecialEditValue> mapItemSpecial;
		private String cid;
		String conditionSetCd;
		private int categoryId;
		ExacErrorLogManager logManager;

		List<StdAcceptItem> listStdAcceptItems;
		Optional<ExternalAcceptCategory> acceptCategory;
		List<AcceptCdConvert> acceptCdConvert;
		
		public RequireImpl(String fileId, String cid, String conditionSetCd, int categoryId, ExacErrorLogManager logManager) {
			this.fileId=fileId;
			this.cid = cid;
			this.conditionSetCd = conditionSetCd;
			this.categoryId = categoryId;
			this.logManager = logManager;
			
			this.mapItemSpecial = new HashMap<>();
			acceptCategory = null;
		}
		
		@Override
		public InputStream get() {
			return fileStreamService.takeOutFromFileId(fileId);
		}

		@Override
		public SpecialEditValue get(SpecialExternalItem specialExternalItem, SpecialEdit spesialEdit) {
			SpecialEditValue speValue;
			if(mapItemSpecial.containsKey(specialExternalItem)) {
				speValue = mapItemSpecial.get(specialExternalItem);
			} else {
				speValue = spesialEdit.edit();
				mapItemSpecial.put(specialExternalItem, speValue);
			}
			return speValue;
		}

		@Override
		public List<StdAcceptItem> getListStdAcceptItems() {
			//ドメインモデル「受入項目(定型)」
			if (this.listStdAcceptItems == null) {
				this.listStdAcceptItems = acceptItemRepos.getListStdAcceptItems(this.cid, this.conditionSetCd);
			}
			return this.listStdAcceptItems;
		}

		@Override
		public Optional<ExternalAcceptCategory> getAcceptCategory() {
			//ドメインモデル「外部受入カテゴリ項目データ」
			if (this.acceptCategory == null) {
				this.acceptCategory = acceptCategoryRepos.getByCategoryId(this.categoryId);
			}
			return this.acceptCategory;
		}
		
		@Override
		public List<AcceptCdConvert> getAcceptCdConvertByCompanyId(){
			//受入コード変換
			if (this.acceptCdConvert == null) {
				this.acceptCdConvert = cdConvertRepos.getAcceptCdConvertByCompanyId(this.cid);
			}
			return this.acceptCdConvert;
		}

		@Override
		public ExacErrorLogManager getLogManager() {
			return logManager;
		}

		@Override
		public StdAcceptItem getStdAcceptItem(int csvItemNo) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public ExternalAcceptCategoryItem getExternalAcceptCategoryItem(int acceptItemNumber) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}
	}
}
