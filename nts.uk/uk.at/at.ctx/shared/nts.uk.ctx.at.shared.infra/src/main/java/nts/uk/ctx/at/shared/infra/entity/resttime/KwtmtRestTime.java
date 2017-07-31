package nts.uk.ctx.at.shared.infra.entity.resttime;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "KWTMT_REST_TIME")
@XmlRootElement
public class KwtmtRestTime implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KwtmtRestTimePK kwtmtRestTimePK;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "WORK_FORM_TYPE")
    private short workFormType;
    @Column(name = "TIMEZONE_SET_METHOD")
    private Short timezoneSetMethod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_TIME1")
    private short fdStrTime1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_DAY_ATR1")
    private short fdStrDayAtr1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_TIME1")
    private short fdEndTime1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_DAY_ATR1")
    private short fdEndDayAtr1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_TIME2")
    private short fdStrTime2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_DAY_ATR2")
    private short fdStrDayAtr2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_TIME2")
    private short fdEndTime2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_DAY_ATR2")
    private short fdEndDayAtr2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_TIME3")
    private short fdStrTime3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_DAY_ATR3")
    private short fdStrDayAtr3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_TIME3")
    private short fdEndTime3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_DAY_ATR3")
    private short fdEndDayAtr3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_TIME4")
    private short fdStrTime4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_DAY_ATR4")
    private short fdStrDayAtr4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_TIME4")
    private short fdEndTime4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_DAY_ATR4")
    private short fdEndDayAtr4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_TIME5")
    private short fdStrTime5;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_DAY_ATR5")
    private short fdStrDayAtr5;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_TIME5")
    private short fdEndTime5;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_DAY_ATR5")
    private short fdEndDayAtr5;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_TIME6")
    private short fdStrTime6;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_DAY_ATR6")
    private short fdStrDayAtr6;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_TIME6")
    private short fdEndTime6;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_DAY_ATR6")
    private short fdEndDayAtr6;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_TIME7")
    private short fdStrTime7;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_DAY_ATR7")
    private short fdStrDayAtr7;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_TIME7")
    private short fdEndTime7;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_DAY_ATR7")
    private short fdEndDayAtr7;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_TIME8")
    private short fdStrTime8;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_DAY_ATR8")
    private short fdStrDayAtr8;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_TIME8")
    private short fdEndTime8;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_DAY_ATR8")
    private short fdEndDayAtr8;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_TIME9")
    private short fdStrTime9;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_DAY_ATR9")
    private short fdStrDayAtr9;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_TIME9")
    private short fdEndTime9;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_DAY_ATR9")
    private short fdEndDayAtr9;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_TIME10")
    private short fdStrTime10;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_STR_DAY_ATR10")
    private short fdStrDayAtr10;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_TIME10")
    private short fdEndTime10;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FD_END_DAY_ATR10")
    private short fdEndDayAtr10;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_TIME1")
    private short hdStrTime1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_DAY_ATR1")
    private short hdStrDayAtr1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_TIME1")
    private short hdEndTime1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_DAY_ATR1")
    private short hdEndDayAtr1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_TIME2")
    private short hdStrTime2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_DAY_ATR2")
    private short hdStrDayAtr2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_TIME2")
    private short hdEndTime2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_DAY_ATR2")
    private short hdEndDayAtr2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_TIME3")
    private short hdStrTime3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_DAY_ATR3")
    private short hdStrDayAtr3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_TIME3")
    private short hdEndTime3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_DAY_ATR3")
    private short hdEndDayAtr3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_TIME4")
    private short hdStrTime4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_DAY_ATR4")
    private short hdStrDayAtr4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_TIME4")
    private short hdEndTime4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_DAY_ATR4")
    private short hdEndDayAtr4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_TIME5")
    private short hdStrTime5;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_DAY_ATR5")
    private short hdStrDayAtr5;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_TIME5")
    private short hdEndTime5;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_DAY_ATR5")
    private short hdEndDayAtr5;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_TIME6")
    private short hdStrTime6;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_DAY_ATR6")
    private short hdStrDayAtr6;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_TIME6")
    private short hdEndTime6;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_DAY_ATR6")
    private short hdEndDayAtr6;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_TIME7")
    private short hdStrTime7;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_DAY_ATR7")
    private short hdStrDayAtr7;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_TIME7")
    private short hdEndTime7;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_DAY_ATR7")
    private short hdEndDayAtr7;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_TIME8")
    private short hdStrTime8;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_DAY_ATR8")
    private short hdStrDayAtr8;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_TIME8")
    private short hdEndTime8;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_DAY_ATR8")
    private short hdEndDayAtr8;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_TIME9")
    private short hdStrTime9;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_DAY_ATR9")
    private short hdStrDayAtr9;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_TIME9")
    private short hdEndTime9;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_DAY_ATR9")
    private short hdEndDayAtr9;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_TIME10")
    private short hdStrTime10;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_STR_DAY_ATR10")
    private short hdStrDayAtr10;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_TIME10")
    private short hdEndTime10;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_END_DAY_ATR10")
    private short hdEndDayAtr10;

    public KwtmtRestTime() {
    }

    public KwtmtRestTime(KwtmtRestTimePK kwtmtRestTimePK) {
        this.kwtmtRestTimePK = kwtmtRestTimePK;
    }

    public KwtmtRestTime(KwtmtRestTimePK kwtmtRestTimePK, short workFormType, short fdStrTime1, short fdStrDayAtr1, short fdEndTime1, short fdEndDayAtr1, short fdStrTime2, short fdStrDayAtr2, short fdEndTime2, short fdEndDayAtr2, short fdStrTime3, short fdStrDayAtr3, short fdEndTime3, short fdEndDayAtr3, short fdStrTime4, short fdStrDayAtr4, short fdEndTime4, short fdEndDayAtr4, short fdStrTime5, short fdStrDayAtr5, short fdEndTime5, short fdEndDayAtr5, short fdStrTime6, short fdStrDayAtr6, short fdEndTime6, short fdEndDayAtr6, short fdStrTime7, short fdStrDayAtr7, short fdEndTime7, short fdEndDayAtr7, short fdStrTime8, short fdStrDayAtr8, short fdEndTime8, short fdEndDayAtr8, short fdStrTime9, short fdStrDayAtr9, short fdEndTime9, short fdEndDayAtr9, short fdStrTime10, short fdStrDayAtr10, short fdEndTime10, short fdEndDayAtr10, short hdStrTime1, short hdStrDayAtr1, short hdEndTime1, short hdEndDayAtr1, short hdStrTime2, short hdStrDayAtr2, short hdEndTime2, short hdEndDayAtr2, short hdStrTime3, short hdStrDayAtr3, short hdEndTime3, short hdEndDayAtr3, short hdStrTime4, short hdStrDayAtr4, short hdEndTime4, short hdEndDayAtr4, short hdStrTime5, short hdStrDayAtr5, short hdEndTime5, short hdEndDayAtr5, short hdStrTime6, short hdStrDayAtr6, short hdEndTime6, short hdEndDayAtr6, short hdStrTime7, short hdStrDayAtr7, short hdEndTime7, short hdEndDayAtr7, short hdStrTime8, short hdStrDayAtr8, short hdEndTime8, short hdEndDayAtr8, short hdStrTime9, short hdStrDayAtr9, short hdEndTime9, short hdEndDayAtr9, short hdStrTime10, short hdStrDayAtr10, short hdEndTime10, short hdEndDayAtr10) {
        this.kwtmtRestTimePK = kwtmtRestTimePK;
        this.workFormType = workFormType;
        this.fdStrTime1 = fdStrTime1;
        this.fdStrDayAtr1 = fdStrDayAtr1;
        this.fdEndTime1 = fdEndTime1;
        this.fdEndDayAtr1 = fdEndDayAtr1;
        this.fdStrTime2 = fdStrTime2;
        this.fdStrDayAtr2 = fdStrDayAtr2;
        this.fdEndTime2 = fdEndTime2;
        this.fdEndDayAtr2 = fdEndDayAtr2;
        this.fdStrTime3 = fdStrTime3;
        this.fdStrDayAtr3 = fdStrDayAtr3;
        this.fdEndTime3 = fdEndTime3;
        this.fdEndDayAtr3 = fdEndDayAtr3;
        this.fdStrTime4 = fdStrTime4;
        this.fdStrDayAtr4 = fdStrDayAtr4;
        this.fdEndTime4 = fdEndTime4;
        this.fdEndDayAtr4 = fdEndDayAtr4;
        this.fdStrTime5 = fdStrTime5;
        this.fdStrDayAtr5 = fdStrDayAtr5;
        this.fdEndTime5 = fdEndTime5;
        this.fdEndDayAtr5 = fdEndDayAtr5;
        this.fdStrTime6 = fdStrTime6;
        this.fdStrDayAtr6 = fdStrDayAtr6;
        this.fdEndTime6 = fdEndTime6;
        this.fdEndDayAtr6 = fdEndDayAtr6;
        this.fdStrTime7 = fdStrTime7;
        this.fdStrDayAtr7 = fdStrDayAtr7;
        this.fdEndTime7 = fdEndTime7;
        this.fdEndDayAtr7 = fdEndDayAtr7;
        this.fdStrTime8 = fdStrTime8;
        this.fdStrDayAtr8 = fdStrDayAtr8;
        this.fdEndTime8 = fdEndTime8;
        this.fdEndDayAtr8 = fdEndDayAtr8;
        this.fdStrTime9 = fdStrTime9;
        this.fdStrDayAtr9 = fdStrDayAtr9;
        this.fdEndTime9 = fdEndTime9;
        this.fdEndDayAtr9 = fdEndDayAtr9;
        this.fdStrTime10 = fdStrTime10;
        this.fdStrDayAtr10 = fdStrDayAtr10;
        this.fdEndTime10 = fdEndTime10;
        this.fdEndDayAtr10 = fdEndDayAtr10;
        this.hdStrTime1 = hdStrTime1;
        this.hdStrDayAtr1 = hdStrDayAtr1;
        this.hdEndTime1 = hdEndTime1;
        this.hdEndDayAtr1 = hdEndDayAtr1;
        this.hdStrTime2 = hdStrTime2;
        this.hdStrDayAtr2 = hdStrDayAtr2;
        this.hdEndTime2 = hdEndTime2;
        this.hdEndDayAtr2 = hdEndDayAtr2;
        this.hdStrTime3 = hdStrTime3;
        this.hdStrDayAtr3 = hdStrDayAtr3;
        this.hdEndTime3 = hdEndTime3;
        this.hdEndDayAtr3 = hdEndDayAtr3;
        this.hdStrTime4 = hdStrTime4;
        this.hdStrDayAtr4 = hdStrDayAtr4;
        this.hdEndTime4 = hdEndTime4;
        this.hdEndDayAtr4 = hdEndDayAtr4;
        this.hdStrTime5 = hdStrTime5;
        this.hdStrDayAtr5 = hdStrDayAtr5;
        this.hdEndTime5 = hdEndTime5;
        this.hdEndDayAtr5 = hdEndDayAtr5;
        this.hdStrTime6 = hdStrTime6;
        this.hdStrDayAtr6 = hdStrDayAtr6;
        this.hdEndTime6 = hdEndTime6;
        this.hdEndDayAtr6 = hdEndDayAtr6;
        this.hdStrTime7 = hdStrTime7;
        this.hdStrDayAtr7 = hdStrDayAtr7;
        this.hdEndTime7 = hdEndTime7;
        this.hdEndDayAtr7 = hdEndDayAtr7;
        this.hdStrTime8 = hdStrTime8;
        this.hdStrDayAtr8 = hdStrDayAtr8;
        this.hdEndTime8 = hdEndTime8;
        this.hdEndDayAtr8 = hdEndDayAtr8;
        this.hdStrTime9 = hdStrTime9;
        this.hdStrDayAtr9 = hdStrDayAtr9;
        this.hdEndTime9 = hdEndTime9;
        this.hdEndDayAtr9 = hdEndDayAtr9;
        this.hdStrTime10 = hdStrTime10;
        this.hdStrDayAtr10 = hdStrDayAtr10;
        this.hdEndTime10 = hdEndTime10;
        this.hdEndDayAtr10 = hdEndDayAtr10;
    }

    public KwtmtRestTime(String cid, String siftCd) {
        this.kwtmtRestTimePK = new KwtmtRestTimePK(cid, siftCd);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kwtmtRestTimePK != null ? kwtmtRestTimePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KwtmtRestTime)) {
            return false;
        }
        KwtmtRestTime other = (KwtmtRestTime) object;
        if ((this.kwtmtRestTimePK == null && other.kwtmtRestTimePK != null) || (this.kwtmtRestTimePK != null && !this.kwtmtRestTimePK.equals(other.kwtmtRestTimePK))) {
            return false;
        }
        return true;
    }
}
