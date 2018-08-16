package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.StringUtil;
/**
 * 出張申請設定
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TripRequestSet extends AggregateRoot{
	// 会社ID
	private String companyId;
	// コメント１
	private Optional<Comment> comment1;
	// 上部コメント.色
	private FontColor color1;
	// 上部コメント.太字
	private Weight weight1;
	// コメント2
	private Optional<Comment> comment2;
	// 下部コメント.色
	private FontColor color2;
	// 下部コメント.太字
	private Weight weight2;
	// 出張申請の勤務種類
	private WorkTypeBusinessTrip workType;
	// 勤務の変更
	private WorkChange workChange;
	// 勤務の変更申請時
	private WorkChangeAppTime workChangeTime;
	// 申請対象の矛盾チェック
	private ContractCheck contractCheck;
	// 遅刻早退設定
	private ContractCheck lateLeave;
	
	public static TripRequestSet createFromJavaType(String companyId, String comment1, 
			String color1, int weight1, String comment2, String color2, int weight2, int workType, 
			int workChange, int workChangeTime, int contractCheck, int lateLeave){
		return new TripRequestSet(companyId, 
				comment1 == null ? Optional.empty() : Optional.of(new Comment(comment1)), 
				new FontColor(color1), EnumAdaptor.valueOf(weight1, Weight.class), 
				comment2 == null ? Optional.empty() : Optional.of(new Comment(comment2)), 
				new FontColor(color2),
				EnumAdaptor.valueOf(weight2, Weight.class), 
				EnumAdaptor.valueOf(workType, WorkTypeBusinessTrip.class),
				EnumAdaptor.valueOf(workChange, WorkChange.class), 
				EnumAdaptor.valueOf(workChangeTime, WorkChangeAppTime.class),
				EnumAdaptor.valueOf(contractCheck, ContractCheck.class), 
				EnumAdaptor.valueOf(lateLeave, ContractCheck.class));
	}
}
