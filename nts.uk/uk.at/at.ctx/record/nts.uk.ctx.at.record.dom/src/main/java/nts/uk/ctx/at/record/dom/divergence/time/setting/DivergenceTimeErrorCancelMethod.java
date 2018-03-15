package nts.uk.ctx.at.record.dom.divergence.time.setting;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DivergenceTimeErrorCancelMethod {

	/** The reason inputed. */
	private boolean reasonInputed;
	
	/** The reason selected. */
	private boolean reasonSelected;
	
	public DivergenceTimeErrorCancelMethod(int reasonInputed, int reasonSelected)
	{
		if(reasonInputed == 0) this.reasonInputed =false;
		else this.reasonInputed=true;
		
		if(reasonSelected== 0) this.reasonSelected =false;
		else this.reasonSelected=true;
		
	}

}
