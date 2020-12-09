package nts.uk.ctx.at.request.infra.entity.application.triprequestsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQST_TRIP_REQUEST_SET")
public class KrqstTripRequestSet extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String companyId;
	/** コメント１ */
	@Column(name = "COMMENT_1")
	public String comment1;
	/** 上部コメント.色 */
	@Column(name = "TOP_COMMENT_FONT_COLOR")
	public String color1;
	/** 上部コメント.太字 */
	@Column(name = "TOP_COMMENT_FONT_WEIGHT")
	public int weight1;
	/** コメント2 */
	@Column(name = "COMMENT_2")
	public String comment2;
	/** 下部コメント.色*/
	@Column(name = "BOTTOM_COMMENT_FONT_COLOR")
	public String color2;
	/** 下部コメント.太字 */
	@Column(name = "BOTTOM_COMMENT_FONT_WEIGHT")
	public int weight2;
	/** WORK_TYPE */
	@Column(name = "WORK_TYPE")
	public int workType;
	/** 勤務の変更 */
	@Column(name = "WORK_CHANGE")
	public int workChange;
	/** 勤務の変更申請時 */
	@Column(name = "WORK_CHANGE_APPTIME_ATR")
	public int workChangeTime;
	/** 申請対象の矛盾チェック */
	@Column(name = "CONTRA_CHECK_ATR")
	public int contractCheck;
	/** 遅刻早退設定 */
	@Column(name = "LATE_LEAVE_EARLY_SET")
	public int lateLeave;
	
	@Override
	protected Object getKey() {
		return companyId;
	}
}
