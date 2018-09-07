package nts.uk.ctx.pr.core.dom.salary;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 勤怠締め年月日
 */
@AllArgsConstructor
@Getter
public class CloseDate extends DomainObject {

	/**
	 * 処理区分NO
	 */
	private int processCateNo;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 勤怠締め日
	 */
	private int timeCloseDate;

	/**
	 * 基準月
	 */
	private Optional<SocialInsuColleMonth> baseMonth;

	/**
	 * 基準年
	 */
	private Optional<YearSelectClassification> baseYear;

	/**
	 * 基準日
	 */
	private Optional<DateSelectClassification> refeDate;

	public CloseDate(int processCateNo, String cid, int timeCloseDate, int baseMonth, int baseYear, int refeDate) {
		this.cid = cid;
		this.processCateNo = processCateNo;
		this.timeCloseDate = timeCloseDate;
		this.baseMonth = Optional.ofNullable(EnumAdaptor.valueOf(baseMonth, SocialInsuColleMonth.class));
		this.baseYear = Optional.ofNullable(EnumAdaptor.valueOf(baseYear, YearSelectClassification.class));
		this.refeDate = Optional.ofNullable(EnumAdaptor.valueOf(refeDate, DateSelectClassification.class));
	}

}
