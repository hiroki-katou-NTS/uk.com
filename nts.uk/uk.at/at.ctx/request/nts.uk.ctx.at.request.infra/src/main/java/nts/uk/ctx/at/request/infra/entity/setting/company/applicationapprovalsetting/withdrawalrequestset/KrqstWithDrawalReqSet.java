package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.withdrawalrequestset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "KRQST_WITHDRAWAL_REQ_SET")
public class KrqstWithDrawalReqSet extends ContractUkJpaEntity implements Serializable {
	public static final long serialVersionUID = 1L;
	
	/** * 会社ID */
	@Id
	@Column(name="CID")
    public String companyId;
    
    /** * 勤務時間変更の許可 */
	@Column(name = "PERMISSION_DIVISION")
    public int permissionDivision;
    
    /** * 勤務種類矛盾チェック */
	@Column(name = "APPLI_DATE_CONTRAC")
    public int appliDateContrac;
    
    /** * 実績の表示 */
	@Column(name = "USE_ATR")
    public int useAtr;
    
    /** * 法内法外矛盾チェック */
	@Column(name = "CHECK_UPLIMIT_HALFDAY_HD")
    public int checkUpLimitHalfDayHD;
    
    /** * コメント */
	@Column(name = "PICKUP_COMMENT")
    public String pickUpComment;
    
    /** * 太字 */
	@Column(name = "PICKUP_BOLD")
    public int pickUpBold;
    
    /** * 文字色 */
	@Column(name = "PICKUP_LETTER_COLOR")
    public String pickUpLettleColor;
    
    /** * コメント */
	@Column(name = "DEFERRED_COMMENT")
    public String deferredComment;
    
    /** * 太字 */
	@Column(name = "DEFERRED_BOLD")
    public int deferredBold;
    
    /** * 文字色 */
	@Column(name = "DEFERRED_LETTER_COLOR")
    public String deferredLettleColor;
    
    /** * 就業時間帯選択の利用 */
	@Column(name = "DEFERRED_WORKTIME_SELECT")
    public int deferredWorkTimeSelect;
    
    /** * 同時申請必須 */
	@Column(name = "SIMUL_APPLI_REQUIRE")
    public int simulAppliReq;
    
    /** * 振休先取許可 */
	@Column(name = "LETTER_SUPER_LEAVE")
    public int lettleSuperLeave;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return companyId;
	}
	
	public WithDrawalReqSet toDomain(){
		return new WithDrawalReqSet(
				this.companyId,
				this.permissionDivision,
				this.appliDateContrac,
				this.useAtr,
				this.checkUpLimitHalfDayHD,
				this.pickUpComment,
				this.pickUpBold,
				this.pickUpLettleColor,
				this.deferredComment,
				this.deferredBold,
				this.deferredLettleColor,
				this.deferredWorkTimeSelect,
				this.simulAppliReq,
				this.lettleSuperLeave);
	}
}
