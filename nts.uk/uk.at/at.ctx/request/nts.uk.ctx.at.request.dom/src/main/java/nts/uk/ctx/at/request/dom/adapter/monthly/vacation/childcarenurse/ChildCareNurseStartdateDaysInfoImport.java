package nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse;

import lombok.Data;

import java.util.Optional;


/**
 * 起算日からの休暇情報
 * @author yuri_tamakoshi
 */
@Data
public class ChildCareNurseStartdateDaysInfoImport {
	/** 子の看護休暇情報（本年）*/
	private ChildCareNurseStartdateInfoImport thisYear;
	/** 子の看護休暇情報（翌年）*/
	private Optional<ChildCareNurseStartdateInfoImport> nextYear;

	/**
	 * コンストラクタ　ChildCareNurseStartdateDaysInfoImport
	 */
	public ChildCareNurseStartdateDaysInfoImport(){
		this.thisYear = new ChildCareNurseStartdateInfoImport();
		this.nextYear = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param thisYear 子の看護休暇情報（本年）
	 * @param nextYear 子の看護休暇情報（翌年）
	 * @return 起算日からの休暇情報
	 */
	public static ChildCareNurseStartdateDaysInfoImport of(
			ChildCareNurseStartdateInfoImport thisYear,
			Optional <ChildCareNurseStartdateInfoImport> nextYear){

		ChildCareNurseStartdateDaysInfoImport domain = new ChildCareNurseStartdateDaysInfoImport();
		domain.thisYear = thisYear;
		domain.nextYear = nextYear;
		return domain;
	}

}
