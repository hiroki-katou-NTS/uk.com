package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;
/**
 * 振出の未使用
 * @author do_dt
 *
 */

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.UnUseOfRec;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnUseOfRecDto {
	/**
	 * 使用期限日
	 */
	private String expirationDate;
	/**
	 * 振出データID
	 */
	private String recMngId;
	/**
	 * 発生日数
	 */
	private double occurrenceDays;
	/**
	 * 法定内外区分
	 */
	private Integer statutoryAtr;
	/**
	 * 未使用日数
	 */
	private double unUseDays;
	/**	代休消化区分 */
	private Integer digestionAtr;
	/**	消滅日 */
	private String disappearanceDate;
	/**
	 * 使用開始日
	 */
	private String startDate;
	
	public UnUseOfRecDto(UnbalanceCompensation unUseOfRec) {
		super();
		this.expirationDate = unUseOfRec.getDeadline().toString("yyyy/MM/dd");
		this.recMngId = unUseOfRec.getManageId();
		this.occurrenceDays = unUseOfRec.getNumberOccurren().getDay().v();
		this.statutoryAtr = unUseOfRec.getLegalInExClassi().value;
		this.unUseDays = unUseOfRec.getUnbalanceNumber().getDay().v();
		this.digestionAtr = unUseOfRec.getDigestionCate().value;
		this.disappearanceDate = unUseOfRec.getExtinctionDate().map(x -> x.toString("yyyy/MM/dd")).orElse(null);
		this.startDate = unUseOfRec.getDateOccur().getDayoffDate().map(x -> x.toString("yyyy/MM/dd")).orElse(null);
	}
	
	public UnUseOfRec toDomain() {
	    return new UnUseOfRec(
	            GeneralDate.fromString(expirationDate, "yyyy/MM/dd"), 
	            recMngId, 
	            occurrenceDays, 
	            EnumAdaptor.valueOf(statutoryAtr, StatutoryAtr.class), 
	            unUseDays, 
	            EnumAdaptor.valueOf(digestionAtr, DigestionAtr.class), 
	            disappearanceDate == null ? Optional.empty() : Optional.of(GeneralDate.fromString(disappearanceDate, "yyyy/MM/dd")), 
	            startDate == null ? Optional.empty() : Optional.of(GeneralDate.fromString(startDate, "yyyy/MM/dd")));
	}
}
