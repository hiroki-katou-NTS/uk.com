package nts.uk.ctx.at.function.dom.holidaysremaining;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * @author thanh.tq 帳票に出力する項目
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ItemOutputForm extends DomainObject {

	// 時間外超過
	private NursingCareLeave nursingcareLeave;

	// 代休
	private ItemsOutputtedAlternate substituteHoliday;

	// 公休
	private ItemsPublicOutput holidays;

	// 子の看護休暇
	private ChildNursingLeave childNursingVacation;

	// 年休
	private YearlyItemsOutput annualHoliday;

	// 振休
	private PauseItem pause;

	// 時間外超過
	private Overtime outOfTime;

	// 積立年休
	private YearlyReserved yearlyReserved;

	// 特別休暇
	private List<SpecialHoliday> specialHoliday;

	public ItemOutputForm(NursingCareLeave nursingcareLeave, ItemsOutputtedAlternate substituteHoliday,
			ItemsPublicOutput holidays, ChildNursingLeave childNursingVacation, YearlyItemsOutput annualHoliday,
			PauseItem pause, YearlyReserved yearlyReserved) {
		super();
		this.nursingcareLeave = nursingcareLeave;
		this.substituteHoliday = substituteHoliday;
		this.holidays = holidays;
		this.childNursingVacation = childNursingVacation;
		this.annualHoliday = annualHoliday;
		this.pause = pause;
		this.yearlyReserved = yearlyReserved;
	}

}
