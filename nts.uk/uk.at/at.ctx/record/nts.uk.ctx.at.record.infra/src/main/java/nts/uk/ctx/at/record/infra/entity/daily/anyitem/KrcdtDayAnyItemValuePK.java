package nts.uk.ctx.at.record.infra.entity.daily.anyitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtDayAnyItemValuePK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*社員ID*/
	@Column(name = "SID")
	public String employeeID;
	/*年月日*/
	@Convert(converter = GeneralDateToDBConverter.class)
	@Column(name = "YMD")
	public GeneralDate generalDate;
	@Column(name = "ITEM_NO")
	public int itemNo;
	
}
