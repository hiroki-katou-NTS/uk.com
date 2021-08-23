package nts.uk.ctx.office.infra.entity.equipment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UKDesign.データベース.ER図.オフィス支援.設備管理.OFIDT_EQUIPMENT_CLS
 * 設備分類
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "OFIDT_EQUIPMENT_CLS")
public class OfidtEquipmentCls implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OfidtEquipmentClsPK pk;
	
	/**
	 * 名称
	 */
	@NotNull
	@Column(name = "NAME")
	private String name;
}
