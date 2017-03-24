package nts.uk.ctx.basic.infra.entity.system.era;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.AggregateTableEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="CMNMT_ERA")
public class CmnmtEra extends AggregateTableEntity implements Serializable{
	 
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public CmnmtEraPk cmnmtEraPk;
	
	@Basic(optional = false)
//	@Column(name="ERA_NAME")
	@Column(name="ERA_NAME")
	public String era_Name;
	
	@Basic(optional = false)
	@Column(name="ERA_MARK")
	public String era_Mark;
	
	@Column(name="END_D")
	@Convert(converter= GeneralDateToDBConverter.class)
	public GeneralDate end_D;

	@Column(name="FIX_ATR")
	public int fix_Atr;
	
}
