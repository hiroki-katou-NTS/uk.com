package nts.uk.ctx.exio.infra.entity.exo.dataformat.init;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;


import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.dataformat.init.JapCalendarSymbol;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
* 和暦記号
*/
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_JAP_CALENDAR_SYMBOL")
public class OiomtJapCalendarSymbol extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtJapCalendarSymbolPk japCalendarSymbolPk;
    

    
    /**
    * 元号名
    */
    @Basic(optional = false)
    @Column(name = "ERA_NAME")
    public String eraName;
    
    @Override
    protected Object getKey()
    {
        return japCalendarSymbolPk;
    }
    
    
    @ManyToOne
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "CONVERT_CD", referencedColumnName = "CONVERT_CD") })
    private OiomtNumberDataFmSet oiomtNumberDataFmSet;
    
    public JapCalendarSymbol toDomain( ) {
        return new JapCalendarSymbol(this.japCalendarSymbolPk.cid, this.japCalendarSymbolPk.eraId, this.eraName) ;
    }
    public static OiomtJapCalendarSymbol toEntity(JapCalendarSymbol domain) {
        return new OiomtJapCalendarSymbol(new OiomtJapCalendarSymbolPk(domain.getCid(), domain.getEraId()),
        		domain.getEraName().v());
    }
	public OiomtJapCalendarSymbol(OiomtJapCalendarSymbolPk japCalendarSymbolPk, String eraName) {
		super();
		this.japCalendarSymbolPk = japCalendarSymbolPk;
		this.eraName = eraName;
		
	}

	

}
