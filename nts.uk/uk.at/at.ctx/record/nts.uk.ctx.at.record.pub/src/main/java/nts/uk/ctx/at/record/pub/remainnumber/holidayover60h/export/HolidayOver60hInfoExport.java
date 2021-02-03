package nts.uk.ctx.at.record.pub.remainnumber.holidayover60h.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hGrantRemaining;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

/**
 * ６０H超休情報
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class HolidayOver60hInfoExport {

	/**
	 * 年月日
	*/
	private GeneralDate ymd;

	/**
	 * 残数
	*/
	private HolidayOver60hRemainingNumberExport remainingNumber;

	/**
	 * 付与残数データ
	*/
	private List<HolidayOver60hGrantRemainingExport> grantRemainingList;

	/**
	 * コンストラクタ
	 */
	public HolidayOver60hInfoExport(){
		this.ymd = GeneralDate.min();
		this.remainingNumber = new HolidayOver60hRemainingNumberExport();
		this.grantRemainingList = new ArrayList<HolidayOver60hGrantRemainingExport>();
	}

	/**
	   * ドメインから変換
	 * @param holidayOver60hInfo
	 * @return
	 */
	static public HolidayOver60hInfoExport of(HolidayOver60hInfo holidayOver60hInfo) {

		HolidayOver60hInfoExport export = new HolidayOver60hInfoExport();

		//comment 年月日
		export.ymd = holidayOver60hInfo.getYmd();

		//comment 残数
		export.remainingNumber = HolidayOver60hRemainingNumberExport.of(holidayOver60hInfo.getRemainingNumber());

		//comment 付与残数データ
		ArrayList<HolidayOver60hGrantRemainingExport> grantRemainingList
			= new ArrayList<HolidayOver60hGrantRemainingExport>();
		for( HolidayOver60hGrantRemaining holidayOver60hGrantRemaining
				: holidayOver60hInfo.getGrantRemainingList()) {
			grantRemainingList.add(HolidayOver60hGrantRemainingExport.of(holidayOver60hGrantRemaining));
		}
		export.grantRemainingList = grantRemainingList;

		return export;
	}

	/**
	   * ドメインへ変換
	 * @param holidayOver60hInfoExport
	 * @return
	 */
	static public HolidayOver60hInfo toDomain(
			HolidayOver60hInfoExport holidayOver60hInfoExport) {

		HolidayOver60hInfo domain = new HolidayOver60hInfo();

		//comment 年月日
		domain.setYmd(holidayOver60hInfoExport.getYmd());

		//comment 残数
		domain.setRemainingNumber(
				HolidayOver60hRemainingNumberExport.toDomain(holidayOver60hInfoExport.getRemainingNumber()));

		//comment 付与残数データ
		ArrayList<HolidayOver60hGrantRemaining> grantRemainingList
			= new ArrayList<HolidayOver60hGrantRemaining>();
		for( HolidayOver60hGrantRemainingExport holidayOver60hGrantRemainingExport
				: holidayOver60hInfoExport.getGrantRemainingList()) {
			grantRemainingList.add(HolidayOver60hGrantRemainingExport.toDomain(holidayOver60hGrantRemainingExport));
		}
		domain.setGrantRemainingList(grantRemainingList);

		return domain;
	}

}
