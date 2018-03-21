package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

/**
 * The Class DicergenceTypeFinder.
 */
@Stateless
public class DivergenceTypeFinder {

	public List<DivergenceTypeDto> getDivergenceTypeList(){
		
		List<DivergenceTypeDto> typeList = new ArrayList<DivergenceTypeDto>();
		for(int i=0;i<=9;i++)
		{
			DivergenceTypeDto type = DivergenceTypeDto.valueOf(i);
			typeList.add(type);
		}
		
		return typeList;
	}
}
