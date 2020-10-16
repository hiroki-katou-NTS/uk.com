package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import java.util.Optional;


/**
 * 起算日からの休暇情報
 * @author yuri_tamakoshi
 */
public class ChildCareNurseStartdateDaysInfo {
	/** 子の看護休暇情報（本年）*/
	private ChildCareNurseStartdateInfo thisYear;
	/** 子の看護休暇情報（翌年）*/
	private Optional<ChildCareNurseStartdateInfo> nextYear;

	/**
	 * コンストラクタ　ChildCareNurseStartdateDaysInfo
	 */
	public ChildCareNurseStartdateDaysInfo(){
		this.thisYear = new ChildCareNurseStartdateInfo();
		this.nextYear = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param 子の看護休暇情報（本年）
	 * @param 子の看護休暇情報（翌年）
	 * @return 起算日からの休暇情報
	 */
	public static ChildCareNurseStartdateDaysInfo of(
			ChildCareNurseStartdateInfo thisYear,
			Optional <ChildCareNurseStartdateInfo> nextYear){

		ChildCareNurseStartdateDaysInfo domain = new ChildCareNurseStartdateDaysInfo();
		domain.thisYear = thisYear;
		domain.nextYear = nextYear;
		return domain;
	}

}
