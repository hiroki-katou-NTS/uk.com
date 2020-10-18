package nts.uk.ctx.sys.portal.infra.entity.layout;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * UKDesign.詳細設計.ER図.システム.ポータル.レイアウト.SPTMT_LAYOUT
 * @author LienPTK
 */
@Data
@Entity
@Table(name = "SPTMT_LAYOUT")
@EqualsAndHashCode(callSuper = true)
public class SptmtLayout extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SptmtLayoutPk id;
	
	@Version
	@Column(name = "EXCLUS_VER")
	private BigDecimal exclusVer;
	
	@Column(name = "CONTRACT_CD")
	private String contractCd;
	
	@Column(name = "LAYOUT_TYPE")
	private BigDecimal layoutType;
	
	@Column(name = "FLOW_MENU_CD")
	private String flowMenuCd;

	@Column(name = "URL")
	private String url;

	@Column(name = "FLOW_MENU_UP_CD")
	private String flowMenuUpCd;

	@Override
	protected Object getKey() {
		return this.id;
	}
}
