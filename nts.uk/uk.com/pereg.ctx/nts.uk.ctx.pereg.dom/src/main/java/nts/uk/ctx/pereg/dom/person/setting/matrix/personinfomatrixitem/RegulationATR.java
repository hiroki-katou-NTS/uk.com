/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem;

/**
 * @author hieult
 *
 */
public enum RegulationATR {

		/** 0: 規定  */
		PROVISION(0),
		
		/** 1: 任意  */
		ANY(1);
	public int value;
	
	private RegulationATR(int type) {
			this.value = type;
	}
}
