package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;

/**
 * 処理単位分割日（子の看護介護休暇）
  * @author yuri_tamakoshi
 */
@Getter
@Setter
public class DividedDayEachProcess {

	/** 暫定データ */
	private List<TempChildCareNurseManagement> interimDate;
	/** 終了日 */
	private NextDayAfterPeriodEndWork endDate;
	/** 年月日 */
	private GeneralDate ymd;
	/** 本年か翌年か */
	private YearAtr yearAtr;

	/**
	 * コンストラクタ
	 */
	public DividedDayEachProcess(){
		this.interimDate = new ArrayList<>();
		this.endDate = new NextDayAfterPeriodEndWork();
		this.ymd = GeneralDate.today();
		this.yearAtr = YearAtr.THIS_YEAR;
	}
	/**
	 * ファクトリー
	 * @param interimDate 暫定データ
	 * @param endDate 終了日
	 * @param ymd 年月日
	 * @param yearAtr 本年か翌年か
	 * @return 処理単位分割日（子の看護介護休暇）
	 */
	public static DividedDayEachProcess of (
			List<TempChildCareNurseManagement> interimDate,
			NextDayAfterPeriodEndWork endDate,
			GeneralDate ymd,
			YearAtr yearAtr) {

		DividedDayEachProcess domain = new DividedDayEachProcess();
		domain.interimDate = interimDate;
		domain.endDate = endDate;
		domain.ymd = ymd;
		domain.yearAtr = yearAtr;
		return domain;
	}
	
	/*
	 * 終了日期間として扱う
	 */
	public void treatAsPeriodEnd(){
		this.setEndDate(NextDayAfterPeriodEndWork.of(true, this.getEndDate().isNextPeriodEndAtr()));
	}
	
	
	
}
