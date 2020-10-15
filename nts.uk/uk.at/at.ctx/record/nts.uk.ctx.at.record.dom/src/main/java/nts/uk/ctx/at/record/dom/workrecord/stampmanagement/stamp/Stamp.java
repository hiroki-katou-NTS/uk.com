package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * AR : 打刻
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻
 * 
 * @author tutk
 *
 */
@AllArgsConstructor
public class Stamp implements DomainAggregate {

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
	 * 反映済み区分
	 */
	@Getter
	private boolean reflectedCategory;

	/**
	 * 打刻場所情報
	 */
	@Getter
	private final Optional<StampLocationInfor> locationInfor;

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
			StampType type, RefectActualResult refActualResults, Optional<StampLocationInfor> locationInfor) {
		super();
		this.contractCode = contractCode; //ver2　属性追加
		this.cardNumber = cardNumber;
		this.stampDateTime = stampDateTime;
		this.relieve = relieve;
		this.type = type;
		this.refActualResults = refActualResults;
		this.reflectedCategory = false;
		this.locationInfor = locationInfor;
	}

	public void setAttendanceTime(AttendanceTime attendanceTime) {
		this.attendanceTime = Optional.ofNullable(attendanceTime);
	}

	public String retriveKey() {
		return this.getCardNumber().v() + this.getStampDateTime().toString();
	}

	public void setReflectedCategory(boolean reflectedCategory) {
		this.reflectedCategory = reflectedCategory;
	}
	
}
