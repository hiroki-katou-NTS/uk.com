package nts.uk.screen.at.app.query.kdp.kdp001.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampDataOfEmployeesDto;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampToSuppressDto;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmpInfoPotalStampDto {

	/**
	 * ・社員の打刻データ(Opt) ← 社員の打刻データ EA3782
	 */
	private StampDataOfEmployeesDto stampDataOfEmp;
	/**
	 * ・抑制する打刻(Opt)
	 */
	private StampToSuppressDto StampToSuppress;
}
