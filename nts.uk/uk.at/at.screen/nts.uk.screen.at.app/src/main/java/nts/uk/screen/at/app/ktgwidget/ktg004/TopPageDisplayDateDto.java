package nts.uk.screen.at.app.ktgwidget.ktg004;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.TopPageDisplayYearMonthEnum;

/**
 * @author thanhpv
 * @name トップページ表示年月 Characteristic
 */
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TopPageDisplayDateDto {
	
	/** 表示年月 */
	private Integer currentOrNextMonth;
	
	/** 締めID */
	private Integer closureId;

	public TopPageDisplayYearMonthEnum getTopPageYearMonthEnum() {
		return EnumAdaptor.valueOf(currentOrNextMonth,TopPageDisplayYearMonthEnum.class);
	}

}
