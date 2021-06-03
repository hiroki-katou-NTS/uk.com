package nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim;

import java.io.Serializable;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
//import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;

/**
 * 暫定60H超休管理データ 
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class TmpHolidayOver60hMng extends InterimRemain implements Serializable {

	/**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;
    
	/** 使用時間 */
	private Optional<UseTime> useTime;
	
	/** 時間休暇種類 */
	private Optional<DigestionHourlyTimeType> appTimeType;
	
	/**
	 * コンストラクタ
	 * @param sID
	 * @param ymd
	 * @param remainManaID
	 */
	public TmpHolidayOver60hMng(String sID, GeneralDate ymd, String remainManaID) {
		super(sID, ymd, remainManaID);
	}
	
	/**
	 * コンストラクタ
	 * @param employeeId
	 * @param end
	 */
	public TmpHolidayOver60hMng(String employeeId, GeneralDate end) {
		super(employeeId, end);
	}

	public TmpHolidayOver60hMng(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr,
			RemainType remainType, Optional<UseTime> useTime, Optional<DigestionHourlyTimeType> appTimeType) {
		super(remainManaID, sID, ymd, creatorAtr, remainType);
		this.useTime = useTime;
		this.appTimeType = appTimeType;
	}
	
	
	
//	/**
//	 * 等しいかどうか
//	 * @param target 暫定60H超休管理データWORK
//	 * @return true：等しい、false：等しくない
//	 */
//	public boolean equals(TmpHolidayOver60hMngWork target){
//		if (!this.ymd.equals(target.ymd)) return false;
//		if (this.remainAtr != target.remainAtr) return false;
//		if (this.creatorAtr == CreateAtr.FLEXCOMPEN){
//			if (target.creatorAtr != CreateAtr.FLEXCOMPEN) return false;
//		}
//		else {
//			if (target.creatorAtr == CreateAtr.FLEXCOMPEN) return false;
//		}
//		return true;
//	}
}
