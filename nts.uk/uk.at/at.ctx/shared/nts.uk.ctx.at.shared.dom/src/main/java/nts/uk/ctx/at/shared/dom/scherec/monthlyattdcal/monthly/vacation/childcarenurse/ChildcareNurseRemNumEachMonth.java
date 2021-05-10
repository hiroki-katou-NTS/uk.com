package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 子の看護休暇月別残数データ
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class ChildcareNurseRemNumEachMonth  {

	/** 本年使用数 */
	private ChildCareNurseUsedInfo thisYearUsedInfo;
	/** 合計使用数 */
	private ChildCareNurseUsedInfo usedInfo;
	/** 本年残数 */
	private ChildCareNurseRemainNumber thisYearRemainNumber;
	/** 翌年使用数 */
	private Optional<ChildCareNurseUsedInfo> nextYearUsedInfo;
	/** 翌年残数 */
	private Optional<ChildCareNurseRemainNumber> nextYearRemainNumber;

	public void calcUsedInfo() {
		if ( !nextYearUsedInfo.isPresent() ) {
			usedInfo = thisYearUsedInfo.clone();
			return;
		}

		usedInfo = new ChildCareNurseUsedInfo( )

	}

	/**
	 * コンストラクタ
	 */
	public ChildcareNurseRemNumEachMonth() {
		thisYearUsedInfo = new ChildCareNurseUsedInfo();
		usedInfo = new ChildCareNurseUsedInfo();
		thisYearRemainNumber = new ChildCareNurseRemainNumber();
		nextYearUsedInfo = Optional.empty();
		nextYearRemainNumber = Optional.empty();
	}

	/**
	 * コンストラクタ
	 */
	public ChildcareNurseRemNumEachMonth(ChildcareNurseRemNumEachMonth c) {
		thisYearUsedInfo = c.thisYearUsedInfo;
		usedInfo = c.usedInfo.clone();
		thisYearRemainNumber = c.thisYearRemainNumber.clone();
		nextYearUsedInfo = Optional.empty();
		nextYearRemainNumber = Optional.empty();
	}

	/**
	 * @param thisYearUsedInfo 本年使用数
	 * @param usedInfo 合計使用数
	 * @param thisYearRemainNumber 本年残数
	 * @param nextYearUsedInfo 翌年使用数
	 * @param nextYearRemainNumber 翌年残数
	 * @return 子の看護休暇月別残数データ
	 */
	public static ChildcareNurseRemNumEachMonth of(
			ChildCareNurseUsedInfo thisYearUsedInfo,
			ChildCareNurseUsedInfo usedInfo,
			ChildCareNurseRemainNumber thisYearRemainNumber,
			Optional<ChildCareNurseUsedInfo> nextYearUsedInfo,
			Optional<ChildCareNurseRemainNumber> nextYearRemainNumber
			){

		ChildcareNurseRemNumEachMonth domain = new ChildcareNurseRemNumEachMonth();
		domain.thisYearUsedInfo = thisYearUsedInfo;
		domain.usedInfo = usedInfo;
		domain.thisYearRemainNumber = thisYearRemainNumber;
		domain.nextYearUsedInfo = nextYearUsedInfo;
		domain.nextYearRemainNumber = nextYearRemainNumber;

		return domain;
	}
}
