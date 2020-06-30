package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecDetailPara;

/**
 * 振出振休明細
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AbsRecDetailParaDto {
	/**
	 * 社員ID
	 */
	private String sid;
	/**
	 * 状態: 管理データ状態区分
	 */
	private Integer dataAtr;
	/**
	 * 年月日: 発生消化年月日
	 */
	private CompensatoryDayoffDateDto ymdData;
	/**
	 * 発生消化区分
	 */
	private Integer occurrentClass;
	/**
	 * 振休の未相殺
	 */
	private UnOffsetOfAbsDto unOffsetOfAb;
	/**
	 * 振出の未使用
	 */
	private UnUseOfRecDto unUseOfRec;
	
	public AbsRecDetailParaDto(AbsRecDetailPara absRecDetailPara) {
		super();
		this.sid = absRecDetailPara.getSid();
		this.dataAtr = absRecDetailPara.getDataAtr().value;
		this.ymdData = new CompensatoryDayoffDateDto(absRecDetailPara.getYmdData());
		this.occurrentClass = absRecDetailPara.getOccurrentClass().value;
		this.unOffsetOfAb = absRecDetailPara.getUnOffsetOfAb().isPresent()?new UnOffsetOfAbsDto(absRecDetailPara.getUnOffsetOfAb().get()):null;
		this.unUseOfRec = absRecDetailPara.getUnUseOfRec().isPresent()?new UnUseOfRecDto(absRecDetailPara.getUnUseOfRec().get()):null;
	}
	
	
}
