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
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert.CodeConvertDetail;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *  受入コード変換詳細
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "XIMMT_CODE_CONVERT_DETAIL")
public class XimmtCodeConvertDetail extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private XimmtCodeConvertDetailPK pk;
	
	/* 変換後のコード */
	@Column(name = "SYSTEM_CODE")
	private String systemCode;
	
	public static final JpaEntityMapper<XimmtCodeConvertDetail> MAPPER = new JpaEntityMapper<>(XimmtCodeConvertDetail.class);
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public CodeConvertDetail toDomain() {
		return new CodeConvertDetail(
				pk.getCompanyId(), 
				pk.getConvertCode(), 
				pk.getTargetCode(), 
				systemCode);
	}
}
