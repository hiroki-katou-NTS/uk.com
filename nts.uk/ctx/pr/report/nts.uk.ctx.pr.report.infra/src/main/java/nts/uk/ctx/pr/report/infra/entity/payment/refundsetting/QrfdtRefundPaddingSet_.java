/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.refundsetting;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QrfdtRefundPaddingSet_.
 */
@StaticMetamodel(QrfdtRefundPaddingSet.class)
public class QrfdtRefundPaddingSet_ {

	/** The qrfdt refund padding set PK. */
	public static volatile SingularAttribute<QrfdtRefundPaddingSet, QrfdtRefundPaddingSetPK> qrfdtRefundPaddingSetPK;

	/** The padding top. */
	public static volatile SingularAttribute<QrfdtRefundPaddingSet, BigDecimal> paddingTop;

	/** The padding left. */
	public static volatile SingularAttribute<QrfdtRefundPaddingSet, BigDecimal> paddingLeft;

	/** The upper area padding top. */
	public static volatile SingularAttribute<QrfdtRefundPaddingSet, BigDecimal> upperAreaPaddingTop;

	/** The under area padding top. */
	public static volatile SingularAttribute<QrfdtRefundPaddingSet, BigDecimal> underAreaPaddingTop;

	/** The is show break line. */
	public static volatile SingularAttribute<QrfdtRefundPaddingSet, BigDecimal> isShowBreakLine;

	/** The break line margin. */
	public static volatile SingularAttribute<QrfdtRefundPaddingSet, BigDecimal> breakLineMargin;

	/** The middle area padding top. */
	public static volatile SingularAttribute<QrfdtRefundPaddingSet, BigDecimal> middleAreaPaddingTop;

	/** The break line margin top. */
	public static volatile SingularAttribute<QrfdtRefundPaddingSet, BigDecimal> breakLineMarginTop;

	/** The break line margin buttom. */
	public static volatile SingularAttribute<QrfdtRefundPaddingSet, BigDecimal> breakLineMarginButtom;

}