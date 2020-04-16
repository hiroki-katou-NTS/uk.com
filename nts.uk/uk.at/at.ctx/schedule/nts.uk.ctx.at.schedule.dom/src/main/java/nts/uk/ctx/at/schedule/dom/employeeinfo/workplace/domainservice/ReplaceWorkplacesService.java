package nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.domainservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.AffWorkplaceGroup;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceGroup;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceReplaceResult;
import nts.uk.shr.com.context.AppContexts;
/**
 * 職場グループに所属する職場を入れ替える
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.社員.職場.職場グループ.職場グループに所属する職場を入れ替える.職場グループに所属する職場を入れ替える
 * @author phongtq
 *
 */
public class ReplaceWorkplacesService {
	public static Map<String, WorkplaceReplaceResult> getWorkplace(Require require,WorkplaceGroup group, List<String> lstWorkplaceId){
		String CID = AppContexts.user().companyId();
		
		// require.職場グループを指定して職場グループ所属情報を取得する( 職場グループ.職場グループID )
		// 旧所属情報リスト=get*(ログイン会社ID, 職場グループ.職場グループID):List<職場グループ所属情報>
		List<AffWorkplaceGroup> lstFormerAffInfo = require.getByListWKPID(CID, lstWorkplaceId);
		
		// filter not 職場IDリスト.contains( $.職場ID )
		List<AffWorkplaceGroup> lstDel = lstFormerAffInfo.stream()
				.filter(x-> !lstWorkplaceId.contains(x.getWKPID())).collect(Collectors.toList());
		
		List<WorkplaceReplaceResult> replaceResults = new ArrayList<>();
		
		// $削除結果リスト = $削除対象リスト:					
		lstDel.forEach(x->{
			// require.職場を指定して職場グループ所属情報を削除する( $.職場ID )	
			Optional<AtomTask> atomTaks = Optional.of(AtomTask.of(() -> {
				require.deleteByWKPID(CID, x.getWKPID());
			}));
			
			//職場グループの職場入替処理結果#離脱する( $.value )
			replaceResults.add(WorkplaceReplaceResult.add(atomTaks));
		});
		
		List<WorkplaceReplaceResult> results = new ArrayList<>();

		// $追加結果リスト = 職場IDリスト:
		lstWorkplaceId.forEach(x->{
			// 職場グループに職場を追加する#追加する( require, 職場グループ, $ )
			results.add(AddWplOfWorkGrpService.addWorkplace(require, group, x));
		});
		List<WorkplaceReplaceResult> map = Stream.of(replaceResults, results)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());
		// Chờ QA http://192.168.50.4:3000/issues/110132
		Map<String, WorkplaceReplaceResult> dateHistLst = new HashMap<>();
		return dateHistLst;
	}
	
	public static interface Require extends AddWplOfWorkGrpService.Require{
		List<AffWorkplaceGroup> getByListWKPID(String CID, List<String> WKPID);
		
		void deleteByWKPID(String CID, String WKPID);
	}
}
