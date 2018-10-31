package nts.uk.ctx.pr.yearend.infra.yearendadjustment.entity.insurancecompany.earthquakeInsurance;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.earthquakeInsurance.EarthquakeInsurance;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 地震保険情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QETMT_EARTH_QUAKE_INSU")
public class QetmtEarthQuakeInsu extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QetmtEarthQuakeInsuPk earthQuakeInsuPk;
    
    /**
    * 名称
    */
    @Basic(optional = false)
    @Column(name = "EARTHQUAKE_NAME")
    public String earthquakeName;
    
    @Override
    protected Object getKey()
    {
        return earthQuakeInsuPk;
    }

    public EarthquakeInsurance toDomain() {
        return new EarthquakeInsurance(this.earthQuakeInsuPk.cid, this.earthQuakeInsuPk.earthquakeCode, this.earthquakeName);
    }
    public static QetmtEarthQuakeInsu toEntity(EarthquakeInsurance domain) {
        return new QetmtEarthQuakeInsu(new QetmtEarthQuakeInsuPk(domain.getCId(), domain.getEarthquakeInsuranceCode().v()),domain.getEarthquakeInsuranceName().v());
    }

}
