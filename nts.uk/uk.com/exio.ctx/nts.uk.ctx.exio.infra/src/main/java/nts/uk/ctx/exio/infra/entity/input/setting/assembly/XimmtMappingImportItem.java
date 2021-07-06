package nts.uk.ctx.exio.infra.entity.input.setting.assembly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportItemMapping;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *  CSV受入項目マッピング
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "XIMMT_MAPPING_IMPORT_ITEM")
public class XimmtMappingImportItem extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private XimmtMappingImportItemPK pk;
	
	/* CSV列番号 */
	@Column(name = "CSV_COLUMN_NO")
	private int csvColumnNumber;
	
	@Override
	protected Object getKey() {
		return pk;
	} 
	
	public static final JpaEntityMapper<XimmtMappingImportItem> MAPPER = new JpaEntityMapper<>(XimmtMappingImportItem.class);
	
	public ImportItemMapping toDomain(){
		return new ImportItemMapping(this.pk.getItemNo(), this.csvColumnNumber);
		
	}
}
