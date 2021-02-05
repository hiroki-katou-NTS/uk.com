package nts.uk.ctx.exio.app.command.exi.csvimport;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.ejb.Stateful;
import javax.inject.Inject;

import org.apache.commons.csv.CSVFormat;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDateTime;
import nts.gul.csv.CSVParsedResult;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvert;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvertRepository;
import nts.uk.ctx.exio.dom.exi.codeconvert.CdConvertDetails;
import nts.uk.ctx.exio.dom.exi.codeconvert.CodeConvertCode;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceLineNumber;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.ctx.exio.dom.exi.csvimport.ExiCharset;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSet;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSet;
import nts.uk.ctx.exio.dom.exi.execlog.ErrorOccurrenceIndicator;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLog;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLogRepository;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategory;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryItem;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryItemService;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryRepository;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialEditValue;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialExternalItem;
import nts.uk.ctx.exio.dom.exi.item.AcceptItemEditValueDto;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItemRepository;
import nts.uk.ctx.exio.dom.exi.service.FileUtil;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;

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
	@Inject
	private AcceptCdConvertRepository cdConvertRepos;
	@Inject
	private StoredFileStreamService fileStreamService;
	@Inject
	private ExternalAcceptCategoryItemService categoryItemService;
	
	private static final String NUMBER_OF_ERROR = "エラー件数";
	private static final String NUMBER_OF_SUCCESS = "処理カウント";
	private static final String NUMBER_OF_TOTAL = "処理トータルカウント";
	private static final String STOP_MODE = "中断するしない"; // 0 - しない　1-する
	private static final String STATUS = "動作状態"; // 0 - チェック中, 1 - チェック完了, 2 - 受入中, 3 - 完了
	
	private static Charset getCharset(Integer valueEncoding) {
		ExiCharset encoding = ExiCharset.valueOf(valueEncoding);
        switch (encoding) {
        case Shift_JIS:
            return Charset.forName("Shift_JIS");
        default:
            return StandardCharsets.UTF_8;
        }
    }
	
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
		//ドメインモデル「受入条件設定(定型)」
		Optional<StdAcceptCondSet> optCondSet = condSetRepos.getById(cid, command.getConditionSetCode());
		if(!optCondSet.isPresent() || !optCondSet.get().getCategoryId().isPresent()) return;
		StdAcceptCondSet condSet = optCondSet.get();
		int categoryId = condSet.getCategoryId().get();
		//ドメインモデル「受入項目(定型)」
		List<StdAcceptItem> lstAccSetItem = acceptItemRepos.getListStdAcceptItems(cid, command.getConditionSetCode());
		//ドメインモデル「外部受入カテゴリ項目データ」
		Optional<ExternalAcceptCategory> optAcceptCategory = acceptCategoryRepos.getByCategoryId(categoryId);
		//受入コード変換
		List<AcceptCdConvert> lstcdConvert = cdConvertRepos.getAcceptCdConvertByCompanyId(cid);
		
		if(!optAcceptCategory.isPresent()) {
			setter.updateData(NUMBER_OF_SUCCESS, command.getCsvLine());
			setter.updateData(STATUS, command.getStateBehavior());
			return;	
		}
		ExternalAcceptCategory acceptCategory = optAcceptCategory.get();
		List<ExternalAcceptCategoryItem> lstAcceptItem = acceptCategory.getLstAcceptItem();
		int numberOfError = 0;
		try {
			// get input stream by fileId		
			InputStream inputStream = this.fileStreamService.takeOutFromFileId(command.getCsvFileId());
			int dataLineNum = condSet.getCsvDataItemLineNumber().isPresent() ? condSet.getCsvDataItemLineNumber().get().v() : 1;
			int startLine = condSet.getCsvDataStartLine().isPresent() ? condSet.getCsvDataStartLine().get().v() : 2;
			
			NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true).withColumnSizeDiffByRow()
					.withChartSet(getCharset(command.getEndcoding()));
			CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
			//Read header
			NtsCsvRecord colHeader = csvParsedResult.getRecords().get(dataLineNum - 1);
			
			List<ExacErrorLog> lstExacErrorLog = new ArrayList<>();
			for (int i = 1; i <= command.getCsvLine() ; i++) { // line
				if(i < startLine) continue;
				List<List<String>> lstLineData = new ArrayList<>();
				//Read record				
				NtsCsvRecord record = csvParsedResult.getRecords().get(i);
				boolean isLineError = false;
				boolean isCond = true; //True 受入条件がOK、False　受入条件がNOT　OK
				Map<SpecialExternalItem, SpecialEditValue> mapItemSpecial = new HashMap<>();
				for(int items = 0; items < colHeader.columnLength(); items ++){ //line of item
					String csvItemName = colHeader.getColumn(items).toString();
					String csvItemValue = record.getColumn(items).toString();
					
					int count = items + 1;
					List<StdAcceptItem> lstAcceptSetItem = lstAccSetItem.stream()
							.filter(x -> x.getCsvItemName().get().equals(csvItemName) && x.getCsvItemNumber().get() == count)
							.collect(Collectors.toList());
					if(lstAcceptSetItem.isEmpty()) continue;
					StdAcceptItem accSetItem = lstAcceptSetItem.get(0);
					
					List<ExternalAcceptCategoryItem> accItems = lstAcceptItem.stream()
							.filter(x -> x.getCategoryId() == categoryId && x.getItemNo() == accSetItem.getAcceptItemNumber())
							.collect(Collectors.toList());
					if(accItems.isEmpty()) continue;
					ExternalAcceptCategoryItem acceptItem = accItems.get(0);
					
					lstLineData.add(Arrays.asList(csvItemName, csvItemValue));
					//①　受付条件設定チェック			
					AcceptItemEditValueDto condEditAndCheck = accSetItem.checkCondition(csvItemValue);
					//コード変換に選択があるか判別
					condEditAndCheck = convertCodeItem(lstcdConvert, accSetItem, csvItemValue, condEditAndCheck);
					
					if(!condEditAndCheck.getEditError().isEmpty()) {
						//エラー内容を編集してドメイン「外部受入エラーログ」に書き出す
						ExacErrorLog exLog = new ExacErrorLog(i, //ログ連番
								cid,
								command.getProcessId(),
								Optional.ofNullable(csvItemName),
								Optional.ofNullable(csvItemValue),
								Optional.ofNullable(TextResource.localize(condEditAndCheck.getEditError())),
								new AcceptanceLineNumber(i),
								GeneralDateTime.now(),
								Optional.ofNullable(acceptItem.getItemName()),
								ErrorOccurrenceIndicator.EDIT);
						lstExacErrorLog.add(exLog);
						isLineError = condEditAndCheck.isResultCheck();
					}
					
					if(condEditAndCheck.isResultCheck()) {
						isCond = false;
					} else {
						SpecialEditValue speValue;
						//②　TODO Check primitive value
						
						//③　TODO アルゴリズム「特殊区分項目の編集」を実行
						//【work特殊区分】に該当特殊区分の値が既に存在（取得済み）するかをチェックする
						if(mapItemSpecial.containsKey(acceptItem.getSpecialFlg())) {
							speValue = mapItemSpecial.get(acceptItem.getSpecialFlg());
						} else {
							speValue = categoryItemService.editSpecial(condEditAndCheck.getEditValue(),
									condSet.getAcceptMode(),
									lstLineData,
									acceptItem);
							mapItemSpecial.put(acceptItem.getSpecialFlg(), speValue);
						}
						if(speValue.isChkError()) {
							ExacErrorLog exLog = new ExacErrorLog(i, //ログ連番
									cid,
									command.getProcessId(),
									Optional.ofNullable(acceptItem.getTableName() + "[" + acceptItem.getColumnName() + "]"),
									Optional.ofNullable(csvItemValue),
									Optional.ofNullable(TextResource.localize(speValue.getErrorContent())),
									new AcceptanceLineNumber(i),
									GeneralDateTime.now(),
									Optional.ofNullable(acceptItem.getItemName()),
									ErrorOccurrenceIndicator.EDIT);
							lstExacErrorLog.add(exLog);
							isLineError = true;
						} else {
							
						}
						//④　TODO　履歴区分
					}	
					
					
					
				} //line of item
				if(isLineError) {
					numberOfError += 1; 
					setter.updateData(NUMBER_OF_ERROR, numberOfError);
				} else {
					if(isCond) {
						//TODO insert vao db	
					}			
					setter.updateData(NUMBER_OF_SUCCESS, i - numberOfError);
				}
							
				//↓-----------lan truoc
				//TODO アルゴリズム「外部受入テスト本体」を実行する
				if (asyncTask.hasBeenRequestedToCancel()) {
					// 外部受入動作管理の中断するしない区分を更新する
					setter.updateData(STOP_MODE, 1);
					asyncTask.finishedAsCancelled();
					inputStream.close();
					break;
				}			
				
				
				setter.updateData(STATUS, command.getStateBehavior());
				
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					inputStream.close();
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				//↑---------lan truoc
			} // line
			exacErrorLogRepository.addList(lstExacErrorLog);
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * コード変換に選択があるか判別
	 * @param lstcdConvert
	 * @param accSetItem
	 * @param CSV受入値
	 * @param condEditAndCheck
	 */
	private AcceptItemEditValueDto convertCodeItem(List<AcceptCdConvert> lstcdConvert,
			StdAcceptItem accSetItem,
			String CSV受入値,
			AcceptItemEditValueDto condEditAndCheck) {
		if ((accSetItem.getItemType() == ItemType.CHARACTER
				|| accSetItem.getItemType() == ItemType.NUMERIC)
				&& accSetItem.getDataFormatSetting().isPresent()) {
			Optional<CodeConvertCode> cdConvertSt;
			if(accSetItem.getItemType() == ItemType.CHARACTER) {
				ChrDataFormatSet chrSet = (ChrDataFormatSet) accSetItem.getDataFormatSetting().get();
				cdConvertSt = chrSet.getCdConvertCd();
			} else {
				NumDataFormatSet numSet = (NumDataFormatSet) accSetItem.getDataFormatSetting().get();
				cdConvertSt = numSet.getCdConvertCd();
			}
			if(!cdConvertSt.isPresent()) return condEditAndCheck;
			//コード変換コード
			List<AcceptCdConvert> cdConverts = lstcdConvert.stream()
					.filter(x -> x.getConvertCd().v().equals(cdConvertSt.get().v()))
					.collect(Collectors.toList());
			if(cdConverts.isEmpty()) return condEditAndCheck;
			
			AcceptCdConvert cdConvert = cdConverts.get(0);
			List<CdConvertDetails> listConvertDetails = cdConvert.getListConvertDetails()
					.stream().filter(x -> x.getOutputItem().v().equals(CSV受入値))
					.collect(Collectors.toList());
			if(!listConvertDetails.isEmpty()) {
				condEditAndCheck.setEditValue(listConvertDetails.get(0).getSystemCd().v());	
			} else {
				//「設定のないコードの受入」を判別
				if(cdConvert.getAcceptWithoutSetting() == NotUseAtr.USE) {
					condEditAndCheck.setEditError("Msg_1018");
					condEditAndCheck.setResultCheck(true);
				}
			}						
		}
		return condEditAndCheck;
	}

}
