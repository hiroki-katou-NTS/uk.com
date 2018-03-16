package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.List;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * The Class DivergenceTimeInputMethodDto.
 */
@Data
public class DivergenceTimeInputMethodDto {

	/** The divergence time no. */

	private int divergenceTimeNo;

	/** The c id. */

	private String companyId;

	/** The Use classification. */

	private int divergenceTimeUseSet;

	/** The divergence time name. */

	private String divergenceTimeName;

	/** The divergence type. */

	private int divergenceType;

	/** The divergence time error cancel method. */

	private boolean reasonInput;

	/** The reason select. */
	private boolean reasonSelect;

	/** The divergence reason inputed. */

	private boolean divergenceReasonInputed;

	/** The divergence reason selected. */

	private boolean divergenceReasonSelected;

	/** Attendance Item list. */
	private List<Integer> attendanceId;

	/**
	 * Instantiates a new divergence time input method dto.
	 *
	 * @param divergenceTimeNo
	 *            the divergence time no
	 * @param companyId
	 *            the company id
	 * @param divTimeUseSet
	 *            the div time use set
	 * @param divergenceTimeName
	 *            the divergence time name
	 * @param divType
	 *            the div type
	 * @param reasonInput
	 *            the reason input
	 * @param reasonSelect
	 *            the reason select
	 * @param divergenceReasonInputed
	 *            the divergence reason inputed
	 * @param divergenceReasonSelected
	 *            the divergence reason selected
	 * @param attendanceName
	 *            the attendance name
	 */
	public DivergenceTimeInputMethodDto(){
		super();
	}
	public DivergenceTimeInputMethodDto(int divergenceTimeNo, String companyId, int divTimeUseSet,
			String divergenceTimeName, int divType, boolean reasonInput, boolean reasonSelect,
			boolean divergenceReasonInputed, boolean divergenceReasonSelected, List<Integer> attendanceId) {
		super();
		this.divergenceTimeNo = divergenceTimeNo;
		this.companyId = companyId;
		this.divergenceTimeUseSet = divTimeUseSet;
		this.divergenceTimeName = divergenceTimeName;
		this.divergenceType = divType;
		this.reasonInput = reasonInput;
		this.reasonSelect = reasonSelect;
		this.divergenceReasonInputed = divergenceReasonInputed;
		this.divergenceReasonSelected = divergenceReasonSelected;
		this.attendanceId = attendanceId;
	}

}
