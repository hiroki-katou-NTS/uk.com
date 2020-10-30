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
import nts.uk.ctx.sys.portal.dom.toppage.ToppageNew;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * UKDesign.詳細設計.ER図.システム.ポータル.トップページ.SPTMT_TOPPAGE
 * @author LienPTK
 */
@Data
@Entity
@Table(name = "SPTMT_TOPPAGE")
@EqualsAndHashCode(callSuper = true)
public class SptmtToppage extends UkJpaEntity
		implements Serializable, ToppageNew.MementoSetter, ToppageNew.MementoGetter {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SptmtToppagePk id;
	
	@Version
	@Column(name = "EXCLUS_VER")
	private int exclusVer;
	
	@Column(name = "CONTRACT_CD")
	private String contractCd;
	
	@Column(name = "TOP_PAGE_NAME")
	private String topPageName;
	
	@Column(name = "LAYOUT_DISP")
	private BigDecimal layoutDisp;

	@Override
	protected Object getKey() {
		return this.id;
	}

	@Override
	public String getCid() {
		return this.id.cid;
	}

	@Override
	public void setCid(String cid) {
		this.id.cid = cid;
	}

	@Override
	public String getTopPageCode() {
		return this.id.topPageCode;
	}

	@Override
	public void setTopPageCode(String toppageCode) {
		this.id.topPageCode = toppageCode;
	}
}
