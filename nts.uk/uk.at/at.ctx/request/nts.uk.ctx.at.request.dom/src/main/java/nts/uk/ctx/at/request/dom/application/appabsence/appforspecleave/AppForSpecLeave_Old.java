package nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author loivt
 * 特別休暇申請
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppForSpecLeave_Old {
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 喪主フラグ
	 */
	private boolean mournerFlag;
	/**
	 * 続柄コード
	 */
	private RelationshipCDPrimitive relationshipCD;
	/**
	 * 続柄理由
	 */
	private RelationshipReasonPrimitive relationshipReason;
	
	public static AppForSpecLeave_Old createFromJavaType(String appID, boolean mournerFlag, String relationshipCD,
				String relationshipReason){
		return new AppForSpecLeave_Old(appID,
				mournerFlag,
				new RelationshipCDPrimitive(relationshipCD),
				new RelationshipReasonPrimitive(relationshipReason));
	}
}
