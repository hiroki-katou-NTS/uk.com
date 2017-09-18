package nts.uk.ctx.bs.employee.dom.position.jobtitlemaster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Position extends AggregateRoot {

	/** The CompanyId 会社ID */
	private String companyId;

	/** The PositionId */
	private String positionId;

	/** The SequenceCode */
	private SequenceCode sequenceCode;

	/** The PositionCode */
	private PositionCode positionCode;

	/** The PositionName */
	private PositionName positionName;
	
	private JobStudies jobStudies;

	public static Position creatFromJavaType(String companyId, String positionId, String sequenceCode,
			String positionCode, String positionName ,JobStudies jobStudies) {
		return new Position(companyId, positionId, new SequenceCode(sequenceCode), new PositionCode(positionCode),
				new PositionName(positionName), jobStudies);

	}
}
