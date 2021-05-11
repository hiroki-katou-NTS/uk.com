package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import lombok.Data;
import java.util.Optional;


/**
 * 起算日からの休暇情報
 * @author yuri_tamakoshi
 */
@Data
public class ChildCareNurseStartdateDaysInfoExport {
	/** 子の看護休暇情報（本年）*/
	private ChildCareNurseStartdateInfoExport thisYear;
	/** 子の看護休暇情報（翌年）*/
	private Optional<ChildCareNurseStartdateInfoExport> nextYear;

	/**
	 * コンストラクタ　ChildCareNurseStartdateDaysInfoExport
	 */
	public ChildCareNurseStartdateDaysInfoExport(){
		this.thisYear = new ChildCareNurseStartdateInfoExport();
		this.nextYear = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param thisYear 子の看護休暇情報（本年）
	 * @param nextYear 子の看護休暇情報（翌年）
	 * @return 起算日からの休暇情報
	 */
	public static ChildCareNurseStartdateDaysInfoExport of(
			ChildCareNurseStartdateInfoExport thisYear,
			Optional <ChildCareNurseStartdateInfoExport> nextYear){

		ChildCareNurseStartdateDaysInfoExport export = new ChildCareNurseStartdateDaysInfoExport();
		export.thisYear = thisYear;
		export.nextYear = nextYear;
		return export;
	}
}
