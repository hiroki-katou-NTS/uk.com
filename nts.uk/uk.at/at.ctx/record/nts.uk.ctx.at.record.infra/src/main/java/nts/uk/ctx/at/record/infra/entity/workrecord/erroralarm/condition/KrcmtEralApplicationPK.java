/**
 * 11:12:18 AM Dec 6, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author hungnm
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class KrcmtEralApplicationPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    public String cid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "ERROR_CD")
    public String errorCd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "APP_TYPE_CD")
    public int appTypeCd;
	
}
