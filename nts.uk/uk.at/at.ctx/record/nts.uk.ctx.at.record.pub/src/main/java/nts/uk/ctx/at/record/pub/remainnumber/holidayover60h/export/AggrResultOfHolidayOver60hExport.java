package nts.uk.ctx.at.record.pub.remainnumber.holidayover60h.export;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.AggrResultOfHolidayOver60h;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hError;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hGrantRemaining;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

/**
 * 60H超休の集計結果
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class AggrResultOfHolidayOver60hExport{

	/**
	 * 使用回数
	*/
	private UsedTimes usedTimes;

	/**
	 * 60H超休エラー情報
	 * Enumの意味は、HolidayOver60hErrorを参照する
	 */
	private List<Integer> holidayOver60hErrors;

	/**
	 * 60H超休情報（期間終了日時点）
	 */
	private HolidayOver60hInfoExport asOfPeriodEnd;

	/**
	 * 60H超休情報（期間終了日の翌日開始時点）
	 */
	private HolidayOver60hInfoExport asOfStartNextDayOfPeriodEnd;

	/**
	 * 60H超休情報（消滅）
	 */
	private Optional<HolidayOver60hInfoExport> lapsed;

	/**
	 * コンストラクタ
	 */
	public AggrResultOfHolidayOver60hExport(){
		this.usedTimes = new UsedTimes(0);
		this.holidayOver60hErrors = new ArrayList<Integer>();
		this.asOfPeriodEnd = new HolidayOver60hInfoExport();
		this.asOfStartNextDayOfPeriodEnd = new HolidayOver60hInfoExport();
		this.lapsed = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param asOfPeriodEnd 60H超休情報（期間終了日時点）
	 * @param asOfStartNextDayOfPeriodEnd 60H超休情報（期間終了日の翌日開始時点）
	 * @param lapsed 60H超休情報（消滅）
	 * @param holidayOver60hErrors 60H超休エラー情報
	 * @return 60H超休の集計結果
	 */
	public static AggrResultOfHolidayOver60hExport of(
			UsedTimes usedTimes,
			HolidayOver60hInfoExport asOfPeriodEnd,
			HolidayOver60hInfoExport asOfStartNextDayOfPeriodEnd,
			Optional<HolidayOver60hInfoExport> lapsed,
			List<Integer> holidayOver60hErrors){

		AggrResultOfHolidayOver60hExport domain = new AggrResultOfHolidayOver60hExport();
		domain.usedTimes = usedTimes;
		domain.holidayOver60hErrors = holidayOver60hErrors;
		domain.asOfPeriodEnd = asOfPeriodEnd;
		domain.asOfStartNextDayOfPeriodEnd = asOfStartNextDayOfPeriodEnd;
		domain.lapsed = lapsed;

		return domain;
	}

	/**
	   * ドメインから変換
	 * @param AggrResultOfHolidayOver60h
	 * @return
	 */
	static public AggrResultOfHolidayOver60hExport of(AggrResultOfHolidayOver60h aggrResultOfHolidayOver60h) {

		AggrResultOfHolidayOver60hExport export = new AggrResultOfHolidayOver60hExport();

		// 使用回数
		export.usedTimes = new UsedTimes(aggrResultOfHolidayOver60h.getUsedTimes().v());

		// 60H超休エラー情報
		ArrayList<Integer> errorList = new ArrayList<Integer>();
		for(HolidayOver60hError holidayOver60hError : aggrResultOfHolidayOver60h.getHolidayOver60hErrors()) {
			errorList.add(holidayOver60hError.value);
		}

		// 60H超休情報（期間終了日時点）
		export.setAsOfPeriodEnd(
			HolidayOver60hInfoExport.of(aggrResultOfHolidayOver60h.getAsOfPeriodEnd()));

		// 60H超休情報（期間終了日の翌日開始時点）
		export.setAsOfStartNextDayOfPeriodEnd(
			HolidayOver60hInfoExport.of(aggrResultOfHolidayOver60h.getAsOfStartNextDayOfPeriodEnd()));

		// 60H超休情報（消滅）
		export.setLapsed(
			aggrResultOfHolidayOver60h.getLapsed().map(c->HolidayOver60hInfoExport.of(c)));

		return export;
	}

	/**
	   * ドメインへ変換
	 * @param AggrResultOfHolidayOver60hExport
	 * @return
	 */
	static public AggrResultOfHolidayOver60h toDomain(
			AggrResultOfHolidayOver60hExport aggrResultOfHolidayOver60hExport) {

		AggrResultOfHolidayOver60h domain = new AggrResultOfHolidayOver60h();

		// 使用回数
		domain.setUsedTimes(new UsedTimes(aggrResultOfHolidayOver60hExport.getUsedTimes().v()));

		// 60H超休エラー情報
		ArrayList<HolidayOver60hError> errorList = new ArrayList<HolidayOver60hError>();
		for(Integer holidayOver60hError : aggrResultOfHolidayOver60hExport.getHolidayOver60hErrors()) {
			errorList.add(EnumAdaptor.valueOf(holidayOver60hError, HolidayOver60hError.class));
		}
		domain.setHolidayOver60hErrors(errorList);

		// 60H超休情報（期間終了日時点）
		domain.setAsOfPeriodEnd(
			HolidayOver60hInfoExport.toDomain(aggrResultOfHolidayOver60hExport.getAsOfPeriodEnd()));

		// 60H超休情報（期間終了日の翌日開始時点）
		domain.setAsOfStartNextDayOfPeriodEnd(
			HolidayOver60hInfoExport.toDomain(aggrResultOfHolidayOver60hExport.getAsOfStartNextDayOfPeriodEnd()));

		// 60H超休情報（消滅）
		domain.setLapsed(
			aggrResultOfHolidayOver60hExport.getLapsed().map(c->HolidayOver60hInfoExport.toDomain(c)));

		return domain;
	}

}
