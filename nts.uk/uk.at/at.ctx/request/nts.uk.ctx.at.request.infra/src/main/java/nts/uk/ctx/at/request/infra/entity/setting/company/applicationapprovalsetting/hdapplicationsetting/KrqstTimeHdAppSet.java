package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.hdapplicationsetting;

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
@Table(name = "KRQST_TIME_HD_APP_SET")
public class KrqstTimeHdAppSet extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 会社ID **/
	@Id
	@Column(name = "CID")
	public String companyId;
	
	/** 1日休超過をチェックする **/
	@Column(name = "CHECK_DAY_OFF")
	public int checkDay;
	
	/** 60H超休を利用する **/
	@Column(name = "USE_60H_HD")
	public int use60h;
	
	/** 出勤前2を利用する **/
	@Column(name = "USE_ATTEND_BEFORE2")
	public int useAttend2;
	
	/** 出勤前2名称 **/
	@Column(name = "NAMES2_BEFORE_WORK")
	public String nameBefore2;
	
	/** 出勤前を利用する **/
	@Column(name = "USE_BEFORE_GOTO_WORK")
	public int useBefore;
	
	/** 出勤前名称 **/
	@Column(name = "NAME_BEFORE_WORK")
	public String nameBefore;
	
	/** 実績表示区分 **/
	@Column(name = "ACTUAL_DISPLAY_ATR")
	public int actualDisp;
	
	/** 実績超過をチェックする  **/
	@Column(name = "CHECK_OVER_DILIVER")
	public int checkOver;
	
	/** 時間代休を利用する **/
	@Column(name = "USE_TIME_HD")
	public int useTimeHd;
	
	/** 時間年休を利用する **/
	@Column(name = "USE_TIME_YEAR_HD")
	public int useTimeYear;
	
	/** 私用外出を利用する **/
	@Column(name = "USE_PRIVATE_OUT")
	public int usePrivate;
	
	/** 私用外出名称 **/
	@Column(name = "PRIVATE_OUT_NAME")
	public String privateName;
	
	/** 組合外出を利用する **/
	@Column(name = "USE_UNION_LEAVE")
	public int unionLeave;
	
	/**  組合外出名称 **/
	@Column(name = "UNION_NAME_GOOUT")
	public String unionName;
	
	/** 退勤後2を利用する **/
	@Column(name = "USE2_AFTER_LEAVE_HOME")
	public int useAfter2;
	
	/** 退勤後2名称 **/
	@Column(name = "NAMES2_AFTER_LEAVE_HOME")
	public String nameAfter2;
	
	/** 退勤後を利用する **/
	@Column(name = "USE_AFTER_LEAVE_WORK")
	public int useAfter;
	
	/** 退勤後名称 **/
	@Column(name = "NAME_AFTER_LEAVE_HOME")
	public String nameAfter;
	
	@Override
	protected Object getKey() {
		return null;
	}

}
