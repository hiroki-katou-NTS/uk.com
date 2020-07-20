package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.PerServiceLengthTableCD;
import nts.uk.shr.com.context.AppContexts;

@Getter
// domain name: 年休社員基本情報
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveEmpBasicInfo extends AggregateRoot implements SerializableWithOptional {
	
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 年間所定労働日数
	 */
	private transient Optional<WorkingDayPerYear> workingDaysPerYear;

	/**
	 * 導入前労働日数
	 */
	private transient Optional<WorkingDayBeforeIntro> workingDayBeforeIntroduction;

	/**
	 * 付与ルール
	 */
	private AnnualLeaveGrantRule grantRule;

	public static AnnualLeaveEmpBasicInfo createFromJavaType(String employeeId, Integer workingDaysPerYear,
			Integer workingDayBeforeIntro, String grantTableCode, GeneralDate grantStandardDate) {
		AnnualLeaveEmpBasicInfo domain = new AnnualLeaveEmpBasicInfo();
		domain.companyId = AppContexts.user().companyId();
		domain.employeeId = employeeId;
		domain.workingDaysPerYear = workingDaysPerYear != null ? Optional.of(new WorkingDayPerYear(workingDaysPerYear))
				: Optional.empty();
		domain.workingDayBeforeIntroduction = workingDayBeforeIntro != null
				? Optional.of(new WorkingDayBeforeIntro(workingDayBeforeIntro)) : Optional.empty();
		domain.grantRule = new AnnualLeaveGrantRule(new PerServiceLengthTableCD(grantTableCode), grantStandardDate);
		return domain;
	}
	
	public static AnnualLeaveEmpBasicInfo createFromJavaType(String employeeId, BigDecimal workingDaysPerYear,
			BigDecimal workingDayBeforeIntro, String grantTableCode, GeneralDate grantStandardDate) {
		return createFromJavaType(employeeId, toInteger(workingDaysPerYear), toInteger(workingDayBeforeIntro),
				grantTableCode, grantStandardDate);
	}
	
	private static Integer toInteger(BigDecimal bigNumber) {
		return bigNumber != null ? bigNumber.intValue() : null;
	}

	private void writeObject(ObjectOutputStream stream){	
		writeObjectWithOptional(stream);
	}	
	private void readObject(ObjectInputStream stream){	
		readObjectWithOptional(stream);
	}	

}
