package nts.uk.ctx.exio.infra.entity.input.revise.type.codeconvert;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *  受入コード変換
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "XIMMT_CODE_CONVERT")
public class XimmtCodeConvert extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private XimmtCodeConvertPK pk;
	
	/* コード変換名称 */
	@Column(name = "NAME")
	private String name;
	
	/* 変換対象外を受け入れる */
	@Column(name = "IMPORT_WITHOUT_SETTING")
	private int importWithoutSetting;
	
	public static final JpaEntityMapper<XimmtCodeConvert> MAPPER = new JpaEntityMapper<>(XimmtCodeConvert.class);
	
	@Override
	protected Object getKey() {
		return pk;
	}
}
