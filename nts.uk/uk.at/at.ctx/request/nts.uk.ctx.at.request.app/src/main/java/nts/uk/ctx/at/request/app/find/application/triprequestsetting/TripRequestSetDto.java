package nts.uk.ctx.at.request.app.find.application.triprequestsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting.TripRequestSet;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TripRequestSetDto {
	/** 会社ID */
	public String companyId;
	/** コメント１ */
	public String comment1;
	/** 上部コメント.色 */
	public String color1;
	/** 上部コメント.太字 */
	public int weight1;
	/** コメント2 */
	public String comment2;
	/** 下部コメント.色*/
	public String color2;
	/** 下部コメント.太字 */
	public int weight2;
	/** WORK_TYPE */
	public int workType;
	/** 勤務の変更 */
	public int workChange;
	/** 勤務の変更申請時 */
	public int workChangeTime;
	/** 申請対象の矛盾チェック */
	public int contractCheck;
	/** 遅刻早退設定 */
	public int lateLeave;
	public static TripRequestSetDto convertToDto(TripRequestSet domain){
		return new TripRequestSetDto(domain.getCompanyId(), 
				domain.getComment1().isPresent() ? domain.getComment1().get().v() : "", 
				domain.getColor1().v(), domain.getWeight1().value, 
				domain.getComment2().isPresent() ? domain.getComment2().get().v() : "", domain.getColor2().v(), 
				domain.getWeight2().value, domain.getWorkType().value, domain.getWorkChange().value, 
				domain.getWorkChangeTime().value, domain.getContractCheck().value, domain.getLateLeave().value);
	}
}
