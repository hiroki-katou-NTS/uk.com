package nts.uk.ctx.sys.assist.app.find.resultofdeletion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.sys.assist.app.find.params.LogDataParams;
import nts.uk.ctx.sys.assist.dom.deletedata.CategoryDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ManualSetDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ManualSetDeletionRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ResultOfDeletionFinder {
	@Inject
	private ResultDeletionRepository finder;
	
	@Inject
	private ManualSetDeletionRepository manualSetDeletionRepository;
	
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
		
		List<ResultOfDeletionDto> listResultOfDeletion = new ArrayList<ResultOfDeletionDto>();
		
		for(ResultOfDeletionDto resultOfDeletion : resultOfDeletions) {
			//step 処理IDでドメインモデル「データ削除の手動設定」を取得する
			List<ManualSetDeletion> manualSetDeletions = manualSetDeletionRepository.getManualSetDeletionsSystemTypeAndId(logDataParams.getSystemType(),resultOfDeletion.getDelId());
			if(manualSetDeletions.size() > 0) {
				for(ManualSetDeletion manualSetDeletion : manualSetDeletions) {
					for(CategoryDeletion categoryDeletion : manualSetDeletion.getCategories()) {
						if(logDataParams.getSystemType() == categoryDeletion.getSystemType()) {
							listResultOfDeletion.add(resultOfDeletion);
							break;
						}
					}
				}
			}
		}
		//step List<データ削除の結果>を返す。
		return listResultOfDeletion;
	}
}
