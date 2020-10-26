package nts.uk.ctx.sys.portal.infra.entity.layout.widget;

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
 * UKDesign.詳細設計.ER図.システム.ポータル.レイアウト.SPTMT_LAYOUT_WIDGET
 * 
 * @author LienPTK
 */
@Data
@Entity
@Table(name = "SPTMT_LAYOUT_WIDGET")
@EqualsAndHashCode(callSuper = true)
public class SptmtLayoutWidget extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SptmtLayoutWidgetPK id;

	@Version
	@Column(name = "EXCLUS_VER")
	private BigDecimal exclusVer;

	@Column(name = "CONTRACT_CD")
	private String contractCd;

	@Column(name = "WIDGET_DISP")
	private BigDecimal widgetDisp;

	@Override
	protected Object getKey() {
		return this.id;
	}
}
