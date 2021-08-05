package nts.uk.ctx.exio.infra.entity.input.setting.assembly;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingItemMapping;
import nts.uk.ctx.exio.infra.entity.input.setting.XimmtImportSettingPK;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *  項目マッピング
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "XIMMT_ITEM_MAPPING")
public class XimmtItemMapping extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private XimmtItemMappingPK pk;

	/* CSV列番号 */
	@Column(name = "CSV_COLUMN_NO")
	private Integer csvColumnNo;

	/* 固定値 */
	@Column(name = "FIXED_VALUE")
	private String fixedValue;
	
	@Override
	protected Object getKey() {
		return pk;
	} 
	
	public static final JpaEntityMapper<XimmtItemMapping> MAPPER = new JpaEntityMapper<>(XimmtItemMapping.class);
	
	public static XimmtItemMapping toEntity(XimmtImportSettingPK parentPk, ImportingItemMapping domain) {
		
		val entity = new XimmtItemMapping();
		
		entity.pk = new XimmtItemMappingPK(parentPk.getCompanyId(), parentPk.getCode(), domain.getItemNo());
		entity.csvColumnNo = domain.getCsvColumnNo().orElse(null);
		entity.fixedValue = domain.getFixedValue().map(f -> f.getValue()).orElse(null);
		
		return entity;
	}
	
	public ImportingItemMapping toDomain(){
		
		return new ImportingItemMapping(
				pk.getItemNo(),
				Optional.ofNullable(csvColumnNo),
				Optional.ofNullable(fixedValue).map(StringifiedValue::of));
	}
}
