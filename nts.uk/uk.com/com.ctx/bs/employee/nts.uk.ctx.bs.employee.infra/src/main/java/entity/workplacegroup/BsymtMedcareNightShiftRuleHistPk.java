package entity.workplacegroup;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class BsymtMedcareNightShiftRuleHistPk implements Serializable {
    private final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    public String CID;

    /**
     * 職場グループID : 病棟・事業所情報履歴.職場グループID
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WKPGRP_ID")
    public String WKPGRPID;

    /**
     * 履歴ID: 病棟・事業所情報履歴.履歴.履歴ID
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "HIST_ID")
    public String HISTID;

}
