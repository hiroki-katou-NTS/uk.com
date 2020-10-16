package nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto;

import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.shared.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_BUSINESS_TYPE_NAME)
public class BusinessTypeOfDailyPerforDto extends AttendanceItemCommon {

	@Override
	public String rootName() { return DAILY_BUSINESS_TYPE_NAME; }
	/***/
	private static final long serialVersionUID = 1L;

	private String employeeId;
	
	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate baseDate; 
	
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = BUSINESS_TYPE)
	@AttendanceItemValue
	private String businessTypeCode;
	
	public static BusinessTypeOfDailyPerforDto getDto(WorkTypeOfDailyPerformance domain){
		BusinessTypeOfDailyPerforDto dto = new BusinessTypeOfDailyPerforDto();
		if(domain != null){
			dto.setBusinessTypeCode(domain.getWorkTypeCode() == null ? null : domain.getWorkTypeCode().v());
			dto.setBaseDate(domain.getDate());
			dto.setEmployeeId(domain.getEmployeeId());
			dto.exsistData();
		}
		return dto;
	}
	
	@Override
	public BusinessTypeOfDailyPerforDto clone(){
		BusinessTypeOfDailyPerforDto dto = new BusinessTypeOfDailyPerforDto();
		dto.setBusinessTypeCode(businessTypeCode);
		dto.setBaseDate(workingDate());
		dto.setEmployeeId(employeeId());
		if(isHaveData()){
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.baseDate;
	}

	@Override
	public WorkTypeOfDailyPerformance toDomain(String employeeId, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		return new WorkTypeOfDailyPerformance(employeeId, date, this.businessTypeCode);
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		if (path.equals(BUSINESS_TYPE)) {
			return Optional.of(ItemValue.builder().value(businessTypeCode).valueType(ValueType.CODE));
		}
		
		return Optional.empty();	
	}

	@Override
	public boolean isRoot() { return true; }
	

	@Override
	public void set(String path, ItemValue value) {
		if (path.equals(BUSINESS_TYPE)) {
			this.businessTypeCode = value.valueOrDefault(null);
			
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		if (path.equals(BUSINESS_TYPE)) {
			return PropType.VALUE;
			
		}
		return PropType.OBJECT;
	}
}
