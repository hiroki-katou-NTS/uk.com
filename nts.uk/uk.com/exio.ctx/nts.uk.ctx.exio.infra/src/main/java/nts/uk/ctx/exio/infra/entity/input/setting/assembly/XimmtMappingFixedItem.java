package nts.uk.ctx.exio.infra.entity.input.setting.assembly;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.input.importableitem.ItemType;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.FixedItemMapping;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *  固定値項目マッピング
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "XIMMT_MAPPING_FIXED_ITEM")
public class XimmtMappingFixedItem extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private XimmtMappingFixedItemPK pk; 
	
	/* 項目型 */
	@Column(name = "ITEM_TYPE")
	private int itemType;
	
	/* 数値 */
	@Column(name = "NUMERIC")
	private BigDecimal numeric;
	
	/* 文字列値 */
	@Column(name = "STRING")
	private String string;
	
	/* 日付値 */
	@Column(name = "DATE")
	private GeneralDate date; 
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static final JpaEntityMapper<XimmtMappingFixedItem> MAPPER = new JpaEntityMapper<>(XimmtMappingFixedItem.class);
	
	public FixedItemMapping toDomain() {
		switch(EnumAdaptor.valueOf(itemType, ItemType.class)) {
		case STRING:
			return new FixedItemMapping(this.pk.getItemNo(), string);
		case DATE:
			return new FixedItemMapping(this.pk.getItemNo(), date);
		case INT:
			return new FixedItemMapping(this.pk.getItemNo(), numeric.longValue());
		case REAL:
			return new FixedItemMapping(this.pk.getItemNo(), numeric);
		case TIME_DURATION:
		case TIME_POINT:
			return new FixedItemMapping(this.pk.getItemNo(), numeric.intValue());
		default:
			throw new RuntimeException("項目型に対する実装が存在しません。:" + EnumAdaptor.valueOf(itemType, ItemType.class));
		}
	}
}
