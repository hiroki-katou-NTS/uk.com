package nts.uk.ctx.bs.employee.dom.workplace.group.domainservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceReplaceResult;
/**
 * 職場グループに所属する職場を入れ替える
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.社員.職場.職場グループ.職場グループに所属する職場を入れ替える.職場グループに所属する職場を入れ替える
 * @author phongtq
 *
 */
public class ReplaceWorkplacesService {
	public static Map<String, WorkplaceReplaceResult> getWorkplace(Require require,WorkplaceGroup group, List<String> lstWorkplaceId){
		
		// require.職場グループを指定して職場グループ所属情報を取得する( 職場グループ.職場グループID )
		// 旧所属情報リスト=get*(ログイン会社ID, 職場グループ.職場グループID):List<職場グループ所属情報>
		List<AffWorkplaceGroup> lstFormerAffInfo = require.getByListWKPID(lstWorkplaceId);
		
		// filter not 職場IDリスト.contains( $.職場ID )
		List<AffWorkplaceGroup> lstDel = lstFormerAffInfo.stream()
				.filter(x-> !lstWorkplaceId.contains(x.getWKPID())).collect(Collectors.toList());
		
		List<WorkplaceReplaceResult> replaceResults = new ArrayList<>();
		
		// $削除結果リスト = $削除対象リスト:					
		lstDel.forEach(x->{
			// require.職場を指定して職場グループ所属情報を削除する( $.職場ID )	
			Optional<AtomTask> atomTaks = Optional.of(AtomTask.of(() -> {
				require.deleteByWKPID(x.getWKPID());
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
		
		// [R-1] 職場グループを指定して職場グループ所属情報を取得する																
		// 職場グループ所属情報Repository.*get( 会社ID, 職場ID )	
		List<AffWorkplaceGroup> getByListWKPID(List<String> WKPID);
		
		// 職場を指定して職場グループ所属情報を削除する																		
		// 職場グループ所属情報Repository.delete( 会社ID, 職場ID )	
		void deleteByWKPID(String WKPID);
	}
}
