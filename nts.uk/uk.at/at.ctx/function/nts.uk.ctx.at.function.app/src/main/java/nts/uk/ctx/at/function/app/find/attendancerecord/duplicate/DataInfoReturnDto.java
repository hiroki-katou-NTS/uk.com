package nts.uk.ctx.at.function.app.find.attendancerecord.duplicate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataInfoReturnDto {
	/** コード */
	private String code;
	
	/** 名称 */
	private String name;

	private int id;

	/**
	 * Instantiates a new data infor return dto.
	 *
	 * @param code the code
	 * @param name the name
	 * @param layoutId the layout id
	 */
	public DataInfoReturnDto(String code, String name, int id) {
		super();
		this.code = code;
		this.name = name;
		this.id = id;
	}
}
