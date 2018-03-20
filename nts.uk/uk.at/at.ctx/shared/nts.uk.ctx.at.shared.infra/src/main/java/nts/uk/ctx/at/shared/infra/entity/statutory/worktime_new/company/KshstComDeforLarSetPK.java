package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Embeddable
public class KshstComDeforLarSetPK implements Serializable {
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    
    @NotNull
    @Column(name = "YEAR")
    private short year;

    public KshstComDeforLarSetPK() {
    }

    public KshstComDeforLarSetPK(String cid, short year) {
        this.cid = cid;
        this.year = year;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) year;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshstComDeforLarSetPK)) {
            return false;
        }
        KshstComDeforLarSetPK other = (KshstComDeforLarSetPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        return true;
    }
    
}
