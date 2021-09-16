package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;

/**
 * VO : 打刻する方法
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻する方法
 * @author tutk
 *
 */
public class Relieve implements DomainValue, Cloneable{

	/**
	 * 認証方法
	 * 打刻方法 1 old
	 */
	@Getter
	private final AuthcMethod authcMethod;
	
	/**
	 * 打刻手段
	 * 打刻方法 2 old
	 */
	@Getter
	private final StampMeans stampMeans;

	public Relieve(AuthcMethod authcMethod, StampMeans stampMeans) {
		super();
		this.authcMethod = authcMethod;
		this.stampMeans = stampMeans;
	}
	
	// 「１」打刻方法を打刻元情報に変換する
	// thuật toán này tạm thời fix cứng output( xác nhận với Tín rồi).
	public ReasonTimeChange convertStampmethodtostampSourceInfo(GeneralDate referenceDate) {
		
		// 「打刻.打刻する方法」を確認する 
		
		// 時刻変更理由を判断
		ReasonTimeChange reasonTimeChange =  new ReasonTimeChange(TimeChangeMeans.REAL_STAMP, Optional.of(EngravingMethod.TIME_RECORD_ID_INPUT));
		return reasonTimeChange;
	}

	@Override
	public Relieve clone() {
		return new Relieve(AuthcMethod.valueOf(authcMethod.value), StampMeans.valueOf(stampMeans.value));
	}
	
	
	
}
