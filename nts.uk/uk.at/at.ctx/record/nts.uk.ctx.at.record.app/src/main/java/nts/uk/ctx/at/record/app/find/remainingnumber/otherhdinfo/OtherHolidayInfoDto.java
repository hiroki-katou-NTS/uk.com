package nts.uk.ctx.at.record.app.find.remainingnumber.otherhdinfo;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.ExcessLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.publicholiday.PublicHolidayRemain;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtherHolidayInfoDto extends PeregDomainDto{
	
	// 代休残数
	@PeregItem("IS00366")
	private BigDecimal remainNumber;
	
	// 振休残数
	@PeregItem("IS00368")
	private BigDecimal remainsLeft;
	
	// 公休残数
	@PeregItem("IS00369")
	private BigDecimal pubHdremainNumber;
	
	// 60H超休管理
	@PeregItem("IS00370")
	private int useAtr;
	
	// 発生単位
	@PeregItem("IS00371")
	private int occurrenceUnit;
	
	// 精算方法
	@PeregItem("IS00372")
	private int paymentMethod;
		
	// 60H超休残数
	@PeregItem("IS00374")
	private int extraHours;
	
	public static OtherHolidayInfoDto createFromDomain(Optional<PublicHolidayRemain> pubHDRemain, Optional<ExcessLeaveInfo> exLeavInfo){
		OtherHolidayInfoDto dto = new OtherHolidayInfoDto();
		if (pubHDRemain.isPresent()){
			dto.pubHdremainNumber = pubHDRemain.get().getRemainNumber().v();
		}
		if (exLeavInfo.isPresent()){
			dto.useAtr = exLeavInfo.get().getUseAtr().value;
			dto.occurrenceUnit = exLeavInfo.get().getOccurrenceUnit().v();
			dto.paymentMethod = exLeavInfo.get().getPaymentMethod().value;
		}
		return dto;
	}

}
