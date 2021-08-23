package nts.uk.ctx.office.infra.entity.equipment;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;

/**
 * UKDesign.データベース.ER図.オフィス支援.設備管理.OFIDT_EQUIPMENT
 * 設備情報
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "OFIDT_EQUIPMENT")
public class OfidtEquipment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private OfidtEquipmentPK pk;
	
	/**
	 * 契約コード
	 */
	@NotNull
	@Column(name = "CONTRACT_CD")
	private String contractCd;
	
	/**
	 * 名称
	 */
	@NotNull
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 有効開始日
	 */
	@NotNull
	@Column(name = "EXP_START_DATE")
	private GeneralDate effectiveStartDate;
	
	/**
	 * 有効終了日
	 */
	@NotNull
	@Column(name = "EXP_END_DATE")
	private GeneralDate effectiveEndDate;
	
	/**
	 * 備分類コード
	 */
	@NotNull
	@Column(name = "EQUIPMENT_CLS_CODE")
	private String equipmentClsCode;
	
	/**
	 * 備考
	 */
	@Basic(optional = true)
	@Column(name = "REMARK")
	private String remark;
}
