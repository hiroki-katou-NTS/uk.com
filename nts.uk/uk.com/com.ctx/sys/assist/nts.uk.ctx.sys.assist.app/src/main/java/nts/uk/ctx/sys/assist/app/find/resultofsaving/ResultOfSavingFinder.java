package nts.uk.ctx.sys.assist.app.find.resultofsaving;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.app.find.params.LogDataParams;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSavingRepository;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
 * データ保存の保存結果
 */
public class ResultOfSavingFinder {
	
	@Inject
	private ResultOfSavingRepository finder;

	@Inject
	private TableListRepository tableListRepository;
	

	public List<ResultOfSavingDto> getAllResultOfSaving() {
		return finder.getAllResultOfSaving().stream().map(item -> ResultOfSavingDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	public ResultOfSavingDto getResultOfSavingById(String storeProcessingId) {
		return ResultOfSavingDto.fromDomain(finder.getResultOfSavingById(storeProcessingId).get());
	}
	
	//step データ保存の保存結果を取得
	public List<ResultOfSavingDto> getResultOfSaving (LogDataParams logDataParams) {
		
		//step ドメインモデル「データ保存の保存結果」を取得
		List<ResultOfSavingDto> resultOfSavings  = finder.getResultOfSaving(
				AppContexts.user().companyId(),
				logDataParams.getStartDateOperator(),
				logDataParams.getEndDateOperator(),
				logDataParams.getListOperatorEmployeeId()
				).stream()
				.map(item -> ResultOfSavingDto.fromDomain(item))
				.collect(Collectors.toList());
		
		//step 空欄Output　List<データ保存の保存結果>を作成する。
		List<ResultOfSavingDto> listResultOfSaving = new ArrayList<ResultOfSavingDto>();
		
		for(ResultOfSavingDto resultOfSaving : resultOfSavings) {
			
			//step オブジェクト「テーブル一覧」を取得する。
			List<TableList> tableList = tableListRepository.getBySystemTypeAndStorageId(logDataParams.getSystemType(), resultOfSaving.getStoreProcessingId());
			
			//step 取得したList<テーブル一覧>をチェックする。
			if(tableList.size() > 0) {
				
				//step 空欄Output List<データ保存の保存結果>にループ中の「データ保存の保存結果システム」を追加する。
				listResultOfSaving.add(resultOfSaving);
			}
		}
		
		//step List<データ保存の保存結果>を返す。
		return listResultOfSaving;
	}
}
