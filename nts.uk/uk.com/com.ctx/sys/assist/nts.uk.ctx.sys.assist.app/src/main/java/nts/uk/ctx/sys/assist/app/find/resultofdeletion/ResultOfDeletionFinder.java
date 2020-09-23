package nts.uk.ctx.sys.assist.app.find.resultofdeletion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.assist.app.find.params.LogDataParams;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletionRepository;
import nts.uk.ctx.sys.assist.dom.reference.record.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.sys.assist.dom.reference.record.PersonEmpBasicInfoImport;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ResultOfDeletionFinder {
	@Inject
	private ResultDeletionRepository finder;
	

	
	//step データ削除の保存結果を取得
	public List<ResultOfDeletionDto> getResultOfDeletion (LogDataParams logDataParams) {
		
		//step ドメインモデル「データ削除の保存結果」を取得 
		List<ResultOfDeletionDto> resultOfDeletions =  finder.getResultOfDeletion(
				AppContexts.user().companyId(),
				logDataParams.getStartDateOperator(),
				logDataParams.getEndDateOperator(),
				logDataParams.getListOperatorEmployeeId()
				).stream()
				.map(item -> ResultOfDeletionDto.fromDomain(item))
				.collect(Collectors.toList());
		
		//step   空のList<データ削除の結果>を作成する。
		List<ResultOfDeletionDto> listResultOfDeletion = new ArrayList<ResultOfDeletionDto>();
		
		for(ResultOfDeletionDto resultOfDeletion : resultOfDeletions) {
			//TODO step ドメインモデル「データ削除の手動設定」を取得する。
			//TODO step 取得した「データ削除の手動設定．対象カテゴリ」リストから「対象カテゴリ」を取得する。
			
			//step List<データ削除の結果>に、ループ中の「データ削除の保存結果」を追加する。
			if(true) {
				listResultOfDeletion.add(resultOfDeletion);
			}
		}
		//step List<データ削除の結果>を返す。
		return listResultOfDeletion;
	}
}
