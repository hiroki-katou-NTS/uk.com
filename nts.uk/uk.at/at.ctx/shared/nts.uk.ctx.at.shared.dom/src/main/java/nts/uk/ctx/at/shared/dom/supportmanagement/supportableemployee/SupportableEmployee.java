package nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.gul.util.OptionalUtil;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 応援可能な社員
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援可能な社員.応援可能な社員
 * @author kumiko_otake
 */
@Getter
@AllArgsConstructor
public class SupportableEmployee implements DomainAggregate {

	/** Serializable */
	@SuppressWarnings("unused") private static final long serialVersionUID = 1L;

	/** ID **/
	private final String id;
	/** 社員ID **/
	private final EmployeeId employeeId;
	/** 応援先 **/
	private final TargetOrgIdenInfor recipient;
	/** 応援形式 **/
	private final SupportType supportType;
	/** 期間 **/
	private final DatePeriod period;
	/** 時間帯 **/
	private final Optional<TimeSpanForCalc> timespan;



	/**
	 * 終日応援として作成する
	 * @param employeeId 社員ID
	 * @param recipient 応援先
	 * @param period 期間
	 * @return 応援可能な社員
	 */
	public static SupportableEmployee createAsAllday(
			EmployeeId employeeId, TargetOrgIdenInfor recipient, DatePeriod period
	) {

		return new SupportableEmployee(
					IdentifierUtil.randomUniqueId()
				,	employeeId
				,	recipient
				,	SupportType.ALLDAY
				,	period
				,	Optional.empty()
			);

	}

	/**
	 * 時間帯応援として作成する
	 * @param employeeId 社員ID
	 * @param recipient 応援先
	 * @param date 年月日
	 * @param timespan 時間帯
	 * @return 応援可能な社員
	 */
	public static SupportableEmployee createAsTimezone(
			EmployeeId employeeId, TargetOrgIdenInfor recipient, GeneralDate date, TimeSpanForCalc timespan
	) {

		return new SupportableEmployee(
					IdentifierUtil.randomUniqueId()
				,	employeeId
				,	recipient
				,	SupportType.TIMEZONE
				,	DatePeriod.oneDay( date )
				,	Optional.of( timespan )
			);

	}



	/**
	 * 期間を変更する
	 * @param period 期間
	 * @return 応援可能な社員
	 */
	public SupportableEmployee changePeriod(DatePeriod period) {

		// 時間帯応援では期間を変更できない
		if ( this.supportType == SupportType.TIMEZONE ) {
			throw new BusinessException("Msg_2313");
		}

		// 期間を変更して返す
		return new SupportableEmployee(
					this.id
				,	this.employeeId
				,	this.recipient
				,	this.supportType
				,	period
				,	this.timespan
			);

	}

	/**
	 * 時間帯を変更する
	 * @param period 期間
	 * @return 応援可能な社員
	 */
	public SupportableEmployee changeTimespan(TimeSpanForCalc timespan) {

		// 終日応援では時間帯を設定できない
		if ( this.supportType == SupportType.ALLDAY ) {
			throw new BusinessException("Msg_2314");
		}

		// 時間帯を変更して返す
		return new SupportableEmployee(
					this.id
				,	this.employeeId
				,	this.recipient
				,	this.supportType
				,	this.period
				,	Optional.of( timespan )
			);

	}


	/**
	 * 応援チケットを作成する
	 * @param ymd 年月日
	 * @return 応援チケット
	 */
	public Optional<SupportTicket> createTicket(GeneralDate ymd) {

		if ( !this.period.contains(ymd) ) {
			return Optional.empty();
		}

		return Optional.of( new SupportTicket( this.employeeId, this.recipient, this.supportType, ymd, this.timespan ) );

	}

	/**
	 * 応援チケットに変換する
	 * @return 応援チケットリスト
	 */
	public List<SupportTicket> toTickets() {

		return this.period.stream()
				.map( this::createTicket )
				.flatMap(OptionalUtil::stream)
				.collect(Collectors.toList());

	}

}
