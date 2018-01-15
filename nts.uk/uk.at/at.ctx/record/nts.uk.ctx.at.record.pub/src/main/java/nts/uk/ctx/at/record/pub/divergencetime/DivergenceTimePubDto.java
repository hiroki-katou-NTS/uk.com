package nts.uk.ctx.at.record.pub.divergencetime;

import lombok.Getter;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class DivergenceTimePubDto {

	/* 会社ID */
	private String companyId;
	/* 乖離時間ID */
	private int divTimeId;
	/* 乖離時間名称 */
	private String divTimeName;

	public DivergenceTimePubDto(String companyId, int divTimeId, String divTimeName) {
		super();
		this.companyId = companyId;
		this.divTimeId = divTimeId;
		this.divTimeName = divTimeName;
	}

}
