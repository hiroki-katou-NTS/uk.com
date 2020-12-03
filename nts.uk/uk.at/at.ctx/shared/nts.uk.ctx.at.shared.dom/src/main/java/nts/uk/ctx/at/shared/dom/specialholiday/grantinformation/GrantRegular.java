package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.Optional;

import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantDeadline;

/**
 * 付与・期限情報
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GrantRegular extends DomainObject {

	/** 付与するタイミングの種類 */
	private TypeTime typeTime;

	/** 付与基準日 */
	private Optional<GrantDate> grantDate;

	/** 指定日付与 */
	private Optional<FixGrantDate> fixGrantDate;

	/** 付与日テーブル参照付与 */
	private Optional<GrantDeadline> grantPeriodic;

	/** 期間付与 */
	private Optional<PeriodGrantDate> periodGrantDate;

//
//	/** 会社ID */
//	private String companyId;
//
//	/** 特別休暇コード */
//	private SpecialHolidayCode specialHolidayCode;
//
//	/** 取得できなかった端数は消滅する */
//	private boolean allowDisappear;
//
//	/** 取得できなかった端数は消滅する */
//	private GrantTime grantTime;

	@Override
	public void validate() {
		super.validate();
	}


	public Optional<Integer> getLimitAccumulationDays() {
		if(this.typeTime==TypeTime.REFER_GRANT_DATE_TBL) {
			if(!this.getGrantPeriodic().isPresent())return Optional.empty();
			if(!this.getGrantPeriodic().get().getLimitAccumulationDays().isPresent())return Optional.empty();
			if(!this.getGrantPeriodic().get().getLimitAccumulationDays().get().getLimitAccumulationDays().isPresent())return Optional.empty();

			return Optional.of(this.getGrantPeriodic().get().getLimitAccumulationDays().get().getLimitAccumulationDays().get().v());
		}
		if(this.typeTime==TypeTime.GRANT_SPECIFY_DATE) {
			if(!this.getFixGrantDate().isPresent())return Optional.empty();
			if(!this.getFixGrantDate().get().getGrantPeriodic().getLimitAccumulationDays().isPresent())return Optional.empty();
			if(!this.getFixGrantDate().get().getGrantPeriodic().getLimitAccumulationDays().get().getLimitAccumulationDays().isPresent())return Optional.empty();

			return Optional.of(this.getFixGrantDate().get().getGrantPeriodic().getLimitAccumulationDays().get().getLimitAccumulationDays().get().v());
		}
		return Optional.empty();
	}

}
