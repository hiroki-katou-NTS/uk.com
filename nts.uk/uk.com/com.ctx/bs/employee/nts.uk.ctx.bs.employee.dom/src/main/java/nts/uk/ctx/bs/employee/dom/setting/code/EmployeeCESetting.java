package nts.uk.ctx.bs.employee.dom.setting.code;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@AllArgsConstructor
public class EmployeeCESetting extends AggregateRoot {

	private String companyId;

	private EmployeeCEMethodAttr ceMethodAtr;

	private NumberOfDigit digitNumb;

	public static EmployeeCESetting createFromJavaType(String companyId, int ceMethodAtr, int digitNumb) {
		return new EmployeeCESetting(companyId, EnumAdaptor.valueOf(ceMethodAtr, EmployeeCEMethodAttr.class),
				new NumberOfDigit(digitNumb));
	}
	
	public String editEmployeeCode(String employeeCode) {
		switch (this.ceMethodAtr) {
		case ZEROBEFORE:
			return StringUtils.leftPad(employeeCode, this.digitNumb.v(), "0");
		case ZEROAFTER:
			employeeCode = StringUtils.rightPad(employeeCode, this.digitNumb.v(), "0");
		case SPACEBEFORE:
			employeeCode = StringUtils.leftPad(employeeCode, this.digitNumb.v());
		case SPACEAFTER:
			employeeCode = StringUtils.rightPad(employeeCode, this.digitNumb.v());
		default:
			throw new RuntimeException("unknown enum value:" + this.ceMethodAtr);
		}
	}
}
