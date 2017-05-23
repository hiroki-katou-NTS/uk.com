/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment.data.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PrintSettingDto.
 */
@Getter
@Setter
public class PaymentRefundPaddingDto {
	/** The print type. */
	private int printType;

	/** The padding top. */
	// 余白上 E_INP_001
	private BigDecimal paddingTop;

	/** The padding left. */
	// 余白左 E_INP_002, F_INP_003, G_INP_004
	private BigDecimal paddingLeft;

	/** The upper area padding top. */
	// 上段上 F_INP_001, G_INP_001
	private BigDecimal upperAreaPaddingTop;

	/** The under area padding top. */
	// 下段上 F_INP_002, G_INP_003
	private BigDecimal underAreaPaddingTop;

	/** The is show break line. */
	// 区切線の出力 F_SEL_001, G_SEL_001
	private int isShowBreakLine;

	/** The break line margin. */
	// 区切線の補正 F_INP_004
	private BigDecimal breakLineMargin;

	/** The middle area padding top. */
	// 中段上 G_INP_002
	private BigDecimal middleAreaPaddingTop;

	/** The break line margin top. */
	// 区切線の補正上 G_INP_005
	private BigDecimal breakLineMarginTop;

	/** The break line margin buttom. */
	// 区切線の補正下 G_INP_006
	private BigDecimal breakLineMarginButtom;
}
