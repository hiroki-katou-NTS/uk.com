package nts.uk.ctx.exio.infra.entity.input.revise.type.codeconvert;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.BooleanUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert.CodeConvertDetail;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert.ExternalImportCodeConvert;
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
	
	/* 変換対象外を受け入れる */
	@Column(name = "IMPORT_WITHOUT_SETTING")
	private int importWithoutSetting;
	
	public static final JpaEntityMapper<XimmtCodeConvert> MAPPER = new JpaEntityMapper<>(XimmtCodeConvert.class);
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public ExternalImportCodeConvert toDomain(List<CodeConvertDetail> details) {
		return new ExternalImportCodeConvert(
				BooleanUtils.toBoolean(importWithoutSetting),
				details);
	}
}
