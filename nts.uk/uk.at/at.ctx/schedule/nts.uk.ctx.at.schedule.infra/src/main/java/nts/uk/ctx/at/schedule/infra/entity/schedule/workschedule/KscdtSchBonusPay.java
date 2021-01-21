package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.BonusPayTime;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の加給時間
 * UKDesign.データベース.ER図.就業.勤務予定.勤務予定.勤務予定
 * @author HieuLt
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_BONUSPAY")
@Getter
public class KscdtSchBonusPay extends ContractUkJpaEntity{
	
	@EmbeddedId
	public KscdtSchBonusPayPK pK;
	
	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	
	/** 加給時間 **/
	@Column(name = "PREMIUM_TIME")
	public int premiumTime;
	
	/** 所定内加給時間 **/
	@Column(name = "PREMIUM_TIME_WITHIN")
	public int premiumTimeWithIn;
	
	/** 所定外加給時間 **/
	@Column(name = "PREMIUM_TIME_WITHOUT")
	public int premiumTimeWithOut;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SID", referencedColumnName = "SID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchTime kscdtSchTime;
	
	
	@Override
	protected Object getKey() {
		return this.pK;
	}
	//
	public static KscdtSchBonusPay toEntity(String sid , GeneralDate ymd ,BonusPayTime bonusPayTime){
		//Truyen 0 vào tao QA 加給種類 // -------------------QA http://192.168.50.4:3000/issues/110810
		KscdtSchBonusPayPK pk = new KscdtSchBonusPayPK(sid, ymd, 0, bonusPayTime.getBonusPayTimeItemNo());
		
		return new KscdtSchBonusPay(pk,
				AppContexts.user().companyId(),
				bonusPayTime.getBonusPayTime() == null ? 0 : bonusPayTime.getBonusPayTime().v(),
				bonusPayTime.getBonusPayTime() == null ? 0 : bonusPayTime.getWithinBonusPay().getTime().v(), 
				bonusPayTime.getBonusPayTime() == null ? 0 : bonusPayTime.getExcessBonusPayTime().getTime().v());
		
	}
	//勤務予定．勤怠時間．勤務時間．総労働時間．加給時間．加給時間
	public  List<BonusPayTime> toDomain(List<KscdtSchBonusPay> bonusPays){
		List<BonusPayTime> result = new ArrayList<>();
		if(!bonusPays.isEmpty()) {
		bonusPays.stream().forEach( x -> {
			BonusPayTime payTime = new BonusPayTime(
					x.getPK().getFrameNo(),
					new AttendanceTime(x.getPremiumTime()), 
					TimeWithCalculation.sameTime(new AttendanceTime(x.getPremiumTimeWithIn())),
					TimeWithCalculation.sameTime(new AttendanceTime(x.getPremiumTimeWithOut())));
			result.add(payTime);
		});
		}
		return result;
	}
	

	public KscdtSchBonusPay(KscdtSchBonusPayPK pK, String cid, int premiumTime, int premiumTimeWithIn,
			int premiumTimeWithOut) {
		super();
		this.pK = pK;
		this.cid = cid;
		this.premiumTime = premiumTime;
		this.premiumTimeWithIn = premiumTimeWithIn;
		this.premiumTimeWithOut = premiumTimeWithOut;
	}
	

}
