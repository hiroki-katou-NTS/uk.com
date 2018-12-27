package nts.uk.ctx.exio.infra.entity.exo.outcnddetail;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 出力条件詳細(定型)
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_OUT_CND_DETAIL")
public class OiomtOutCndDetail extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtOutCndDetailPk outCndDetailPk;
    
    /**
    * 条件SQL
    */
    @Basic(optional = false)
    @Column(name = "EXTER_OUT_CDN_SQL")
    public String exterOutCdnSql;

	@OneToMany(targetEntity = OiomtOutCndDetailItem.class, cascade = CascadeType.ALL, mappedBy = "oiomtOutCndDetail", orphanRemoval = true, fetch = FetchType.LAZY)
	@OrderBy("outCndDetailItemPk.seriNum ASC")
	public List<OiomtOutCndDetailItem> listOiomtOutCndDetailItem;
    
    @Override
    protected Object getKey()
    {
        return outCndDetailPk;
    }

	public OiomtOutCndDetail(String cid, String conditionSettingCd, String exterOutCdnSql,
			List<OutCndDetailItem> listOiomtOutCndDetailItem) {
		this.outCndDetailPk = new OiomtOutCndDetailPk(cid, conditionSettingCd);
		this.exterOutCdnSql = exterOutCdnSql;
		this.listOiomtOutCndDetailItem = listOiomtOutCndDetailItem.stream().map(x -> OiomtOutCndDetailItem.toEntity(x))
				.collect(Collectors.toList());
	}

	public List<OutCndDetailItem> getListOutCndDetailItem() {
		return this.listOiomtOutCndDetailItem.stream().map(x -> x.toDomain()).collect(Collectors.toList());
	}
}
