package nts.uk.ctx.exio.infra.entity.input.revise.type.codeconvert;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
	
	@OneToMany(targetEntity = XimmtCodeConvertDetail.class, cascade = CascadeType.ALL, mappedBy = "XimmtCodeConvert"
			+ "", orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "XIMMT_CODE_CONVERT_DETAIL")
	public List<XimmtCodeConvertDetail> ximmtCodeConvertDetail;
	
	@Override
	protected Object getKey() {
		return pk;
	}
}
