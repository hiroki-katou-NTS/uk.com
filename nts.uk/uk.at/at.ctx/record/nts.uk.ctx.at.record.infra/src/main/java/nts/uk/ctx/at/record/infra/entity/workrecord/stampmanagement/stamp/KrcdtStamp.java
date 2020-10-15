package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.AuthcMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampLocationInfor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * @author ThanhNX
 *
 *         打刻
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_STAMP")
public class KrcdtStamp extends UkJpaEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtStampPk pk;

//	public static final JpaEntityMapper<KrcdtStamp> MAPPER = new JpaEntityMapper<>(KrcdtStamp.class);
	
	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 認証方法 0:ID認証 1:ICカード認証 2:静脈認証 3:外部認証(外部受入など）
	 */
	@Basic(optional = false)
	@Column(name = "AUTHC_METHOD")
	public int autcMethod;

	/**
	 * 打刻手段 0:氏名選択 1:指認証打刻 2:ICカード打刻 3:個人打刻 4:ポータル打刻 5:スマホ打刻 6:タイムレコーダー打刻 7:テキスト打刻
	 * 8:リコー複写機打刻
	 */
	@Basic(optional = false)
	@Column(name = "STAMP_MEANS")
	public int stampMeans;

	/**
	 * 計算区分変更対象 0:なし 1:早出 2:残業 3:休出 4:ﾌﾚｯｸｽ
	 */
	@Basic(optional = false)
	@Column(name = "CHANGE_CAL_ART")
	public int changeCalArt;

	/**
	 * 所定時刻セット区分 0:なし 1:直行 2:直帰
	 */
	@Basic(optional = false)
	@Column(name = "SET_PRE_CLOCK_ART")
	public int preClockArt;

	/**
	 * 勤務種類を半休に変更する 0:False 1:True
	 */
	@Basic(optional = false)
	@Column(name = "CHANGE_HALF_DAY")
	public boolean changeHalfDay;

	/**
	 * 外出区分 0:私用 1:公用 2:有償 3:組合
	 */
	@Basic(optional = true)
	@Column(name = "GO_OUT_ART")
	public Integer goOutArt;

	/**
	 * 反映済み区分 0:未反映 1:反映済み
	 */
	@Basic(optional = false)
	@Column(name = "REFLECTED_ATR")
	public boolean reflectedAtr;

	/**
	 * 応援カード番号
	 */
	@Basic(optional = true)
	@Column(name = "SUPPORT_CARD")
	public String suportCard;

	/**
	 * 打刻場所コード
	 */
	@Basic(optional = true)
	@Column(name = "STAMP_PLACE")
	public String stampPlace;

	/**
	 * 就業時間帯コード
	 */
	@Basic(optional = true)
	@Column(name = "WORKTIME")
	public String workTime;

	/**
	 * 時間外時間
	 */
	@Basic(optional = true)
	@Column(name = "OVERTIME")
	public Integer overTime;

	/**
	 * 時間外深夜時間
	 */
	@Basic(optional = true)
	@Column(name = "LATE_NIGHT_OVERTIME")
	public Integer lateNightOverTime;

	/**
	 * 経度
	 */
	@Basic(optional = true)
	@Column(name = "LOCATION_LON")
	public BigDecimal locationLon;

	/**
	 * 緯度
	 */
	@Basic(optional = true)
	@Column(name = "LOCATION_LAT")
	public BigDecimal locationLat;

	/**
	 * エリア外の打刻区分
	 */
	@Basic(optional = true)
	@Column(name = "OUTSIDE_AREA_ART")
	public Boolean outsideAreaArt;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KrcdtStamp toEntityUpdate(Stamp stamp) {
		this.autcMethod = stamp.getRelieve().getAuthcMethod().value;
		this.stampMeans = stamp.getRelieve().getStampMeans().value;
		this.changeCalArt = stamp.getType().getChangeCalArt().value;
		this.preClockArt = stamp.getType().getSetPreClockArt().value;
		this.changeHalfDay = stamp.getType().isChangeHalfDay();
		this.goOutArt = stamp.getType().getGoOutArt().isPresent() ? stamp.getType().getGoOutArt().get().value : null;
		this.reflectedAtr = stamp.isReflectedCategory();
		this.suportCard = stamp.getRefActualResults().getCardNumberSupport().isPresent()
				? stamp.getRefActualResults().getCardNumberSupport().get()
				: null;
		this.stampPlace = stamp.getRefActualResults().getWorkLocationCD().isPresent()
				? stamp.getRefActualResults().getWorkLocationCD().get().v()
				: null;
		this.workTime = stamp.getRefActualResults().getWorkTimeCode().isPresent()
				? stamp.getRefActualResults().getWorkTimeCode().get().v()
				: null;
		this.overTime = stamp.getRefActualResults().getOvertimeDeclaration().isPresent()
				? stamp.getRefActualResults().getOvertimeDeclaration().get().getOverTime().v()
				: null; // overTime
		this.lateNightOverTime = stamp.getRefActualResults().getOvertimeDeclaration().isPresent()
				? stamp.getRefActualResults().getOvertimeDeclaration().get().getOverLateNightTime().v()
				: null; // lateNightOverTime
		this.locationLon = stamp.getLocationInfor().isPresent()? (stamp.getLocationInfor().get().getPositionInfor() !=null? new BigDecimal(stamp.getLocationInfor().get().getPositionInfor().getLongitude()):null):null;
		this.locationLat = stamp.getLocationInfor().isPresent()? (stamp.getLocationInfor().get().getPositionInfor() !=null? new BigDecimal(stamp.getLocationInfor().get().getPositionInfor().getLatitude()):null):null;
		this.outsideAreaArt = stamp.getLocationInfor().isPresent() ? stamp.getLocationInfor().get().isOutsideAreaAtr()
				: null;
		return this;
	}


	public Stamp toDomain() {
		GeoCoordinate geoLocation = null;
		if (this.locationLat !=  null && this.locationLon != null){
			geoLocation = new GeoCoordinate(this.locationLat.doubleValue(), this.locationLon.doubleValue()); 
		}
		val stampNumber = new StampNumber(this.pk.cardNumber);
		val relieve = new Relieve(AuthcMethod.valueOf(this.autcMethod), StampMeans.valueOf(this.stampMeans));
		val stampType = StampType.getStampType(this.changeHalfDay,
				this.goOutArt == null ? null : GoingOutReason.valueOf(this.goOutArt),
				SetPreClockArt.valueOf(this.preClockArt), ChangeClockArt.valueOf(this.pk.changeClockArt),
				ChangeCalArt.valueOf(this.changeCalArt));
		
		OvertimeDeclaration overtime = this.overTime == null ? null
				: new OvertimeDeclaration(new AttendanceTime(this.overTime),
						new AttendanceTime(this.lateNightOverTime));
						
		val refectActualResult = new RefectActualResult(this.suportCard,
				this.stampPlace == null ? null : new WorkLocationCD(this.stampPlace),
				this.workTime == null ? null : new WorkTimeCode(this.workTime),
				overtime );
		
		val locationInfor = new StampLocationInfor(geoLocation,
				this.outsideAreaArt == null ? false : this.outsideAreaArt);
		
		return new Stamp(new ContractCode(this.pk.contractCode) ,
						stampNumber, 
						this.pk.stampDateTime,
						relieve, stampType, refectActualResult,
						this.reflectedAtr, Optional.ofNullable(locationInfor), Optional.empty());

	}
	
	
}
