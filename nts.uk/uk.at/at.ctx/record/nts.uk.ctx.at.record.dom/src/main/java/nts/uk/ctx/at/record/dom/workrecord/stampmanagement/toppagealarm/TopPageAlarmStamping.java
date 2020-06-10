package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * web打刻用トップページアラーム
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.トップページアラーム.web打刻用トップページアラーム
 * @author chungnt
 *
 */

@AllArgsConstructor
@Getter
public class TopPageAlarmStamping implements DomainAggregate {

	/**
	 * 詳細
	 */
	public final List<TopPageArmDetail> lstTopPageDetail;

	/**
	 * トップページアラーム
	 */
	public final TopPageArm pageArm;

	/**
	 * 
	 * @param companyId  		会社ID
	 * @param lstEmployeeId		管理者リスト
	 * @param employeeID		対象社員
	 * @param lsterror			エラーリスト
	 * @return					web打刻用トップページアラーム
	 */
	public TopPageAlarmStamping get(String companyId, List<String> lstEmployeeId, String employeeID, List<String> lsterror) {
		//if エラーリスト.isEmpty
		if (lsterror.isEmpty()) {
			//	$トップページエラー無 = トップページアラーム#新規作成する(会社ID, エラーの有無.エラーなし, 管理者リスト)		
			TopPageArm arm = new TopPageArm(companyId, ExistenceError.NO_ERROR, lstEmployeeId); 
			
			// return web打刻用トップページアラーム#web打刻用トップページアラーム(empty, $トップページエラー無)		
			return new TopPageAlarmStamping(new ArrayList<>(), arm);
		}
		
		//	$count = 0																														
		// $詳細 = $エラーメッセージ in エラーリスト：																							
		// 		トップページアラーム詳細#トップページアラーム詳細($エラーメッセージ, count++, 対象社員)
		
		TopPageArm arm = new TopPageArm(companyId ,ExistenceError.HAVE_ERROR, lstEmployeeId);
		
		List<TopPageArmDetail> lstTopPageDetail = new ArrayList<>();
		
		for (int i = 0; i < lsterror.size(); i++) {
			lstTopPageDetail.add(new TopPageArmDetail(lsterror.get(i), i, employeeID));
		}
		
		// return web打刻用トップページアラーム#web打刻用トップページアラーム($詳細, $トップページエラー有)
		return new TopPageAlarmStamping(lstTopPageDetail, arm);
	}

}
