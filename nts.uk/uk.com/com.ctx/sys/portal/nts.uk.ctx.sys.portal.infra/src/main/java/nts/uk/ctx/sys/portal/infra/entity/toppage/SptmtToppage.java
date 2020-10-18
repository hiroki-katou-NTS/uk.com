package nts.uk.ctx.sys.portal.infra.entity.toppage;

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
 * UKDesign.詳細設計.ER図.システム.ポータル.トップページ.SPTMT_TOPPAGE
 * @author LienPTK
 */
@Data
@Entity
@Table(name = "SPTMT_TOPPAGE")
@EqualsAndHashCode(callSuper = true)
public class SptmtToppage extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SptmtToppagePk id;
	
	@Version
	@Column(name = "EXCLUS_VER")
	private BigDecimal exclusVer;
	
	@Column(name = "CONTRACT_CD")
	private String contractCd;
	
	@Column(name = "TOP_PAGE_NAME")
	private String toppageName;
	
	@Column(name = "LAYOUT_DISP")
	private BigDecimal layoutDisp;

	@Override
	protected Object getKey() {
		return this.id;
	}
}
