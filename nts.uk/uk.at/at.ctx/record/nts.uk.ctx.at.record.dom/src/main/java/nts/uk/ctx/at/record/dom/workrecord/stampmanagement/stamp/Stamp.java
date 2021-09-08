package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * AR : 打刻
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻
 * 
 * @author tutk
 *
 */
@AllArgsConstructor
public class Stamp implements DomainAggregate, Cloneable {

	/**
	 * 契約コード
	 * ver2　属性追加
	 */
	@Getter
	private final ContractCode contractCode;

	/**
	 * 打刻カード番号 カード番号(old)
	 */
	@Getter
	private final StampNumber cardNumber;

	/**
	 * 打刻日時 年月日 (old) + 勤怠時刻 (old)
	 * 
	 */
	@Getter
	private final GeneralDateTime stampDateTime;

	/**
	 * 打刻する方法
	 */
	@Getter
	private final Relieve relieve;

	/**
	 * 打刻種類
	 */
	@Getter
	private final StampType type;

	/**
	 * 実績への反映内容
	 */
	@Getter
	private final RefectActualResult refActualResults;

	/**
	 * 打刻反映状態
	 */
	@Getter
	private ImprintReflectionState imprintReflectionStatus;

	/**
	 * 打刻位置情報
	 */
	@Getter
	private final Optional<GeoCoordinate> locationInfor;

	// tạo tạm để lưu biến TimeWithDayAttr
	@Getter
	private Optional<AttendanceTime> attendanceTime = Optional.empty();

	/**
	 * [C-1] 初回打刻データを作成する
	 * @param contractCode
	 * @param cardNumber
	 * @param stampDateTime
	 * @param relieve
	 * @param type
	 * @param refActualResults
	 * @param locationInfor
	 */
	public Stamp(ContractCode contractCode, StampNumber cardNumber, GeneralDateTime stampDateTime, Relieve relieve,
			StampType type, RefectActualResult refActualResults, Optional<GeoCoordinate> locationInfor) {
		super();
		this.contractCode = contractCode; //ver2　属性追加
		this.cardNumber = cardNumber;
		this.stampDateTime = stampDateTime;
		this.relieve = relieve;
		this.type = type;
		this.refActualResults = refActualResults;
		this.imprintReflectionStatus = new ImprintReflectionState(false, Optional.empty());
		this.locationInfor = locationInfor;
	}
	
	/**
	 * [2] 勤怠打刻に変換する
	 * @param referenceDate
	 */
	public WorkTimeInformation convertToAttendanceStamp(GeneralDate date) {

		// 打刻。打刻日時から時刻（日区分付き）を算出する
		long diffTime = this.stampDateTime.date().getTime() - date.date().getTime();
		int diffMin   = (int) (diffTime / (60 * 1000));
		
		// 打刻方法を打刻元情報に変換する
		ReasonTimeChange reasonTimeChange = this.relieve.convertStampmethodtostampSourceInfo(date);
		
		// 勤怠打刻を作成する
		WorkTimeInformation workTimeInformation = new WorkTimeInformation(reasonTimeChange, new TimeWithDayAttr(diffMin));
		
		return workTimeInformation;
	}

	public void setAttendanceTime(AttendanceTime attendanceTime) {
		this.attendanceTime = Optional.ofNullable(attendanceTime);
	}

	public String retriveKey() {
		return this.getCardNumber().v() + this.getStampDateTime().toString();
	}

	public void setReflectedCategory(boolean reflectedCategory) {
		this.imprintReflectionStatus.setReflectedCategory(reflectedCategory);
	}

	@Override
	public Stamp clone() {
		return new Stamp(new ContractCode(contractCode.v()), new StampNumber(cardNumber.v()), stampDateTime,
				relieve.clone(), type.clone(), refActualResults.clone(), reflectedCategory,
				locationInfor.map(x -> new GeoCoordinate(x.getLatitude(), x.getLongitude())),
				attendanceTime.map(x -> new AttendanceTime(x.v())));
	}
}
