package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 暫定年休管理データ（new）
 */
@Getter
@Setter
public class TempAnnualLeaveMngs extends InterimRemain{
	/** 勤務種類 */
	private WorkTypeCode workTypeCode;
	/** 年休使用数 */
	private LeaveUsedNumber usedNumber;
	/** 時間休暇種類 */
	private  Optional<DigestionHourlyTimeType> appTimeType;

	/**
	 * コンストラクタ
	 */
	public TempAnnualLeaveMngs(){
		super();
		this.workTypeCode = new WorkTypeCode("");
		this.usedNumber = new LeaveUsedNumber();
		this.appTimeType = Optional.empty();
	}


	/**
	 * ファクトリー
	 * @param workTypeCode 勤務種類
	 * @param usedNumber 使用数
	 * @param appTimeType 時間休暇種類
	 * @return 暫定子の看護管理データ
	 */
	public static TempAnnualLeaveMngs of(
			String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr, RemainType remainType, RemainAtr remainAtr,
			WorkTypeCode workTypeCode,
			LeaveUsedNumber usedNumber,
			Optional<DigestionHourlyTimeType>  appTimeType) {

		TempAnnualLeaveMngs domain = new TempAnnualLeaveMngs();
		domain.workTypeCode = workTypeCode;
		domain.usedNumber = usedNumber;
		domain.appTimeType = appTimeType;
		return domain;
	}

	public TempAnnualLeaveMngs(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr,
			RemainType remainType, WorkTypeCode workTypeCode, LeaveUsedNumber usedNumber,
			Optional<DigestionHourlyTimeType> appTimeType) {
		super(remainManaID, sID, ymd, creatorAtr, remainType);
		this.workTypeCode = workTypeCode;
		this.usedNumber = usedNumber;
		this.appTimeType = appTimeType;
	}

	public boolean isHourlyTimeType(){
		if(!this.getAppTimeType().isPresent())
			return false;
		return this.getAppTimeType().get().isHourlyTimeType();
	}

	public Optional<AppTimeType> getAppTimeTypeEnum(){
		if(this.getAppTimeType().isPresent())
			return this.getAppTimeType().get().getAppTimeType();
		return Optional.empty();
	}

}

