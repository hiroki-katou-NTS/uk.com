package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.scherec.optitem.CalcResultRange;

/**
 * @author anhnm
 *
 */
public interface CalcResultRangeRepository {

    void update(String companyID, int optionalItemNo, CalcResultRange domain);
    
    CalcResultRange find(String companyID, int OptionalItemNo);
}
