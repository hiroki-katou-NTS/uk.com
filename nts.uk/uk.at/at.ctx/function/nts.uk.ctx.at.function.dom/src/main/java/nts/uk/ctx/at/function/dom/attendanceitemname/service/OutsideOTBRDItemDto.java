package nts.uk.ctx.at.function.dom.attendanceitemname.service;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutsideOTBRDItemDto {
	/** The use classification. */
	// 使用区分
	private int useClassification;
	
	/** The be breakdown item no. */
	// 内訳項目NO
	private int breakdownItemNo;
	
	/** The name. */
	// 名称
	private String name;
	
	/** The product number. */
	// 積上番号
	private int productNumber;
	
	/** The attendance item ids. */
	// 集計項目一覧
	private List<Integer> attendanceItemIds;

	public OutsideOTBRDItemDto(int useClassification, int breakdownItemNo, String name, int productNumber,
			List<Integer> attendanceItemIds) {
		super();
		this.useClassification = useClassification;
		this.breakdownItemNo = breakdownItemNo;
		this.name = name;
		this.productNumber = productNumber;
		this.attendanceItemIds = attendanceItemIds;
	}
	
	
}
