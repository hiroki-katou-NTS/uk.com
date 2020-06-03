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

	public TopPageAlarmStamping get(List<String> lstEmployeeId, String employeeID, List<String> lsterror) {
		
		if (lsterror.isEmpty()) {
			TopPageArm arm = new TopPageArm(ExistenceError.NO_ERROR, lstEmployeeId);
			
			return new TopPageAlarmStamping(new ArrayList<>(), arm);
		}
		
		TopPageArm arm = new TopPageArm(ExistenceError.HAVE_ERROR, lstEmployeeId);
		
		List<TopPageArmDetail> lstTopPageDetail = new ArrayList<>();
		
		for (int i = 0; i < lsterror.size(); i++) {
			lstTopPageDetail.add(new TopPageArmDetail(lsterror.get(i), i, employeeID));
		}
		
		return new TopPageAlarmStamping(lstTopPageDetail, arm);
	}

}
